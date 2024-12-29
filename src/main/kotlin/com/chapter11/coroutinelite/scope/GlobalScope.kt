package com.chapter11.coroutinelite.scope

import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-29 16:05
 *
 * Desc: 顶级作用域
 */
object GlobalScope : CoroutineScope {
    override val scopeContext: CoroutineContext
        get() = EmptyCoroutineContext
}