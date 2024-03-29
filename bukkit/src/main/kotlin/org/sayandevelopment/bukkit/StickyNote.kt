package org.sayandevelopment.bukkit

import com.google.common.util.concurrent.ThreadFactoryBuilder
import org.bukkit.entity.Player
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors
import java.util.concurrent.Future

object StickyNote {

    private val threadFactory = ThreadFactoryBuilder().setNameFormat("sticky-note-${plugin.name}-async-thread-%d").build()
    private val asyncExecutor = Executors.newFixedThreadPool(plugin.exclusiveThreads, threadFactory)

    @JvmStatic
    var debug = false

    @JvmStatic
    val logger = LoggerFactory.getLogger("stickynote")

    @JvmStatic
    fun log(message: String) {
        logger.info(message)
    }

    @JvmStatic
    fun log(message: () -> String) {
        log(message())
    }

    @JvmStatic
    fun warn(message: String) {
        logger.warn(message)
    }

    @JvmStatic
    fun warn(message: () -> String) {
        warn(message())
    }

    @JvmStatic
    fun error(message: String) {
        logger.error(message)
    }

    @JvmStatic
    fun error(message: () -> String) {
        error(message())
    }

    @JvmStatic
    fun debug(message: String) {
        if (debug) {
            logger.debug(message)
        }
    }

    @JvmStatic
    fun debug(message: () -> String) {
        if (debug) {
            debug(message())
        }
    }

    @JvmStatic
    fun runSync(runnable: () -> Unit) {
        plugin.server.scheduler.runTask(plugin, runnable)
    }

    @JvmStatic
    fun runSync(runnable: () -> Unit, delay: Long) {
        plugin.server.scheduler.runTaskLater(plugin, runnable, delay)
    }

    @JvmStatic
    fun runSync(runnable: () -> Unit, delay: Long, period: Long) {
        plugin.server.scheduler.runTaskTimer(plugin, runnable, delay, period)
    }

    @JvmStatic
    fun runAsync(runnable: () -> Unit) {
        plugin.server.scheduler.runTaskAsynchronously(plugin, runnable)
    }

    @JvmStatic
    fun runAsync(runnable: () -> Unit, delay: Long) {
        plugin.server.scheduler.runTaskLaterAsynchronously(plugin, runnable, delay)
    }

    @JvmStatic
    fun runAsync(runnable: () -> Unit, delay: Long, period: Long) {
        plugin.server.scheduler.runTaskTimerAsynchronously(plugin, runnable, delay, period)
    }

    @JvmStatic
    fun runEAsync(runnable: Runnable): Future<*> {
        return asyncExecutor.submit(runnable)
    }

    @JvmStatic
    fun registerListener(listener: Listener) {
        plugin.server.pluginManager.registerEvents(listener, plugin)
    }

    @JvmStatic
    fun unregisterListener(listener: Listener) {
        HandlerList.unregisterAll(listener)
    }

    @JvmStatic
    fun unregisterAllListeners() {
        HandlerList.unregisterAll(plugin)
    }

    @JvmStatic
    fun hasPlugin(name: String): Boolean {
        return plugin.server.pluginManager.getPlugin(name) != null
    }

    @JvmStatic
    fun hasPlugins(vararg name: String): Boolean {
        return name.all { hasPlugin(it) }
    }

    @JvmStatic
    fun plugin() = plugin

    @JvmStatic
    fun server() = plugin.server

    @JvmStatic
    fun pluginDirectory() = plugin.dataFolder

    @JvmStatic
    fun onlinePlayers(): MutableCollection<out Player> = plugin.server.onlinePlayers

    @JvmStatic
    fun shutdown() {
        unregisterAllListeners()
        asyncExecutor.shutdown()
    }

}

fun runSync(runnable: () -> Unit) {
    StickyNote.runSync(runnable)
}

fun runSync(runnable: () -> Unit, delay: Long) {
    StickyNote.runSync(runnable, delay)
}

fun runSync(runnable: () -> Unit, delay: Long, period: Long) {
    StickyNote.runSync(runnable, delay, period)
}

fun runAsync(runnable: () -> Unit) {
    StickyNote.runAsync(runnable)
}

fun runAsync(runnable: () -> Unit, delay: Long) {
    StickyNote.runAsync(runnable, delay)
}

fun runAsync(runnable: () -> Unit, delay: Long, period: Long) {
    StickyNote.runAsync(runnable, delay, period)
}

fun runEAsync(runnable: Runnable): Future<*> {
    return StickyNote.runEAsync(runnable)
}

fun registerListener(listener: Listener) {
    StickyNote.registerListener(listener)
}

fun unregisterListener(listener: Listener) {
    StickyNote.unregisterListener(listener)
}

fun log(message: String) {
    StickyNote.log(message)
}

fun log(message: () -> String) {
    StickyNote.log(message)
}

fun warn(message: String) {
    StickyNote.warn(message)
}

fun warn(message: () -> String) {
    StickyNote.warn(message)
}

fun error(message: String) {
    StickyNote.error(message)
}

fun error(message: () -> String) {
    StickyNote.error(message)
}

fun debug(message: String) {
    StickyNote.debug(message)
}

fun debug(message: () -> String) {
    StickyNote.debug(message)
}

fun hasPlugin(name: String): Boolean {
    return StickyNote.hasPlugin(name)
}

fun hasPlugins(vararg name: String): Boolean {
    return StickyNote.hasPlugins(*name)
}

val server = StickyNote.server()
val pluginDirectory = StickyNote.pluginDirectory()
val onlinePlayers = StickyNote.onlinePlayers()