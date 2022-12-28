package me.wakka.kotlinplugin.utils

import de.tr7zw.nbtapi.NBTItem
import me.wakka.kotlinplugin.features.resourcepack.CustomMaterial
import me.wakka.kotlinplugin.utils.StringUtils.colorize
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.EnchantmentStorageMeta
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta
import java.util.Arrays
import java.util.Objects
import java.util.function.Consumer
import java.util.function.Predicate
import java.util.function.Supplier

@Suppress("unused", "DEPRECATION", "MemberVisibilityCanBePrivate")
class ItemBuilder : Cloneable, Supplier<ItemStack> {
    constructor(material: Material) {
        this.itemStack = ItemStack(material)
    }

    constructor(material: Material, amount: Int) {
        this.itemStack = ItemStack(material, amount)
    }

    constructor(itemBuilder: ItemBuilder) {
        ItemBuilder(itemBuilder.build())
        doLoreize = itemBuilder.doLoreize
    }

    constructor(customMaterial: CustomMaterial) {
        ItemBuilder(customMaterial.getItem())
    }

    constructor(itemStack: ItemStack?) {
        ItemBuilder(itemStack, false)
    }

    constructor(itemStack: ItemStack?, update: Boolean) {
        if (itemStack == null)
            return

        this.itemStack = if (update) itemStack else itemStack.clone()
        this.itemMeta = itemStack.itemMeta

        if (this.itemMeta != null && this.itemMeta!!.lore != null)
            this.lore.addAll(itemMeta!!.lore!!)

        this.update = update
    }

    val nbtModelData = "CustomModelData"

    private var itemStack: ItemStack? = null
    private var itemMeta: ItemMeta? = null

    private var lore: MutableList<String> = mutableListOf()
    private var doLoreize = true
    private var update = false

    fun material(material: Material?): ItemBuilder {
        itemStack!!.type = material!!
        itemMeta = itemStack!!.itemMeta
        return this
    }

    fun material(): Material {
        return itemStack!!.type
    }

    fun amount(amount: Int): ItemBuilder {
        itemStack!!.amount = amount
        return this
    }

    fun damage(damage: Int): ItemBuilder {
        if (itemMeta !is Damageable) throw UnsupportedOperationException("Cannot apply durability to non-damageable item")
        (itemMeta as Damageable).damage = damage
        return this
    }

    private fun removeItalicIfUnset(_component: Component): Component {
        var component = _component
        if (component.decoration(TextDecoration.ITALIC) == TextDecoration.State.NOT_SET) component =
            component.decoration(TextDecoration.ITALIC, false)
        return component
    }

    private fun removeItalicIfUnset(vararg components: ComponentLike): List<Component?> {
        return AdventureUtils.asComponentList(components.asList()).stream()
            .map { _components -> removeItalicIfUnset(_components) }
            .toList()
    }

    private fun removeItalicIfUnset(components: List<ComponentLike>): List<Component?> {
        return AdventureUtils.asComponentList(components).stream()
            .map { _components -> removeItalicIfUnset(_components) }
            .toList()
    }

    fun name(): String {
        return itemMeta!!.displayName
    }

    fun name(displayName: String?): ItemBuilder {
        if (displayName == null) itemMeta!!.setDisplayName(null) else itemMeta!!.setDisplayName(colorize("&f$displayName"))
        return this
    }

    fun name(componentLike: ComponentLike?): ItemBuilder {
        if (componentLike != null) itemMeta!!.displayName(removeItalicIfUnset(componentLike.asComponent()))
        return this
    }

    fun resetName(): ItemBuilder {
        return name(null as String?)
    }

    // TODO: May not work
    fun resetLore(): ItemBuilder {
        lore.clear()
        return this
    }

    fun setLore(vararg lore: String): ItemBuilder {
        return setLore(lore.asList())
    }

    fun setLore(lore: List<String>): ItemBuilder {
        resetLore()
        this.lore.addAll(lore)
        return this
    }

    fun lore(vararg lore: String): ItemBuilder {
        return lore(lore.asList())
    }

    fun lore(lore: Collection<String>): ItemBuilder {
        this.lore.addAll(lore)
        return this
    }

    fun lore(line: Int, text: String): ItemBuilder {
        while (lore.size < line) lore.add("")
        lore[line - 1] = colorize(text)
        return this
    }

    fun loreRemove(line: Int): ItemBuilder {
        if (lore.isEmpty()) throw UnsupportedOperationException("Item does not have lore")
        if (line - 1 > lore.size) throw UnsupportedOperationException("Line $line does not exist")
        lore.removeAt(line - 1)
        return this
    }

    fun componentLore(): List<Component?> {
        return if (itemMeta!!.hasLore()) Objects.requireNonNull(
            itemMeta!!.lore()
        ) else
            ArrayList()
    }

    fun loreize(doLoreize: Boolean): ItemBuilder {
        this.doLoreize = doLoreize
        return this
    }

    fun enchant(enchantment: Enchantment?): ItemBuilder {
        return enchant(enchantment, 1)
    }

    fun enchant(enchantment: Enchantment?, level: Int): ItemBuilder {
        return enchant(enchantment, level, true)
    }

    fun enchantMax(enchantment: Enchantment): ItemBuilder {
        return enchant(enchantment, enchantment.maxLevel, true)
    }

