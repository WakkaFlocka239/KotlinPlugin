package me.wakka.kotlinplugin.utils

import net.md_5.bungee.api.ChatColor
import java.util.regex.Matcher
import java.util.regex.Pattern

object StringUtils {
    private val colorChar = "§"
    private val altColorChar = "&"
    private val colorCharsRegex = "[$colorChar$altColorChar]"
    private val colorPattern = Pattern.compile("$colorCharsRegex[\\da-fA-F]")
    private val formatPattern = Pattern.compile("$colorCharsRegex[k-orK-OR]")
    private val hexPattern = Pattern.compile("$colorCharsRegex#[a-fA-F\\d]{6}")

    fun colorize(_input: String?): String? {
        if(_input == null) return null
        var input = _input

        while (true) {
            val matcher: Matcher = hexPattern.matcher(input!!)
            if (!matcher.find())
                break

            val color = matcher.group()
            input = input.replace(color, ChatColor.of(color.replaceFirst(colorCharsRegex.toRegex(), "")).toString())
        }

        return ChatColor.translateAlternateColorCodes(altColorChar[0], input)
    }

    fun String.camelCase(): String {
        var result = ""
        val strings: List<String> = this.lowercase().split("_")
        for (string in strings) {
            result += string[0].uppercase() + string.substring(1) + " "
        }

        return result.trim()
    }
}