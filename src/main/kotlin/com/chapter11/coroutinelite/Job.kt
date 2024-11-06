package com.chapter11.coroutinelite

import com.chapter11.coroutinelite.core.Disposable
import kotlin.coroutines.CoroutineContext

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-04 23:44
 *
 * Desc:
 */
typealias OnComplete = () -> Unit // typealias 表示给 () -> Unit 类型的函数起一个别名
typealias CancellationException = java.util.concurrent.CancellationException
typealias OnCancel = () -> Unit

interface Job : CoroutineContext.Element {
    companion object Key : CoroutineContext.Key<Job>

    override val key: CoroutineContext.Key<*>
        get() = TODO("Not yet implemented")

    val isActive: Boolean

    /**
     * 可以在 Job 上添加回调
     */
    fun invokeOnCompletion(onComplete: OnComplete): Disposable

    fun invokeOnCancel(onCancel: OnCancel): Disposable

    fun remove(disposable: Disposable)

    suspend fun join()

    fun cancel()
}