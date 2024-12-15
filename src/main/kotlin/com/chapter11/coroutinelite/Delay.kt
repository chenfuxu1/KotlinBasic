package com.chapter11.coroutinelite

import com.utils.Logit
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-17 17:35
 *
 * Desc: 挂起函数 delay，不阻塞线程
 */
private val executor = Executors.newScheduledThreadPool(1) { runnable ->
    Thread(runnable, "Delay-Scheduler").apply {
        isDaemon = true
    }
}

suspend fun delay(time: Long, unit: TimeUnit = TimeUnit.MILLISECONDS) = suspendCoroutine<Unit> { continuation ->
    executor.schedule({
        Logit.d("delay resume")
        continuation.resume(Unit)
    }, time, unit)
}
