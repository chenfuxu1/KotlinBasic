package com.chapter11.coroutinelite

import com.chapter11.coroutinelite.context.CoroutineName
import com.chapter11.coroutinelite.core.BlockingCoroutine
import com.chapter11.coroutinelite.core.BlockingQueueDispatcher
import com.chapter11.coroutinelite.core.StandardCoroutine
import com.chapter11.coroutinelite.dispather.DispatcherContext
import com.chapter11.coroutinelite.dispather.Dispatchers
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

fun launch(context: CoroutineContext = EmptyCoroutineContext, block: suspend () -> Unit): Job {
    val completion = StandardCoroutine(newCoroutineContext(context))
    block.startCoroutine(completion)
    return completion
}

fun newCoroutineContext(context: CoroutineContext): CoroutineContext {
    var combined = context + CoroutineName("@Coroutine#${coroutineIndex.getAndIncrement()}")
    // 如果 combined 既不是默认调度器，而且 ContinuationInterceptor 也为空
    if (combined !== Dispatchers.Default && combined[ContinuationInterceptor] == null) {
        combined += Dispatchers.Default
    }
    return combined
}

fun <T> runBlocking(context: CoroutineContext = EmptyCoroutineContext, block: suspend () -> T): T {
    // 需要单独的调度器
    val eventQueue = BlockingQueueDispatcher()
    val newContext = newCoroutineContext(context + DispatcherContext(eventQueue))
    val completion = BlockingCoroutine<T>(newContext, eventQueue)
    block.startCoroutine(completion)
    return completion.joinBlocking()
}