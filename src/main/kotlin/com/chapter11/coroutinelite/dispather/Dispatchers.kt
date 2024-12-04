package com.chapter11.coroutinelite.dispather

import com.chapter11.coroutinelite.dispather.ui.HandlerDispatcher
import com.chapter11.coroutinelite.dispather.ui.SwingDispatcher

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-17 22:18
 *
 * Desc:
 */
object Dispatchers {
    val Default by lazy {
        DispatcherContext(DefaultDispatcher)
    }

    val Android by lazy {
        DispatcherContext(HandlerDispatcher)
    }

    val Swing by lazy {
        DispatcherContext(SwingDispatcher)
    }
}