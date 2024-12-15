package com.chapter11.coroutinelite.core

import com.chapter11.coroutinelite.Job
import com.chapter11.coroutinelite.OnCancel
import com.chapter11.coroutinelite.OnComplete
import com.utils.Logit
import java.util.concurrent.atomic.AtomicInteger
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
abstract class AbstractCoroutine<T>(override val context: CoroutineContext) : Job, Continuation<T> {
    protected val state = AtomicReference<CoroutineState>()

    init {
        state.set(CoroutineState.InComplete())
    }

    // 当前是什么状态
    override val isActive: Boolean
        get() = state.get() is CoroutineState.InComplete

    override val isCompleted: Boolean
        get() = state.get() is CoroutineState.Complete<*>

    override fun invokeOnCompletion(onComplete: OnComplete): Disposable {
        Logit.d("cfx invokeOnCompletion")
        return doOnCompleted {
            onComplete()
        }
    }

    override fun invokeOnCancel(onCancel: OnCancel): Disposable {
        TODO("Not yet implemented")
    }

    override fun remove(disposable: Disposable) {
        Logit.d("cfx remove")

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

    // 会真正的挂起
    private suspend fun joinSuspend() = suspendCoroutine<Unit> { continuation ->
        doOnCompleted { result ->
            Logit.d("cfx joinSuspend resume")
            // 这里当前状态不是，Complete，所以不会走 doOnCompleted 的 block，所以不会恢复，就一直挂起
            continuation.resume(Unit)
        }
    }

    protected fun doOnCompleted(block: (Result<T>) -> Unit): Disposable {
        Logit.d("cfx doOnCompleted 11111111")
        val disposable = CompletionHandlerDisposable(this, block)
        Logit.d("cfx doOnCompleted disposable: $disposable")
        val newState = state.updateAndGet { preState ->

            when (preState) {
                is CoroutineState.InComplete -> {
                    Logit.d("cfx doOnCompleted 222222")
                    CoroutineState.InComplete().from(preState).with(disposable)
                }
                is CoroutineState.Complete<*> -> {
                    preState
                }
            }
        }
        Logit.d("cfx doOnCompleted 333333 newState: $newState ")

        (newState as? CoroutineState.Complete<T>)?.let {
            Logit.d("cfx doOnCompleted block")
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

    // 协程执行完调用
    override fun resumeWith(result: Result<T>) {
        Logit.d("cfx resumeWith")
        val newState = state.updateAndGet { preState ->
            when (preState) {
                // 前一个状态是未完成
                is CoroutineState.InComplete -> {
                    // 转成完成的状态，返回新的状态
                    CoroutineState.Complete(result.getOrNull(), result.exceptionOrNull()).from(preState)
                }
                // 如果前一个状态已经完成了，那么直接抛异常
                is CoroutineState.Complete<*> -> {
                    throw IllegalStateException("Already Completed!")
                }
            }
        }
        Logit.d("cfx resumeWith newState: $newState")

        // 处理异常
        // (newState as CoroutineState.Complete<T>).exception?.let {  }

        // 没有异常，通知 completion
        newState.notifyCompletion(result)
        // 通知完成后，清除回调，防止内存泄漏
        newState.clear()
    }
}