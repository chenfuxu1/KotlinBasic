package com.chapter11.coroutinelite.sample

import com.chapter11.coroutinelite.Job
import com.chapter11.coroutinelite.delay
import com.chapter11.coroutinelite.exception.CoroutineExceptionHandler
import com.chapter11.coroutinelite.launch
import com.chapter11.coroutinelite.scope.GlobalScope
import com.utils.Logit


/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-07 0:22
 *
 * Desc:
 */
suspend fun main() {
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Logit.d("aaaa exceptionHandler ${coroutineContext[Job] } $throwable")
    }
    val job = GlobalScope.launch(exceptionHandler) {
        Logit.d(1)
        delay(1000)
        val job2 = launch {
            throw ArithmeticException("div 0")
        }
        Logit.d("3")
        job2.join()
        Logit.d("4")
    }
    Logit.d(job.isActive)
    job.join()
    Logit.d("hahah")
}