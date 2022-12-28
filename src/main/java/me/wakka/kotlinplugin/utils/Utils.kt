package me.wakka.kotlinplugin.utils

import java.lang.reflect.Modifier
import java.util.Collections

object Utils {
    fun canEnable(clazz: Class<*>): Boolean {
        if (clazz.simpleName.startsWith("_")) return false
        if (Modifier.isAbstract(clazz.modifiers)) return false
        if (Modifier.isInterface(clazz.modifiers)) return false

        return true
    }

    fun <T> reverse(list: List<T?>?): List<T?>? {
        Collections.reverse(list)
        return list
    }
}