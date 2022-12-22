package me.wakka.kotlinplugin

import me.wakka.kotlinplugin.TimeUtils.TickTime
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

object TaskCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender !is Player) {
            sender.sendMessage("You must be in-game to use this command!")
            return true
        }

        val taskId = Tasks.repeat(0, TickTime.SECOND.x(1)) {
            sender.sendMessage("Repeating Task!")
        }

        Tasks.wait(TickTime.SECOND.x(5)) {
            Tasks.cancel(taskId)
            sender.sendMessage("Task $taskId Cancelled!")
        }

        return true
    }



}