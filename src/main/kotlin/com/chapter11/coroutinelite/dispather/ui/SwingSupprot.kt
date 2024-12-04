package com.chapter11.coroutinelite.dispather.ui

import com.chapter11.coroutinelite.dispather.Dispatcher
import javax.swing.SwingUtilities

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-17 23:27
 *
 * Desc: 支持图形的 Dispatcher，例如安卓需要在主线程刷新 ui
 */
object SwingDispatcher: Dispatcher {
    override fun dispatch(block: () -> Unit) {
        // 切到 ui 执行
        SwingUtilities.invokeLater(block)
    }
}