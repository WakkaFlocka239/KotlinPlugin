package me.wakka.kotlinplugin

import me.wakka.kotlinplugin.StringUtils.camelCase
import net.kyori.adventure.text.Component
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.PlayerInventory


object ShowItemCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if(sender !is Player) {
            sender.sendMessage("You must be in-game to use this command!")
            return true
        }

        if(args.isEmpty())
            return false

        val item: ItemStack = getItem(sender, args[0]) ?: return true

        val argList = args.toMutableList()
        argList.removeAt(0)

        var playerMessage: String = argList.joinToString(separator = " ")
        if(playerMessage.isNotEmpty())
            playerMessage += " "

        var itemName: String? = item.itemMeta.getDisplayName()

        if(itemName == null || itemName.isEmpty())
            itemName = item.type.name.camelCase()

        val component : Component = Component
            .text("$playerMessage[$itemName" + count(item.amount) + "]")
            .hoverEvent(item)

        sender.sendMessage(component)

        return true
    }

    private fun count(amount: Int): String {
        return if(amount == 1) "" else " x$amount"
    }

    private fun getItem(player: Player, slot: String): ItemStack? {
        val item: ItemStack?
        val inv: PlayerInventory = player.inventory

        item = when(slot){
            "offhand" -> inv.itemInOffHand
            "mainhand", "hand" -> inv.itemInMainHand
            "hat", "head", "helm", "helmet" -> inv.helmet
            "chest", "chestplate" -> inv.chestplate
            "pants", "legs", "leggings" -> inv.leggings
            "boots", "feet", "shoes" -> inv.boots
            else -> {
                player.sendMessage("Unknown slot '$slot'")
                return null
            }
        }

        if(item == null || item.type.isAir){
            player.sendMessage("Item in slot '$slot' not found")
            return null
        }

        return item
    }
}
