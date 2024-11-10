// package com.chapter11.coroutinelite
//
// import com.chapter11.coroutinelite.context.CoroutineName
// import com.chapter11.coroutinelite.core.StandardCoroutine
// import java.util.concurrent.atomic.AtomicInteger
// import kotlin.coroutines.CoroutineContext
// import kotlin.coroutines.EmptyCoroutineContext
// import kotlin.coroutines.startCoroutine
//
// /**
//  * Project: KotlinBasic
//  * Create By: Chen.F.X
//  * DateTime: 2024-11-06 0:21
//  *
//  * Desc:
//  */
// private var coroutineIndex = AtomicInteger(0)
//
// fun launch(context: CoroutineContext = EmptyCoroutineContext, block: suspend () -> Unit): Job {
//     val completion = StandardCoroutine(newCoroutineContext(context))
//     block.startCoroutine(completion)
//     return completion
// }
//
// fun newCoroutineContext(context: CoroutineContext): CoroutineContext {
//     val combined = context + CoroutineName("@Coroutine#${coroutineIndex.getAndIncrement()}")
//     return combined
// }