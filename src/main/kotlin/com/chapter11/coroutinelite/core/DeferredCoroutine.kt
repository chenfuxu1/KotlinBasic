package com.chapter11.coroutinelite.core

import com.chapter11.coroutinelite.CancellationException
import com.chapter11.coroutinelite.Deferred
import com.chapter11.coroutinelite.Job
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.suspendCoroutine

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-15 16:44
 *
 **/
class DeferredCoroutine<T>(context: CoroutineContext) : AbstractCoroutine<T>(context), Deferred<T> {
    override suspend fun await(): T {
        return when (val currentState = state.get()) {
            is CoroutineState.Cancelling,
                // 如果未完成，那就等待
            is CoroutineState.InComplete -> awaitSuspend()
            is CoroutineState.Complete<*> -> {
                coroutineContext[Job]?.isActive?.takeIf { !it }?.let {
                    throw CancellationException("Coroutine is cancelled")
                }
                (currentState.value as T?) ?: throw currentState.exception!!
            }
        }
    }

    private suspend fun awaitSuspend() = suspendCoroutine<T> { continuation ->
        doOnCompleted { result ->
            continuation.resumeWith(result)
        }
    }
}