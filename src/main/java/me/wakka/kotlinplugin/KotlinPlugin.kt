package me.wakka.kotlinplugin

import me.wakka.kotlinplugin.commands.ShowItemCommand
import me.wakka.kotlinplugin.commands.TaskCommand
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

class KotlinPlugin : JavaPlugin() {

    override fun onEnable() {
        instance = this

        getCommand("showitem")?.setExecutor(ShowItemCommand)
        getCommand("task")?.setExecutor(TaskCommand)
    }

    override fun onDisable() {

    }

    companion object {
        lateinit var instance: Plugin
        private set
    }


}
