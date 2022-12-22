package me.wakka.kotlinplugin

import org.bukkit.plugin.java.JavaPlugin

class KotlinPlugin : JavaPlugin() {
    override fun onEnable() {
        getCommand("showitem")?.setExecutor(ShowItemCommand)
    }

    override fun onDisable() {

    }


}
