package com.chapter11.coroutinelite.core

import com.chapter11.coroutinelite.Job
import com.chapter11.coroutinelite.OnCancel
import com.chapter11.coroutinelite.OnComplete
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-06 0:22
 *
 * Desc:
 */
abstract class AbstractCoroutine<T>(override val context: CoroutineContext): Job, Continuation<T> {
    protected val state = AtomicReference<CoroutineState>()

    init {
        state.set(CoroutineState.InComplete())
    }

    // 当前是什么状态
    override val isActive: Boolean
        get() = state.get() is CoroutineState.InComplete

    override fun invokeOnCompletion(onComplete: OnComplete): Disposable {
        return doOnCompleted {
            onComplete()
        }
    }

    override fun invokeOnCancel(onCancel: OnCancel): Disposable {
        TODO("Not yet implemented")
    }

    override fun remove(disposable: Disposable) {
        state.updateAndGet { preState ->
            when (preState) {
                is CoroutineState.InComplete -> {
                    CoroutineState.InComplete().from(preState).without(disposable)
                }
                is CoroutineState.Complete<*> -> {
                    preState
                }
            }
        }
    }

    /**
     * 等待协程执行完
     */
    override suspend fun join() {
        when (state.get()) {
            // 如果协程未完成，那就执行挂起函数
            is CoroutineState.InComplete -> return joinSuspend()
            // 如果执行完，那就直接返回
            is CoroutineState.Complete<*> -> return
        }
    }

    private suspend fun joinSuspend() = suspendCoroutine<Unit> { continuation ->
        doOnCompleted { result ->
            continuation.resume(Unit)
        }
    }

    private fun doOnCompleted(block: (Result<T>) -> Unit): Disposable {
        val disposable = CompletionHandlerDisposable(this, block)
        val newState = state.updateAndGet { preState ->
            when (preState) {
                is CoroutineState.InComplete -> {
                    CoroutineState.InComplete().from(preState).with(disposable)
                }
                is CoroutineState.Complete<*> -> {
                    preState
                }
            }
        }

        (newState as? CoroutineState.Complete<T>)?.let {
            block(
                when {
                    it.value != null -> Result.success(it.value)
                    it.exception != null -> Result.failure(it.exception)
                    else -> throw IllegalStateException("Won`t happen!")
                }
            )
        }
        return disposable
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }

    override fun resumeWith(result: Result<T>) {
        val newState = state.updateAndGet { preState ->
            when (preState) {
                // 前一个状态是未完成
                is CoroutineState.InComplete -> {
                    // 根据前一个状态，返回新的状态
                    CoroutineState.Complete(result.getOrNull(), result.exceptionOrNull()).from(preState)
                }
                // 如果前一个状态已经完成了，那么直接抛异常
                is CoroutineState.Complete<*> -> {
                    throw  IllegalStateException("Already Completed!")
                }
            }
        }

        // 处理异常
        // (newState as CoroutineState.Complete<T>).exception?.let {  }

        // 没有异常，通知 completion
        newState.notifyCompletion(result)
        // 通知完成后，清除回调，防止内存泄漏
        newState.clear()
    }
}