package com.chapter11.coroutinelite.core

import com.chapter11.coroutinelite.CancellationException
import com.chapter11.coroutinelite.Job
import com.chapter11.coroutinelite.OnCancel
import com.chapter11.coroutinelite.OnComplete
import com.chapter11.coroutinelite.context.CoroutineName
import com.chapter11.coroutinelite.scope.CoroutineScope
import com.utils.Logit
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.*

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-06 0:22
 *
 * Desc:
 */
abstract class AbstractCoroutine<T>(context: CoroutineContext) : Job, Continuation<T>, CoroutineScope {
    protected val state = AtomicReference<CoroutineState>()

    override val context: CoroutineContext = context + this

    override val scopeContext: CoroutineContext
        get() = context

    protected val parentJob = context[Job]

    private var parentCancelDisposable: Disposable? = null

    init {
        state.set(CoroutineState.InComplete())
        parentCancelDisposable = parentJob?.invokeOnCancel {
            cancel()
        }
    }

    // 当前是什么状态
    override val isActive: Boolean
        get() = state.get() is CoroutineState.InComplete

    override val isCompleted: Boolean
        get() = state.get() is CoroutineState.Complete<*>

    // 协程完成时回调
    override fun invokeOnCompletion(onComplete: OnComplete): Disposable {
        Logit.d("cfx invokeOnCompletion")
        return doOnCompleted {
            onComplete()
        }
    }

    // 协程取消时回调
    override fun invokeOnCancel(onCancel: OnCancel): Disposable {
        val disposable = CancellationHandlerDisposable(this, onCancel)
        val newState = state.updateAndGet { prev ->
            when (prev) {
                is CoroutineState.InComplete -> {
                    CoroutineState.InComplete().from(prev).with(disposable)
                }
                is CoroutineState.Cancelling,
                is CoroutineState.Complete<*> -> {
                    // 如果是 Cancelling 或 Complete 状态，直接返回当前状态
                    prev
                }
            }
        }

        (newState as? CoroutineState.Cancelling)?.let {
            // 如果当前是取消的状态，那就回调一下 onCancel，如果是 Complete 就不用再次回调了
            onCancel()
        }
        return disposable
    }

    override fun cancel() {
        val newState = state.updateAndGet { prev ->
            when (prev) {
                is CoroutineState.InComplete -> {
                    // 状态扭转为 Cancelling
                    CoroutineState.Cancelling().from(prev)
                }
                is CoroutineState.Cancelling,
                is CoroutineState.Complete<*> -> {
                    // 如果已经取消，或者已经完成，那就直接返回当前状态
                    prev
                }

            }
        }
        if(newState is CoroutineState.Cancelling) {
            newState.notifyCancellation()
        }

        parentCancelDisposable?.dispose()
    }

    override fun remove(disposable: Disposable) {
        Logit.d("cfx remove")
        state.updateAndGet { preState ->
            when (preState) {
                is CoroutineState.InComplete -> {
                    CoroutineState.InComplete().from(preState).without(disposable)
                }
                is CoroutineState.Cancelling -> {
                    CoroutineState.Cancelling().from(preState).without(disposable)
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
            is CoroutineState.Cancelling,
            // 如果协程未完成，那就执行挂起函数
            is CoroutineState.InComplete -> return joinSuspend()
            // 如果执行完，那就直接返回
            is CoroutineState.Complete<*> -> {
                val currentCallingJobState = coroutineContext[Job]?.isActive ?: return
                if (!currentCallingJobState) {
                    throw CancellationException("Coroutine is cancelled")
                }
                return
            }
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
                is CoroutineState.Cancelling -> {
                    CoroutineState.Cancelling().from(preState).with(disposable)
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

    // 协程执行完调用
    override fun resumeWith(result: Result<T>) {
        Logit.d("cfx resumeWith")
        val newState = state.updateAndGet { preState ->
            when (preState) {
                is CoroutineState.Cancelling,
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
        (newState as CoroutineState.Complete<T>).exception?.let(::tryHandleException)

        // 没有异常，通知 completion
        newState.notifyCompletion(result)
        // 通知完成后，清除回调，防止内存泄漏
        newState.clear()
        parentCancelDisposable?.dispose()
    }

    private fun tryHandleException(e: Throwable): Boolean {
        return when (e) {
            is CancellationException -> false
            else -> {
                (parentJob as AbstractCoroutine<*>).handleChildException(e)?.takeIf {
                    it
                } ?: handleJobException(e)
            }
        }
    }

    protected open fun handleJobException(e: Throwable): Boolean {
        return false
    }

    protected open fun handleChildException(e: Throwable): Boolean {
        cancel()
        return  tryHandleException(e)
    }

    override fun toString(): String {
        return "${context[CoroutineName]?.name}"
    }
}