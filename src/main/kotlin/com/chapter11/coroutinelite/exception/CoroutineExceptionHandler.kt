package com.chapter11.coroutinelite.exception

import com.chapter11.coroutinelite.core.AbstractCoroutine
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-26 23:25
 *
 * Desc:
 */
interface CoroutineExceptionHandler: CoroutineContext.Element {
    companion object Key: CoroutineContext.Key<CoroutineExceptionHandler>

    fun handleException(context: CoroutineContext, exception: Throwable)
}

inline fun CoroutineExceptionHandler(crossinline handler: (CoroutineContext, Throwable) -> Unit): CoroutineExceptionHandler =
    object : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {
        override fun handleException(context: CoroutineContext, exception: Throwable) {
            handler.invoke(context, exception)
        }
    }