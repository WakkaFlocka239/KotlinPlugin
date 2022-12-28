package me.wakka.kotlinplugin.utils

object EnumUtils {

    inline fun <reified T : Enum<T>> T.hasNext(): Boolean {
        val values = enumValues<T>()
        return ordinal + 1 < values.size
    }

    inline fun <reified T : Enum<T>> T.next(): T {
        val values = enumValues<T>()
        val nextOrdinal: Int = (ordinal + 1) % values.size
        val lastOrdinal: Int = values.size - 1
        return values[lastOrdinal.coerceAtMost(nextOrdinal)]
    }

    inline fun <reified T : Enum<T>> T.previous(): T {
        val values = enumValues<T>()
        val previousOrdinal: Int = (ordinal - 1) % values.size
        return values[0.coerceAtLeast(previousOrdinal)]
    }

    inline fun <reified T : Enum<T>> T.nextWithLoop(): T {
        val values = enumValues<T>()
        val nextOrdinal: Int = (ordinal + 1) % values.size

        if (nextOrdinal >= values.size)
            return values[0]

        return values[nextOrdinal]
    }

    inline fun <reified T : Enum<T>> T.previousWithLoop(): T {
        val values = enumValues<T>()
        val previousOrdinal: Int = (ordinal + 1) % values.size

        if (previousOrdinal < 0)
            return values[values.size - 1]

        return values[previousOrdinal]
    }
}