    fun enchant(enchantment: Enchantment?, level: Int, ignoreLevelRestriction: Boolean): ItemBuilder {
        if (itemStack!!.type == Material.ENCHANTED_BOOK) (itemMeta as EnchantmentStorageMeta).addStoredEnchant(
            enchantment!!,
            level,
            ignoreLevelRestriction
        ) else itemMeta!!.addEnchant(
            enchantment!!, level, ignoreLevelRestriction
        )
        return this
    }

    fun enchantRemove(enchantment: Enchantment?): ItemBuilder {
        itemMeta!!.removeEnchant(enchantment!!)
        return this
    }

    fun enchants(item: ItemStack): ItemBuilder {
        if (item.itemMeta != null) item.itemMeta.enchants.forEach { (enchant: Enchantment?, level: Int?) ->
            itemMeta!!.addEnchant(
                enchant!!, level!!, true
            )
        }
        return this
    }

    fun glow(): ItemBuilder {
        enchant(Enchantment.ARROW_INFINITE)
        itemFlags(ItemFlag.HIDE_ENCHANTS)
        return this
    }

    fun glow(glow: Boolean): ItemBuilder {
        return if (glow) glow() else this
    }

    fun isGlowing(): Boolean {
        return itemMeta!!.hasEnchant(Enchantment.ARROW_INFINITE) && itemMeta!!.hasItemFlag(ItemFlag.HIDE_ENCHANTS)
    }

    fun unbreakable(): ItemBuilder {
        itemMeta!!.isUnbreakable = true
        return this
    }

    fun itemFlags(flags: ItemFlags): ItemBuilder {
        return itemFlags(flags.get())
    }

    fun itemFlags(vararg flags: ItemFlag): ItemBuilder {
        return itemFlags(*flags)
    }

    fun itemFlags(flags: List<ItemFlag>): ItemBuilder {
        itemMeta!!.addItemFlags(*flags.toTypedArray())
        return this
    }

    enum class ItemFlags(private val predicate: Predicate<ItemFlag>?) {
        HIDE_ALL({ itemFlag -> itemFlag.name.startsWith("HIDE_") });

        fun get(): List<ItemFlag> {
            return Arrays.stream(ItemFlag.values()).filter(predicate).toList()
        }
    }

    // Leather Armor

    fun dyeColor(): Color? {
        if (itemMeta != null && itemMeta is LeatherArmorMeta)
            return (itemMeta as LeatherArmorMeta).color

        return null
    }

    fun dyeColor(hex: String): ItemBuilder {
        return dyeColor(hexToBukkit(hex))
    }

    fun dyeColor(color: Color): ItemBuilder {
        if (itemMeta != null && itemMeta is LeatherArmorMeta)
            (itemMeta as LeatherArmorMeta).setColor(color)
        return this
    }

    private fun hexToBukkit(_hex: String): Color {
        var hex = _hex
        if (!hex.startsWith("#"))
            hex = "#$hex"

        val decode = java.awt.Color.decode(hex)

        return Color.fromRGB(decode.red, decode.green, decode.blue)
    }

    // NBT

    // NBT
    fun nbt(consumer: Consumer<NBTItem?>): ItemBuilder {
        val nbtItem: NBTItem = nbtItem()
        consumer.accept(nbtItem)
        itemStack = nbtItem.item
        itemMeta = itemStack!!.itemMeta
        return this
    }

    private fun nbtItem(): NBTItem {
        return NBTItem(build())
    }

    fun attribute(attribute: Attribute?, value: AttributeModifier?): ItemBuilder {
        itemMeta!!.addAttributeModifier(attribute!!, value!!)
        return this
    }

    fun modelId(id: Int): ItemBuilder {
        if (id > 0) nbt { item: NBTItem? -> item?.setInteger(nbtModelData, id) }
        return this
    }

    fun modelId(): Int {
        val nbtItem: NBTItem = nbtItem()
        return nbtItem.getInteger(nbtModelData)
    }

    object ModelId {
        fun of(item: ItemStack?): Int {
            return if (Nullables.isNullOrAir(item)) 0 else of(item)
        }

        fun of(item: ItemBuilder): Int {
            return item.modelId()
        }

        fun hasModelId(item: ItemStack?): Boolean {
            return of(item) != 0
        }

        fun hasModelId(item: ItemBuilder): Boolean {
            return of(item) != 0
        }
    }

    // Building //

    override fun get(): ItemStack {
        return build() ?: ItemStack(Material.AIR)
    }

    fun build(): ItemStack? {
        return if (update) {
            buildLore()
            if (itemMeta != null)
                itemStack!!.itemMeta = itemMeta

            itemStack
        } else {
            val result = itemStack!!.clone()
            buildLore()
            if (itemMeta != null)
                result.itemMeta = itemMeta

            result
        }
    }

    fun buildLore() {
        if (lore.isEmpty()) return  // don't override Component lore
        lore.removeIf { obj: String? -> Objects.isNull(obj) }
        val colorized: MutableList<String> = ArrayList()
        for (line in lore) if (doLoreize) colorized.addAll(StringUtils.loreize(colorize(line))) else colorized.add(
            colorize(line)
        )
        itemMeta!!.lore = colorized
        itemStack!!.itemMeta = itemMeta
        itemMeta = itemStack!!.itemMeta
    }

    override fun clone(): ItemBuilder {
        itemStack!!.itemMeta = itemMeta
        val builder = ItemBuilder(itemStack!!.clone())
        builder.lore(lore)
        builder.loreize(doLoreize)
        return builder
    }


}