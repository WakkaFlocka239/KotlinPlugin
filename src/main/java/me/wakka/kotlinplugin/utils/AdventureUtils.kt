package me.wakka.kotlinplugin.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.jetbrains.annotations.Contract
import java.awt.Color
import java.util.Arrays
import java.util.stream.Collectors

@Suppress("unused")
object AdventureUtils {

    private val PLAIN_SERIALIZER =
        PlainTextComponentSerializer.builder().flattener(Bukkit.getUnsafe().componentFlattener()).build()
    private val LEGACY_SERIALIZER =
        LegacyComponentSerializer.builder().extractUrls().hexColors().flattener(Bukkit.getUnsafe().componentFlattener())
            .build()
    private val LEGACY_AMPERSAND_SERIALIZER =
        LegacyComponentSerializer.builder().extractUrls().hexColors().character('&')
            .flattener(Bukkit.getUnsafe().componentFlattener()).build()

    fun asPlainText(component: ComponentLike): String {
        return PLAIN_SERIALIZER.serialize(component.asComponent())
    }

    fun fromLegacyText(string: String): Component {
        return LEGACY_SERIALIZER.deserialize(string)
    }

    @Contract("null -> null; !null -> !null")
    fun textColorOf(color: Color?): TextColor? {
        return if (color == null) null else TextColor.color(color.rgb)
    }

    @Contract("null -> null; !null -> !null")
    fun textColorOf(color: org.bukkit.Color?): TextColor? {
        return if (color == null) null else TextColor.color(color.asRGB())
    }

    @Contract("null -> null; !null -> !null")
    fun textColorOf(color: ChatColor?): TextColor? {
        return if (color == null) null else textColorOf(color.color)
    }

    /**
     * Parses a hexadecimal number
     * @param string number in the format "#FFFFFF" (# optional)
     * @throws IllegalArgumentException string contained an invalid hexadecimal number
     * @return corresponding text color
     */
    @Contract("null -> null; !null -> !null")
    @Throws(IllegalArgumentException::class)
    fun textColorOf(string: String?): TextColor? {
        var string = string ?: return null
        if (string.startsWith("#")) string = string.substring(1)
        return try {
            TextColor.color(string.toInt(16))
        } catch (ex: NumberFormatException) {
            throw IllegalArgumentException("Illegal hex string $string")
        }
    }

    /**
     * Returns a component that has separated the input list of components with commas.
     * <p>
     * If the input list is empty, a blank component will be returned.
     * <br>
     * If the list has one item, it will be returned.
     * <br>
     * If the list has two items, "[component1] and [component2]" will be returned.
     * <br>
     * Else, "[component1], [component2], [...], and [componentX]" will be returned.
     * @param components components to separate by commas.
     * @return a formatted TextComponent
     */
    fun commaJoinText(components: List<ComponentLike>): TextComponent {
        return commaJoinText(components, null)
    }

    /**
     * Returns a component that has separated the input list of components with commas.
     *
     *
     * If the input list is empty, a blank component will be returned.
     * <br></br>
     * If the list has one item, it will be returned.
     * <br></br>
     * If the list has two items, "[component1] and [component2]" will be returned.
     * <br></br>
     * Else, "[component1], [component2], [...], and [componentX]" will be returned.
     * @param components components to separate by commas.
     * @param color optional color to use for the commas
     * @return a formatted TextComponent
     */
    fun commaJoinText(components: List<ComponentLike>, color: TextColor?): TextComponent {
        val component = Component.text("", color)

        if (components.isEmpty()) return component
        if (components.size == 1) return component.append(components[0])
        if (components.size == 2)
            return component.append(components[0])
                .append(Component.text(" and "))
                .append(components[1])

        val builder = component.toBuilder()

        for (i in components.indices) {
            builder.append(components[i])
            if (i < components.size - 2)
                builder.append(Component.text(", "))
            else if (i == components.size - 2)
                builder.append(Component.text(", and "))
        }

        return builder.build()
    }

    /**
     * Maps a list of [ComponentLike] to [Component]
     */
    fun asComponentList(components: List<ComponentLike>): List<Component> {
        return components.stream()
            .map { obj: ComponentLike -> obj.asComponent() }
            .collect(Collectors.toList())
    }

    /**
     * Maps a list of [ComponentLike] to [Component]
     */
    fun asComponentList(vararg components: ComponentLike): List<Component> {
        return Arrays.stream(components)
            .map { obj: ComponentLike -> obj.asComponent() }
            .collect(Collectors.toList())
    }
}