package com.chapter11.coroutinelite

import com.chapter11.coroutinelite.context.CoroutineName
import com.chapter11.coroutinelite.core.BlockingCoroutine
import com.chapter11.coroutinelite.core.BlockingQueueDispatcher
import com.chapter11.coroutinelite.core.DeferredCoroutine
import com.chapter11.coroutinelite.core.StandardCoroutine
import com.chapter11.coroutinelite.dispather.DispatcherContext
import com.chapter11.coroutinelite.dispather.Dispatchers
import com.chapter11.coroutinelite.scope.CoroutineScope
import com.utils.Logit
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-06 0:21
 *
 * Desc:
 */
private var coroutineIndex = AtomicInteger(0)

fun CoroutineScope.launch(context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> Unit): Job {
    val completion = StandardCoroutine(newCoroutineContext(context))
    block.startCoroutine(completion, completion)
    return completion
}

fun CoroutineScope.newCoroutineContext(context: CoroutineContext): CoroutineContext {
    var combined = scopeContext + context + CoroutineName("@Coroutine#${coroutineIndex.getAndIncrement()}")
    // 如果 combined 既不是默认调度器，而且 ContinuationInterceptor 也为空
    if (combined !== Dispatchers.Default && combined[ContinuationInterceptor] == null) {
        combined += Dispatchers.Default
    }
    return combined
}

fun <T> runBlocking(context: CoroutineContext = EmptyCoroutineContext, block: suspend () -> T): T {
    // 需要单独的调度器
    val eventQueue = BlockingQueueDispatcher()
    val newContext = context + DispatcherContext(eventQueue)
    val completion = BlockingCoroutine<T>(newContext, eventQueue)
    Logit.d("block: ${block.hashCode()}")
    block.startCoroutine(completion)
    Logit.d("cfx runBlocking 1111")
    return completion.joinBlocking()
}

fun <T> CoroutineScope.async(context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> T): Deferred<T> {
    val completion = DeferredCoroutine<T>(newCoroutineContext(context))
    block.startCoroutine(completion, completion)
    return completion
}