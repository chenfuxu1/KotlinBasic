package com.chapter11.coroutinelite.cancel

import com.chapter11.coroutinelite.CancellationException
import com.chapter11.coroutinelite.Job
import com.chapter11.coroutinelite.OnCancel
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.Continuation
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.intrinsics.intercepted
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED


/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-16 23:29
 *
 * Desc: 协程取消的 Continuation
 */
class CancellationContinuation<T>(private val continuation: Continuation<T>) : Continuation<T> by continuation {
    private val state = AtomicReference<CancelState>(CancelState.InComplete)
    private val cancelHandlers = CopyOnWriteArrayList<OnCancel>()

    val isCompleted: Boolean
        get() = state.get() is CancelState.Complete<*>

    val isActive: Boolean
        get() = state.get() == CancelState.InComplete

    override fun resumeWith(result: Result<T>) {
        state.updateAndGet { prev ->
            when(prev) {
                CancelState.InComplete -> {
                    continuation.resumeWith(result)
                    CancelState.Complete(result.getOrNull(), result.exceptionOrNull())
                }
                is CancelState.Complete<*> -> throw IllegalStateException("Already completed")
                CancelState.Cancelled -> {
                    CancellationException("Cancelled.").let {
                        continuation.resumeWith(Result.failure(it))
                        CancelState.Complete(null, it)
                    }
                }
            }
        }
    }

    fun invokeOnCancel(onCancel: OnCancel) {
        cancelHandlers.add(onCancel)
    }

    fun cancel() {
        if (!isActive) return
        val parent = continuation.context[Job] ?: return
        // 有父协程也要取消
        parent.cancel()
    }

    fun getResult(): Any? {
        installCancelHandler()
        return when(val currentState = state.get()) {
            CancelState.InComplete -> COROUTINE_SUSPENDED
            is CancelState.Complete<*> -> {
                (currentState as CancelState.Complete<T>).let {
                    it.exception?.let {
                        throw it
                    } ?: it.value
                }
            }
            CancelState.Cancelled -> throw CancellationException("Continuation is cancelled")
        }
    }

    private fun installCancelHandler() {
        if (!isActive) return
        val parent = continuation.context[Job] ?: return
        parent.invokeOnCancel {
            doCancel()
        }
    }

    private fun doCancel() {
        state.updateAndGet { prev ->
            when(prev) {
                CancelState.InComplete -> {
                    CancelState.Cancelled
                }
                is CancelState.Complete<*>,
                CancelState.Cancelled -> {
                    prev
                }
            }
        }
        cancelHandlers.forEach(OnCancel::invoke)
        cancelHandlers.clear()
    }

}

suspend inline fun <T> suspendCancellableCoroutine(crossinline block: (CancellationContinuation<T>) -> Unit): T =
    suspendCoroutineUninterceptedOrReturn { c: Continuation<T> ->
        val cancellationContinuation = CancellationContinuation(c.intercepted())
        block(cancellationContinuation)
        cancellationContinuation.getResult()
    }