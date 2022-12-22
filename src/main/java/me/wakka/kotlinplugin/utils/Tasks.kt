package me.wakka.kotlinplugin.utils

import me.wakka.kotlinplugin.KotlinPlugin
import me.wakka.kotlinplugin.utils.TimeUtils.TickTime
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitScheduler

object Tasks {
    private val scheduler: BukkitScheduler = Bukkit.getScheduler()
    private val instance: Plugin = KotlinPlugin.instance

    fun repeat(startDelay: TickTime, interval: Long, runnable: Runnable) : Int{
        return repeat(startDelay.get(), interval, runnable)
    }

    fun repeat(startDelay: Long, interval: Long, runnable: Runnable) : Int{
        return scheduler.scheduleSyncRepeatingTask(instance, runnable, startDelay, interval)
    }

    fun wait(delay: TickTime, runnable: Runnable) : Int {
        return wait(delay.get(), runnable)
    }

    fun wait(delay: Long, runnable: Runnable) : Int {
        return scheduler.runTaskLater(instance, runnable, delay).taskId
    }

    fun cancel(taskId: Int) {
        scheduler.cancelTask(taskId)
    }
}