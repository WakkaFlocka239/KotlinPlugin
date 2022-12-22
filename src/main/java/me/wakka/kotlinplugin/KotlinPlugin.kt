package me.wakka.kotlinplugin

import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

class KotlinPlugin : JavaPlugin() {

    override fun onEnable() {
        instance = this

        getCommand("showitem")?.setExecutor(ShowItemCommand)
        getCommand("tasktest")?.setExecutor(TaskCommand)
    }

    override fun onDisable() {

    }

    companion object {
        lateinit var instance: Plugin
        private set
    }


}
