package me.wakka.kotlinplugin.commands

import me.wakka.kotlinplugin.utils.Tasks
import me.wakka.kotlinplugin.utils.TimeUtils.TickTime
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

        var count = 0
        val taskId = Tasks.repeat(0, TickTime.SECOND.x(1)) {
            count++
            sender.sendMessage("Repeating Task! $count")
        }

        Tasks.wait(TickTime.SECOND.x(5)) {
            Tasks.cancel(taskId)
            sender.sendMessage("Task $taskId Cancelled!")
        }

        return true
    }
}