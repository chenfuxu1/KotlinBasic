package com.chapter12.coroutineadvanced.callbacktosuspend

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.utils.Logit
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-29 22:59
 *
 * Desc:
 */
suspend fun main() {
    Looper.prepareMainLooper()

    GlobalScope.launch {
        val handler = Handler(Looper.getMainLooper())
        val result = handler.run { "hello" }
        Logit.d(result)

        val delayedResult = handler.runDelay(1000) { "World" }
        Logit.d(delayedResult)

        Looper.getMainLooper().quit()
    }

    Looper.loop()
}

suspend fun <T> Handler.run(block: () -> T) = suspendCoroutine<T> { continuation ->
    post {
        try {
            continuation.resume(block())
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }
}

suspend fun <T> Handler.runDelay(delay: Long, block: () -> T) = suspendCancellableCoroutine<T> { continuation ->
    val message = Message.obtain(this) {
        try {
            continuation.resume(block())
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }.also {
        it.obj = continuation
    }

    continuation.invokeOnCancellation {
        this@runDelay.removeCallbacksAndMessages(continuation)
    }

    sendMessageDelayed(message, delay)

}