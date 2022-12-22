package me.wakka.kotlinplugin.utils

import me.wakka.kotlinplugin.utils.StringUtils.colorize
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.event.HoverEventSource
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer
import java.util.function.Consumer

class JsonBuilder : ComponentLike {
    private var builder = Component.text()
    private val result = Component.text()

    private var lore: MutableList<String> = mutableListOf()

    fun next(component: ComponentLike?): JsonBuilder {
        if (component != null)
            builder.append(component)
        return this
    }

    fun next(formattedText: String?): JsonBuilder {
        if (formattedText != null)
            builder.append(AdventureUtils.fromLegacyText(colorize(formattedText)!!))
        return this
    }

    fun <V> hover(hoverEvent: HoverEventSource<V>?): JsonBuilder {
        builder.hoverEvent(hoverEvent)
        return this
    }

    fun group(): JsonBuilder {
        if (lore.isNotEmpty()) {
            val lines: MutableList<String> = ArrayList()

            lore.forEach(Consumer { line: String? ->
                colorize(line)?.let { lines.add(it) }
            })

            val hover = Component.text()
            val iterator: Iterator<String> = lines.iterator()

            while (iterator.hasNext()) {
                hover.append(AdventureUtils.fromLegacyText(iterator.next()))
                if (iterator.hasNext()) hover.append(Component.newline())
            }

            builder.hoverEvent(hover.build().asHoverEvent())
            lore.clear()
        }

        result.append(builder)
        builder = Component.text()
        return this
    }

    fun send(recipient: Any?) {
        PlayerUtils.send(recipient, this)
    }

    fun build(): TextComponent {
        group()
        return result.build()
    }

    override fun asComponent(): Component {
        return build();
    }

    override fun toString(): String {
        return AdventureUtils.asPlainText(build())
    }

    fun serialize(): String {
        return GsonComponentSerializer.gson().serialize(build())
    }

}