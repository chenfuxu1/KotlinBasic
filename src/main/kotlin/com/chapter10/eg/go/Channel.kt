package com.chapter10.eg.go

import com.chapter10.common.DispatcherContext
import com.utils.Logit
import java.util.concurrent.atomic.AtomicReference
import kotlin.coroutines.*

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-16 21:53
 *
 * Desc:
 */
interface Channel<T> {
    suspend fun send(value: T)

    suspend fun receive(): T

    fun close()
}

class ClosedException(message: String) : Exception(message)

class SimpleChannel<T> : Channel<T> {

    sealed class Element {
        override fun toString(): String {
            return this.javaClass.simpleName
        }

        object None : Element()

        class Producer<T>(val value: T, val continuation: Continuation<Unit>) : Element()

        class Consumer<T>(val continuation: Continuation<T>) : Element()

        object Closed : Element()
    }

    private val state = AtomicReference<Element>(Element.None)

    override suspend fun send(value: T) = suspendCoroutine<Unit> { continuation ->
        Logit.d("cfx 11111 send value: $value ====================")
        val preStatus = state.getAndUpdate {
            when (it) {
                Element.None -> {
                    Logit.d("cfx 22222222 send current is None set Element Producer")
                    Element.Producer(value, continuation)
                }
                is Element.Producer<*> -> throw IllegalStateException("Can not send new element while previous is not consumed!")
                is Element.Consumer<*> -> {
                    Logit.d("cfx 333333333 send current is Consumer set Element None")
                    Element.None
                }
                Element.Closed -> throw IllegalStateException("Can not send after closed!")
            }
        }
        Logit.d("cfx 4444444 send preStatus: $preStatus")

        (preStatus as? Element.Consumer<T>)?.continuation?.resume(value)?.let {
            continuation.resume(Unit)
        }
    }

    override suspend fun receive(): T = suspendCoroutine<T> { continuation ->
        Logit.d("cfx 5555555 receive==========================")
        val preStatus = state.getAndUpdate {
            when (it) {
                Element.None -> {
                    Logit.d("cfx 66666666 receive current is None set Element Consumer")
                    Element.Consumer(continuation)
                }
                is Element.Producer<*> -> {
                    Logit.d("cfx 777777777 receive current is Producer set Element None")
                    Element.None
                }
                is Element.Consumer<*> -> throw IllegalStateException("Can not receive new element while previous is not provided!")
                Element.Closed -> throw IllegalStateException("Can not send after closed!")
            }
        }
        Logit.d("cfx 8888888888 receive preStatus: $preStatus")
        (preStatus as? Element.Producer<T>)?.let {
            it.continuation.resume(Unit)
            continuation.resume(it.value)
        }
    }

    override fun close() {
        val preStatus = state.getAndUpdate {
            Element.Closed
        }
        if (preStatus is Element.Consumer<*>) {
            preStatus.continuation.resumeWithException(ClosedException("Channel is closed!"))
        } else if (preStatus is Element.Producer<*>) {
            preStatus.continuation.resumeWithException(ClosedException("Channel is closed!"))
        }
    }
}

fun go(name: String = "", completion: () -> Unit = {}, block: suspend () -> Unit) {
    block.startCoroutine(object : Continuation<Any> {
        override val context: CoroutineContext = DispatcherContext()

        override fun resumeWith(result: Result<Any>) {
            Logit.d("cfx end $name result: $result")
            completion()
        }

    })
}

fun main() {
    val channel = SimpleChannel<Int>()

    go("producer") {
        for (i in 0..6) {
            Logit.d("cfx send i: $i")
            channel.send(i)
        }
    }

    go("consumer", channel::close) {
        for (i in 0..5) {
            Logit.d("cfx receive i: $i")
            var result = channel.receive()
            Logit.d("cfx result: $result")
        }
    }
}