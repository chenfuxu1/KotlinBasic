package com.chapter11.coroutinelite.scope

import sun.reflect.generics.scope.Scope
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.startCoroutine
import kotlin.coroutines.suspendCoroutine

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-29 16:07
 *
 * Desc: 主从作用域
 */
private class SupervisorCoroutine<T>(context: CoroutineContext, continuation: Continuation<T>) : ScopeCoroutine<T>(context, continuation) {
    override fun handleChildException(e: Throwable): Boolean {
        return false
    }
}

suspend fun <R> supervisorScope(block: suspend CoroutineScope.() -> R): R = suspendCoroutine { continuation ->
    val coroutine = SupervisorCoroutine(continuation.context, continuation)
    block.startCoroutine(coroutine, coroutine)
}