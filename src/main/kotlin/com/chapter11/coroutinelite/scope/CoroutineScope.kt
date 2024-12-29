package com.chapter11.coroutinelite.scope

import com.chapter11.coroutinelite.Job
import com.chapter11.coroutinelite.core.AbstractCoroutine
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.startCoroutine
import kotlin.coroutines.suspendCoroutine

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-29 11:14
 *
 * Desc: 协同作用域
 */
interface CoroutineScope {
    val scopeContext: CoroutineContext
}

internal class ContextScope(context: CoroutineContext): CoroutineScope {
    override val scopeContext: CoroutineContext = context
}

operator fun CoroutineScope.plus(context: CoroutineContext): CoroutineScope = ContextScope(scopeContext + context)

fun CoroutineScope.cancel() {
    val job = scopeContext[Job] ?: error("Scope can not be cancelled because it has not job")
    job.cancel()
}

suspend fun <R> coroutineScope(block: suspend CoroutineScope.() -> R): R = suspendCoroutine { continuation ->
    val coroutine = ScopeCoroutine(continuation.context, continuation)
    block.startCoroutine(coroutine, coroutine)
}

internal open class ScopeCoroutine<T>(context: CoroutineContext, val continuation: Continuation<T>): AbstractCoroutine<T>(context) {
    override fun resumeWith(result: Result<T>) {
        super.resumeWith(result)
        continuation.resumeWith(result)
    }
}