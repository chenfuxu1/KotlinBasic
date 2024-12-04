package com.chapter11.coroutinelite.dispather

import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-17 22:11
 *
 * Desc:
 */
object DefaultDispatcher: Dispatcher {
    private val threadGroup = ThreadGroup("DefaultDispatcher")
    private val threadIndex = AtomicInteger(0)
    // Runtime.getRuntime().availableProcessors() cpu 的个数
    private val executor = Executors.newFixedThreadPool(2 * Runtime.getRuntime().availableProcessors()) { runnable ->
        Thread(threadGroup, runnable, "${threadGroup.name}-worker-${threadIndex.getAndIncrement()}").apply {
            isDaemon = true
        }
    }

    override fun dispatch(block: () -> Unit) {
        executor.submit(block)
    }
}