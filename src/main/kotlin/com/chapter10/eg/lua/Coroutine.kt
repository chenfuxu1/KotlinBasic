package com.chapter10.eg.lua

import com.utils.Logit
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.*

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-15 0:22
 *
 * Desc:
 */
sealed class Status {
    class Created(val continuation: Continuation<Unit>) : Status()

    class Yield<P>(val continuation: Continuation<P>) : Status()

    class Resumed<R>(val continuation: Continuation<R>) : Status()

    object Dead : Status()
}

class Coroutine<P, R>(
    override val context: CoroutineContext = EmptyCoroutineContext,
    private val block: suspend Coroutine<P, R>.CoroutineBody.(P) -> R
) : Continuation<R> {

    companion object {
        fun <P, R> create(
            context: CoroutineContext = EmptyCoroutineContext,
            block: suspend Coroutine<P, R>.CoroutineBody.(P) -> R
        ): Coroutine<P, R> {
            return Coroutine(context, block)
        }
    }

    inner class CoroutineBody {
        var parameter: P? = null

        suspend fun yield(value: R): P = suspendCoroutine { continuation ->
            val previousStatus = status.getAndUpdate {
                when (it) {
                    is Status.Created -> throw IllegalStateException("Never started!")
                    is Status.Yield<*> -> throw IllegalStateException("Already yielded!")
                    is Status.Resumed<*> -> {
                        Status.Yield(continuation)
                    }
                    Status.Dead -> throw IllegalStateException("Already dead!")
                }
            }

            (previousStatus as? Status.Resumed<R>)?.continuation?.resume(value)
        }
    }

    private val body = CoroutineBody()

    private val status: AtomicReference<Status>

    val isActive: Boolean
        get() = status.get() != Status.Dead

    init {
        val coroutineBlock: suspend CoroutineBody.() -> R = {
            block(parameter!!)
        }
        val start = coroutineBlock.createCoroutine(body, this)
        status = AtomicReference(Status.Created(start))
    }

    // 协程执行完毕调用
    override fun resumeWith(result: Result<R>) {
        val previousStatus = status.getAndUpdate {
            when (it) {
                is Status.Created -> throw  IllegalStateException("Never started!")
                is Status.Yield<*> -> throw IllegalStateException("Already yielded!")
                is Status.Resumed<*> -> {
                    Status.Dead
                }
                Status.Dead -> throw IllegalStateException("Already dead!")
            }
        }
        (previousStatus as? Status.Resumed<R>)?.continuation?.resumeWith(result)
    }

    suspend fun resume(value: P): R = suspendCoroutine { continuation ->
        val previousStatus = status.getAndUpdate {
            when (it) {
                is Status.Created -> {
                    body.parameter = value
                    Status.Resumed(continuation)
                }
                is Status.Yield<*> -> {
                    Status.Resumed(continuation)
                }
                is Status.Resumed<*> -> throw IllegalStateException("Already resumed!")
                Status.Dead -> throw IllegalStateException("Already dead!")
            }
        }

        // 等状态正真扭转之后才操作
        when (previousStatus) {
            is Status.Created -> previousStatus.continuation.resume(Unit)
            is Status.Yield<*> -> {
                (previousStatus as Status.Yield<P>).continuation.resume(value)
            }
        }
    }

    suspend fun <SymT> SymCoroutine<SymT>.yield(value: R): P {
        return body.yield(value)
    }
}

/**
 * 拦截器，用来切线程
 */
class Dispatcher : ContinuationInterceptor {
    override val key: CoroutineContext.Key<*>
        get() = ContinuationInterceptor

    override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> {
        return DispatcherContinuation(continuation)
    }

}

class DispatcherContinuation<T>(private val continuation: Continuation<T>) : Continuation<T> by continuation {

    private val executor = Executors.newSingleThreadExecutor()

    override fun resumeWith(result: Result<T>) {
        executor.submit {
            continuation.resumeWith(result)
        }
    }

}

/**
 * cfx send: 0
 * cfx start parameter: 0
 * cfx send: 1
 * cfx receive value: 1
 * cfx send: 2
 * cfx receive value: 2
 * cfx send: 3
 * cfx receive value: 3
 * cfx receive value: 200
 */
suspend fun main() {
    val producer = Coroutine.create<Unit, Int>(Dispatcher()) {
        for (i in 0..3) {

            Logit.d("cfx ${Thread.currentThread().name} send: $i")
            yield(i)
        }
        200
    }

    val consumer = Coroutine.create<Int, Unit>(Dispatcher()) { parameter: Int ->
        Logit.d("cfx start ${Thread.currentThread().name} parameter: $parameter")
        for (i in 0..3) {
            val value = yield(Unit)
            Logit.d("cfx ${Thread.currentThread().name} receive value: $value")
        }
    }

    while (producer.isActive && consumer.isActive) {
        val result = producer.resume(Unit)
        consumer.resume(result)
    }

    Logit.d("cfx producer.isActive: ${producer.isActive} consumer.isActive: ${consumer.isActive}")
}