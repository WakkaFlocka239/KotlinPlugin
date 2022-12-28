package me.wakka.kotlinplugin.utils

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.Contract

object Nullables {

    /**
     * Tests if an item is not null or [air][MaterialTag.ALL_AIR]
     * @param itemStack item
     * @return if item is not null or air
     */
    @Contract("null -> false; !null -> _")
    fun isNotNullOrAir(itemStack: ItemStack?): Boolean {
        return !isNullOrAir(itemStack)
    }

    /**
     * Tests if an item is not null or [air][MaterialTag.ALL_AIR]
     * @param material item
     * @return if item is not null or air
     */
    @Contract("null -> false; !null -> _")
    fun isNotNullOrAir(material: Material?): Boolean {
        return !isNullOrAir(material)
    }

    /**
     * Tests if a block is not null or [air][MaterialTag.ALL_AIR]
     * @param block block
     * @return if block is not null or air
     */
    @Contract("null -> false; !null -> _")
    fun isNotNullOrAir(block: Block?): Boolean {
        return !isNullOrAir(block)
    }

    /**
     * Tests if an item is null or [air][MaterialTag.ALL_AIR]
     * @param itemStack item
     * @return if item is null or air
     */
    @Contract("null -> true; !null -> _")
    fun isNullOrAir(itemStack: ItemStack?): Boolean {
        return itemStack == null || itemStack.type.isEmpty
    }

    /**
     * Tests if an item is null or [air][MaterialTag.ALL_AIR]
     * @param itemBuilder item
     * @return if item is null or air
     */
    @Contract("null -> true; !null -> _")
    fun isNullOrAir(itemBuilder: ItemBuilder?): Boolean {
        return itemBuilder == null || itemBuilder.material().isEmpty
    }

    /**
     * Tests if an item is null or [air][MaterialTag.ALL_AIR]
     * @param material item
     * @return if item is null or air
     */
    @Contract("null -> true; !null -> _")
    fun isNullOrAir(material: Material?): Boolean {
        return material == null || material.isEmpty
    }

    /**
     * Tests if a block is null or [air][MaterialTag.ALL_AIR]
     * @param block block
     * @return if block is null or air
     */
    @Contract("null -> true; !null -> _")
    fun isNullOrAir(block: Block?): Boolean {
        return block == null || block.type.isEmpty
    }

    @Contract("null -> true; !null -> _")
    fun isNullOrEmpty(string: String?): Boolean {
        return string == null || string.trim { it <= ' ' }.isEmpty()
    }

    @Contract("null -> false; !null -> _")
    fun isNotNullOrEmpty(string: String?): Boolean {
        return !isNullOrEmpty(string)
    }

    @Contract("null -> true; !null -> _")
    fun isNullOrEmpty(collection: Collection<*>?): Boolean {
        return collection == null || collection.isEmpty()
    }

    @Contract("null -> false; !null -> _")
    fun isNotNullOrEmpty(collection: Collection<*>?): Boolean {
        return !isNullOrEmpty(collection)
    }

    @Contract("null -> true; !null -> _")
    fun isNullOrEmpty(map: Map<*, *>?): Boolean {
        return map == null || map.isEmpty()
    }

    @Contract("null -> false; !null -> _")
    fun isNotNullOrEmpty(map: Map<*, *>?): Boolean {
        return !isNullOrEmpty(map)
    }
}