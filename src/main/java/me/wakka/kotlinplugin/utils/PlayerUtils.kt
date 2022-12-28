package me.wakka.kotlinplugin.utils

import net.kyori.adventure.identity.Identified
import net.kyori.adventure.identity.Identity
import net.kyori.adventure.text.ComponentLike
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.UUID

object PlayerUtils {
    fun send(recipient: Any?, _message: Any?, vararg objects: Any?) {
        var message = _message
        if (recipient == null || message == null) return

        if (message is String && objects.isNotEmpty())
            message = String.format(message, objects)

        if (recipient is CommandSender) {
            if (!(message is String || message is ComponentLike))
                message = message.toString()

            if (message is String)
                recipient.sendMessage(JsonBuilder(message))
            else if (message is ComponentLike)
                recipient.sendMessage(message)
        }

        else if (recipient is OfflinePlayer) {
            val player: Player? = recipient.player
            if (player != null)
                send(player, message)
        }

        else if (recipient is UUID) {
            send(Bukkit.getPlayer(recipient), message)
        } else if (recipient is Identity) {
            send(Bukkit.getPlayer(recipient.uuid()), message)
        } else if (recipient is Identified) {
            send(Bukkit.getPlayer(recipient.identity().uuid()), message)
        }
    }

    fun runCommand(sender: CommandSender?, commandNoSlash: String?) {
        if (sender == null) return

        val command = Runnable {
            Bukkit.dispatchCommand(sender, commandNoSlash!!)
        }

        if (Bukkit.isPrimaryThread())
            command.run()
        else
            Tasks.sync(command)
    }
}