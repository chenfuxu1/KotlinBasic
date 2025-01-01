package com.chapter12.coroutineadvanced.callbacktosuspend

import com.utils.Logit
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-29 22:44
 *
 * Desc:
 */
suspend fun main() {
    // CompletableFuture.supplyAsync {
    //     // 会异步执行 supplyAsync
    //     3 // 返回值
    // }.thenAccept {
    //     Logit.d(it) // 拿到返回的值
    // }

    val result = CompletableFuture.supplyAsync {
        // 会异步执行 supplyAsync
        3 // 返回值
    }.await()

    Logit.d(result)
}

suspend fun <T> CompletableFuture<T>.await(): T {
    if (isDone) {
        try {
            // 如果执行完了，直接返回获取的值
            return get()
        } catch (e: ExecutionException) {
            throw e.cause ?: e
        }
    }

    // 如果未完成，先挂起
    return suspendCancellableCoroutine { cancellationContinuation ->
        cancellationContinuation.invokeOnCancellation {
            cancel(true)
        }

        whenComplete { value, throwable ->
            if(throwable == null){
                cancellationContinuation.resume(value)
            } else {
                cancellationContinuation.resumeWithException(throwable.cause ?: throwable)
            }
        }
    }
}