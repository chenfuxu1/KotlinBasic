package com.chapter10.coroutinedemo

import com.utils.Logit
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-20 0:19
 *
 * Desc:
 */
// 作用域
class ProducerScope<T> {
    suspend fun produce(value: T) {
        Logit.d("produce value: $value")
    }
}

fun <R, T> launchCoroutine(receive: R, block: suspend R.() -> T) {
    block.startCoroutine(receive, object : Continuation<T> {
        override val context = EmptyCoroutineContext

        override fun resumeWith(result: Result<T>) {
            Logit.d("Coroutine End result: $result")
        }

    })
}

fun callLaunchCoroutine() {
    launchCoroutine(ProducerScope<Int>()) {
        Logit.d("In Coroutine")
        produce(1024)
        delay(1000)
        produce(2048)
    }
}

fun main() = runBlocking {
    callLaunchCoroutine()
    delay(10000)
}