package me.wakka.kotlinplugin.features.resourcepack

import me.wakka.kotlinplugin.utils.ItemBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

@Suppress("MemberVisibilityCanBePrivate")
enum class CustomMaterial(private val material: Material, private val modelId: Int) {
    GUI_CLOSE(Material.PAPER, 1),
    GUI_BACK(Material.PAPER, 2),
    GUI_PAGE_PREVIOUS(Material.PAPER, 3),
    GUI_PAGE_NEXT(Material.PAPER, 4),
    ;

    fun getItemBuilder(): ItemBuilder {
        return ItemBuilder(material).modelId(modelId)
    }

    fun getItem(): ItemStack? {
        return getItemBuilder().build()
    }


}