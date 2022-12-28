package me.wakka.kotlinplugin

import fr.minuskube.inv.InventoryManager
import fr.minuskube.inv.SmartInvsPlugin
import me.wakka.kotlinplugin.features.commands.AdventCommand
import me.wakka.kotlinplugin.features.commands.ShowItemCommand
import me.wakka.kotlinplugin.features.commands.TaskCommand
import me.wakka.kotlinplugin.utils.Utils
import net.md_5.bungee.api.ChatColor
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

class KotlinPlugin : JavaPlugin() {

    override fun onEnable() {
        invManager.init()

        instance = this

        getCommand("showitem")?.setExecutor(ShowItemCommand)
        getCommand("task")?.setExecutor(TaskCommand)
        getCommand("advent")?.setExecutor(AdventCommand)
    }

    override fun onDisable() {

    }

    companion object {
        val invManager: InventoryManager = SmartInvsPlugin.manager()

        lateinit var instance: Plugin
            private set

        private val debug = false

        fun debug(message: String?) {
            if (debug) instance.logger
                .info("[DEBUG] " + ChatColor.stripColor(message))
        }

        fun log(message: String?) {
            log(Level.INFO, message)
        }

        fun log(message: String?, ex: Throwable?) {
            log(Level.INFO, message, ex)
        }

        fun warn(message: String?) {
            log(Level.WARNING, message)
        }

        fun warn(message: String?, ex: Throwable?) {
            log(Level.WARNING, message, ex)
        }

        fun severe(message: String?) {
            log(Level.SEVERE, message)
        }

        fun severe(message: String?, ex: Throwable?) {
            log(Level.SEVERE, message, ex)
        }

        fun log(level: Level?, message: String?) {
            log(level, message, null)
        }

        fun log(level: Level?, message: String?, ex: Throwable?) {
            instance.logger.log(level, ChatColor.stripColor(message), ex)
        }

        private val listeners: MutableList<Listener> = ArrayList()

        fun registerListener(listener: Listener) {
            if (!Utils.canEnable(listener.javaClass))
                return

            if (listeners.contains(listener)) {
                debug("Ignoring duplicate listener registration for class " + listener.javaClass.simpleName)
                return
            }

            debug("Registering listener: " + listener.javaClass.name)
            if (instance.isEnabled) {
                instance.server.pluginManager.registerEvents(listener, instance)
                listeners.add(listener)

            } else
                log("Could not register listener " + listener.javaClass.name + "!")
        }

        fun unregisterListener(listener: Listener) {
            try {
                HandlerList.unregisterAll(listener)
                listeners.remove(listener)
            } catch (ex: Exception) {
                log("Could not unregister listener $listener!")
                ex.printStackTrace()
            }
        }
    }

}
