package me.wakka.kotlinplugin.utils

import net.md_5.bungee.api.ChatColor
import java.util.Locale
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.abs

object StringUtils {
    private const val colorChar = "§"
    private const val altColorChar = "&"
    private const val colorCharsRegex = "[$colorChar$altColorChar]"
    private val colorPattern = Pattern.compile("$colorCharsRegex[\\da-fA-F]")
    private val formatPattern = Pattern.compile("$colorCharsRegex[k-orK-OR]")
    private val hexPattern = Pattern.compile("$colorCharsRegex#[a-fA-F\\d]{6}")
    private val hexColorizedPattern: Pattern =
        Pattern.compile(colorCharsRegex + "x(" + colorCharsRegex + "[a-fA-F\\d]){6}")
    private val colorGroupPattern: Pattern =
        Pattern.compile("($colorPattern|($hexPattern|$hexColorizedPattern))(($formatPattern)+)?")

    fun getPrefix(clazz: Class<*>): String {
        return getPrefix(clazz.simpleName)
    }

    fun getPrefix(prefix: String): String {
        return colorize("&8&l[&e$prefix&8&l]&3 ")
    }

    fun colorize(_input: String): String {
        var input = _input
        while (true) {
            val matcher: Matcher = hexPattern.matcher(input)
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

    fun stripColor(input: String): String {
        return ChatColor.stripColor(colorize(input))
    }

    fun stripFormat(input: String): String {
        return formatPattern.matcher(colorize(input)).replaceAll("")
    }

    private const val APPROX_LORE_LINE_LENGTH = 40

    fun loreize(string: String): List<String> {
        return loreize(string, APPROX_LORE_LINE_LENGTH)
    }

    fun loreize(string: String, length: Int): List<String> {
        return object : ArrayList<String>() {
            init {
                val split = string.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                var line = StringBuilder()
                for (word in split) {
                    val oldLength: Int = stripColor(line.toString()).length
                    val newLength: Int = oldLength + stripColor(word).length
                    val append = abs(length - oldLength) >= abs(length - newLength)
                    if (!append) {
                        val newline = line.toString().trim { it <= ' ' }
                        add(line.toString().trim { it <= ' ' })
                        line = StringBuilder(getLastColor(newline))
                    }
                    line.append(word).append(" ")
                }
                add(line.toString().trim { it <= ' ' })
            }
        }
    }

    fun getLastColor(text: String): String {
        val matcher: Matcher = colorGroupPattern.matcher(text)
        var last = ""
        while (matcher.find())
            last = matcher.group()

        return last.lowercase(Locale.getDefault())
    }
}