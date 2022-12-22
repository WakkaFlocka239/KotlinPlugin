package me.wakka.kotlinplugin.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentLike
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Bukkit

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
}