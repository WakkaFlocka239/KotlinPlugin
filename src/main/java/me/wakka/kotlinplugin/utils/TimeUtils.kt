package me.wakka.kotlinplugin.utils

import java.time.Duration

class TimeUtils {
    private interface TimeEnum {
        fun get(): Long

        fun x(multiplier: Int): Long {
            return get() * multiplier
        }

        fun x(multiplier: Double): Long {
            return (get() * multiplier).toLong()
        }

        fun duration(multiplier: Long): Duration {
            return Duration.ofSeconds(get()).dividedBy(20).multipliedBy(multiplier)
        }

        /**
         * Duration of a fraction.
         *
         * @param numerator   fraction top half
         * @param denominator fraction bottom half
         */
        fun duration(numerator: Long, denominator: Long): Duration? {
            return duration(numerator).dividedBy(denominator)
        }
    }


    enum class TickTime(private val ticks: Long) : TimeEnum {
        TICK(1),
        SECOND(TICK.get() * 20),
        MINUTE(SECOND.get() * 60),
        HOUR(SECOND.get() * 60),
        ;

        override fun get(): Long {
            return ticks
        }


    }
}