package me.wakka.kotlinplugin.features.menus

import fr.minuskube.inv.ClickableItem
import fr.minuskube.inv.InventoryManager
import fr.minuskube.inv.SmartInventory
import fr.minuskube.inv.content.InventoryContents
import fr.minuskube.inv.content.InventoryProvider
import me.wakka.kotlinplugin.KotlinPlugin
import me.wakka.kotlinplugin.features.resourcepack.CustomMaterial
import me.wakka.kotlinplugin.utils.ItemBuilder
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import java.util.Optional
import java.util.function.Consumer

abstract class CustomMenu : InventoryProvider {
    private val manager: InventoryManager = KotlinPlugin.invManager

    protected open fun backItem(): ItemStack? {
        return ItemBuilder(CustomMaterial.GUI_BACK).name("&cBack").build()
    }

    protected open fun closeItem(): ItemStack? {
        return ItemBuilder(CustomMaterial.GUI_CLOSE).name("&cClose").build()
    }

    protected open fun addBackItem(contents: InventoryContents?, consumer: Consumer<InventoryClickEvent?>?) {
        addBackItem(contents, 0, 0, consumer)
    }

    protected open fun addBackItem(contents: InventoryContents?, viewer: Player, previousMenu: CustomMenu) {
        addBackItem(contents, 0, 0) { previousMenu.open(viewer) }
    }

    protected open fun addBackItem(
        contents: InventoryContents?,
        row: Int,
        col: Int,
        consumer: Consumer<InventoryClickEvent?>?
    ) {
        contents?.set(row, col, ClickableItem.of(backItem(), consumer))
    }

    protected open fun addCloseItem(contents: InventoryContents?) {
        addCloseItem(contents ?: return, 0, 0)
    }

    protected open fun addCloseItem(contents: InventoryContents, row: Int, col: Int) {
        contents.set(row, col, ClickableItem.of(closeItem()) { e -> e.whoClicked.closeInventory() })
    }

    open fun open(viewer: Player) {
        open(viewer, 0)
    }

    fun getInventory(menu: CustomMenu): SmartInventory {
        return SmartInventory.builder()
            .manager(manager)
            .provider(menu)
            .title(getTitle())
            .size(getRows(), 9)
            .build()
    }

    abstract fun getTitle(): String

    abstract fun getRows(): Int

    abstract fun open(viewer: Player, page: Int)

    protected open fun isOpen(viewer: Player, menu: CustomMenu): Boolean {
        val inventory: Optional<SmartInventory> = manager.getInventory(viewer)
        return inventory.isPresent && menu == inventory.get().provider
    }

    fun close(viewer: Player) {
        manager.getInventory(viewer).ifPresent { inv -> inv.close(viewer) }
    }
}