package me.wakka.kotlinplugin.utils

object StringUtils {

    fun String.camelCase(): String {
        var result = ""
        val strings: List<String> = this.lowercase().split("_")
        for (string in strings) {
            result += string[0].uppercase() + string.substring(1) + " "
        }

        return result.trim()
    }
}