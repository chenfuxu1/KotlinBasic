package com.chapter13.suspendfunc

import com.chapter02.foo
import com.utils.Logit
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.reflect.KMutableProperty0

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-01 20:12
 *
 * Desc: 不切线程完成挂起动作
 */
class EatGame {
    private var feedContinuation: Continuation<Int>? = null
    private var eatContinuation: Continuation<String>? = null
    private var eatAttempts = 0

    var isActive: Boolean = true
        private set

    suspend fun eat(): String {
        return if (isActive) suspendCoroutine<String> {
            this.eatContinuation = it
            resumeContinuation(this::feedContinuation, eatAttempts++)
        } else ""
    }

    suspend fun feed(food: String): Int {
        return if (isActive) suspendCoroutine<Int> {
            this.feedContinuation = it
            resumeContinuation(this::eatContinuation, food)
        } else -1
    }

    fun timeout() {
        isActive = false
        resumeContinuation(this::feedContinuation, eatAttempts)
        resumeContinuation(this::eatContinuation, "")
    }

    private fun<T> resumeContinuation(continuationRef: KMutableProperty0<Continuation<T>?>, value: T) {
        // 属性引用
        val continuation = continuationRef.get()
        // 防止递归，先置空
        continuationRef.set(null)
        // 执行 resume 操作，启动协程
        continuation?.resume(value)
    }
}

suspend fun eatGame() {
    coroutineScope {
        val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
        val game = EatGame()
        launch(dispatcher) {
            Logit.d("Ready go!")
            delay(3000)
            game.timeout()
            Logit.d("Timeout!")
        }

        launch(dispatcher) {
            while (game.isActive) {
                delay(300)
                val food = Math.random()
                Logit.d(" #1 Food: $food >>>")
                Logit.d(" #1 Complete: ${game.feed("$food")}")
            }
        }

        launch(dispatcher) {
            while (game.isActive) {
                delay(200)
                Logit.d(" #2 Eat: ${game.eat()} <<<")

            }
        }

        /**
         * 1.三个协程同时启动，到达 200ms 时，调用 game.eat() 函数，这时将 eatContinuation 缓存下来，由于 feedContinuation 为空，所以 resumeContinuation 无法执行 resume
         * 由于 eatContinuation 没有 resume，所以 Logit.d(" #2 Eat: ${game.eat()} <<<") 没有返回值，一直处于挂起状态
         * 2.到达 300ms 时，创建一个随机数 food，这时打印 #1 Food: $food >>>
         * 3.会调用 game.feed("$food")，这时会将 feedContinuation 赋值，接着执行 eatContinuation 的 resumeContinuation，所以 eatContinuation 就会恢复
         * 4.因此 Logit.d(" #2 Eat: ${game.eat()} <<<") 会恢复执行，打印出上面生成的 food
         *
         * 5.接着 delay(200) ms 后，重新调用 game.eat()，会缓存新的 eatContinuation，同时会将 feedContinuation 进行 resume，所以 200ms Logit.d(" #1 Complete: ${game.feed("$food")}")恢复，输出
         * #1 Complete: 1，但是 eatContinuation 又处于挂起状态
         *
         * 6.然后 300ms 后，又生成新的 food，执行 game.feed("$food") 会缓存新的 feedContinuation，同时将 eatContinuation 恢复，所以输出 Logit.d(" #2 Eat: ${game.eat()} <<<") 会恢复执行，打印出上面生成的 food
         * 7.200ms 后重新调用 game.eat()，会缓存新的 eatContinuation，同时会将 feedContinuation 进行 resume，所以 200ms Logit.d(" #1 Complete: ${game.feed("$food")}")恢复，输出
         * #1 Complete: 2，但是 eatContinuation 又处于挂起状态
         *
         * 直到 3s 时间到达后，会将状态置位，输出默认值
         */
    }
}

suspend fun main() {
    eatGame()
}