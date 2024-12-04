package com.chapter10.eg.lua

import com.utils.Logit
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-16 21:07
 *
 * Desc: 对称的协程
 */
class SymCoroutine<T>(
    override val context: CoroutineContext = EmptyCoroutineContext,
    private val block: suspend SymCoroutine<T>.SymCoroutineBody.(T) -> Unit
): Continuation<T> {

    companion object {
        lateinit var main: SymCoroutine<Any?>

        suspend fun main(block: suspend SymCoroutine<Any?>.SymCoroutineBody.() -> Unit) {
            SymCoroutine<Any?> {
                block()
            }.also {
                main = it
            }.start(Unit)
        }

        fun <T> create(
            context: CoroutineContext = EmptyCoroutineContext,
            block: suspend SymCoroutine<T>.SymCoroutineBody.(T) -> Unit
        ): SymCoroutine<T> {
            return SymCoroutine(context, block)
        }
    }

    inner class SymCoroutineBody {
        private tailrec suspend fun <P> transferInner(symCoroutine: SymCoroutine<P>, value: Any?): T {
            if (this@SymCoroutine.isMain) {
                return if (symCoroutine.isMain) {
                    value as T
                } else {
                    val parameter = symCoroutine.coroutine.resume(value as P)
                    transferInner(parameter.coroutine, parameter.value)
                }
            } else {
                this@SymCoroutine.coroutine.run {
                    return yield(Parameter(symCoroutine, value as P))
                }
            }
        }

        /**
         * 参数 1：转移的目标
         */
        suspend fun <P> transfer(symCoroutine: SymCoroutine<P>, value: P): T {
            return transferInner(symCoroutine, value)
        }
    }

    class Parameter<T>(val coroutine: SymCoroutine<T>, val value: T)

    val isMain: Boolean
        get() = this == main

    private val body = SymCoroutineBody()

    private val coroutine = Coroutine<T, Parameter<*>>(context) {
        Parameter(this@SymCoroutine, suspend {
            block(body, it)
            if (this@SymCoroutine.isMain) Unit else throw IllegalStateException("SymCoroutine can not be dead")
        }() as T)
    }

    override fun resumeWith(result: Result<T>) {
        throw IllegalStateException("SymCoroutine can not be dead!")
    }

    suspend fun start(value: T) {
        coroutine.resume(value)
    }
}

object SymCoroutines {
    val coroutine0: SymCoroutine<Int> = SymCoroutine.create<Int> { param: Int ->
        Logit.d("cfx coroutine-0 param: $param")
        var result = transfer(coroutine2, 0)
        Logit.d("cfx coroutine-0 - 1 result: $result")
        result = transfer(SymCoroutine.main, Unit)
        Logit.d("cfx coroutine-0 - 2 result: $result")
    }

    val coroutine1: SymCoroutine<Int> = SymCoroutine.create<Int> { param: Int ->
        Logit.d("cfx coroutine-1 param: $param")
        val result = transfer(coroutine0, 1)
        Logit.d("cfx coroutine-1 - 1 result: $result")
    }

    val coroutine2: SymCoroutine<Int> = SymCoroutine.create<Int> { param: Int ->
        Logit.d("cfx coroutine-2 param: $param")
        var result = transfer(coroutine1, 2)
        Logit.d("cfx coroutine-2 - 1 result: $result")
        result = transfer(coroutine0, 2)
        Logit.d("cfx coroutine-2 - 2 result: $result")
    }
}

suspend fun main() {
    SymCoroutine.main {
        Logit.d("cfx main 0")
        val result = transfer(SymCoroutines.coroutine2, 3)
        Logit.d("cfx main end result: $result")
    }
}