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
        Logit.d("${coroutineContext[Job] } $throwable")
    }
    val job = GlobalScope.launch(exceptionHandler) {
        Logit.d(1)
        delay(1000)
        Logit.d("3")
        throw ArithmeticException("div 0")
        Logit.d("5")


    }
    Logit.d(job.isActive)
    job.cancel()
    job.join()
    Logit.d("hahah")
}


