package com.chapter10.coroutinedemo

import com.utils.Logit
import kotlin.coroutines.*

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-18 23:01
 *
 * Desc:
 *
 */
/*
public fun <T> (suspend () -> T).createCoroutine(
    completion: Continuation<T>
): Continuation<Unit> =
    SafeContinuation(createCoroutineUnintercepted(completion).intercepted(), COROUTINE_SUSPENDED)

    completion 是协程体完成后的回调，即 suspend { } 完成后的回调
 */
fun main() {
    val continuation = suspend {
        Logit.d("cfx In Coroutine")
        66
    }.createCoroutine(object : Continuation<Int> {
        override val context = EmptyCoroutineContext

        override fun resumeWith(result: Result<Int>) {
            Logit.d("cfx resumeWith result: $result")
        }
    })
    /**
     * continuation 是创建出来的协程，这个时候协程还不会直接运行
     * 需要调用 continuation.resume(Unit) 才会立即运行
     */
    continuation.resume(Unit)
}