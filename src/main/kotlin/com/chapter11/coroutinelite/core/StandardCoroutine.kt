package com.chapter11.coroutinelite.core

import com.chapter11.coroutinelite.exception.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-07 0:14
 *
 * Desc: 标准的协程
 */
class StandardCoroutine(context: CoroutineContext) : AbstractCoroutine<Unit>(context) {
    override fun handleJobException(e: Throwable): Boolean {
        context[CoroutineExceptionHandler]?.handleException(context, e) ?: Thread.currentThread().let {
            it.uncaughtExceptionHandler.uncaughtException(it, e)
        }
        return true
    }
}