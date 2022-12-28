package me.wakka.kotlinplugin.features.commands

import me.wakka.kotlinplugin.features.menus.providers.AdventMenu
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object AdventCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("You must be in-game to use this command!")
            return true
        }

        AdventMenu(30).open(sender)

        return true
    }
}