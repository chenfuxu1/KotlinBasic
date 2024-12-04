package com.chapter11.coroutinelite.dispather.ui

import android.os.Handler
import android.os.Looper
import com.chapter11.coroutinelite.dispather.Dispatcher

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-17 23:30
 *
 * Desc:
 */
object HandlerDispatcher: Dispatcher {
    private val handler = Handler(Looper.getMainLooper())

    override fun dispatch(block: () -> Unit) {
        // post 到主线程执行
        handler.post(block)
    }
}