package com.chapter11.coroutinelite.sample

import com.chapter11.coroutinelite.delay
import com.chapter11.coroutinelite.delayCancellable
import com.chapter11.coroutinelite.dispather.Dispatchers
import com.chapter11.coroutinelite.launch
import com.utils.Logit
import kotlin.concurrent.thread
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-07 0:22
 *
 * Desc:
 */
suspend fun main() {
    val job = launch {
        Logit.d(1)
        val result = aaa()
        Logit.d("2 $result")
        delayCancellable(1000)
        Logit.d("3")

    }
    Logit.d(job.isActive)
    job.cancel()
    job.join()
    Logit.d("hahah")
}

suspend fun aaa() = suspendCoroutine<Int> {
    thread(isDaemon = true) {
        Thread.sleep(1000)
        it.resume(10086)
    }
}