package com.chapter13.callbacktosuspend

import com.utils.Logit
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlin.concurrent.thread

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-03 0:03
 *
 * Desc:
 */
interface MultiPathsCallback<T> {
    fun onProgress(value: Int)

    fun onResult(value: T)

    fun onError(t: Throwable)

    fun onComplete()
}

fun startTask(callback: MultiPathsCallback<String>): Cancellable {
    val thread = thread {
        try {
            (0..100).forEach {
                Thread.sleep(50)
                callback.onProgress(it)
            }
            callback.onResult("done")
            callback.onComplete()
        } catch (e: Exception) {
            callback.onError(e)
        }
    }
    return Cancellable {
        thread.interrupt()
    }
}

/**
 * 定义枚举类，有参数就用 class，没有参数就用 object
 */
sealed interface Event
class OnProgress(val value: Int) : Event
class OnError(val t: Throwable) : Event
class OnResult<T>(val value: T) : Event
object OnComplete : Event

@OptIn(ExperimentalCoroutinesApi::class)
fun startTaskAsFlow() = callbackFlow {
    val cancellable = startTask(object : MultiPathsCallback<String> {
        override fun onProgress(value: Int) {
            trySendBlocking(OnProgress(value))
        }

        override fun onResult(value: String) {
            trySendBlocking(OnResult(value))
        }

        override fun onError(t: Throwable) {
            trySendBlocking(OnError(t))
        }

        override fun onComplete() {
            trySendBlocking(OnComplete)
            close()
        }

    })
    awaitClose {
        cancellable.cancel()
    }
}.conflate()

suspend fun main() {
    val scope = CoroutineScope(Dispatchers.Default)
    scope.launch {
        startTaskAsFlow().collect {
            when (it) {
                OnComplete -> Logit.d("OnComplete")
                is OnError -> Logit.d("Error: ${it.t}")
                is OnProgress -> Logit.d("OnProgress: ${it.value}")
                is OnResult<*> -> Logit.d("OnResult: ${it.value}")
            }
        }
    }
    delay(1000)
    scope.cancel()
}