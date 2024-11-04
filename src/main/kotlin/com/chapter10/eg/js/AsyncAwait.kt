package com.chapter10.eg.js

import com.chapter03.retrofit.RetrofitService
import com.utils.Logit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.*


/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-03 21:34
 *
 * Desc: AsyncAwait
 */
interface AsyncScope

suspend fun <T> AsyncScope.await(block: () -> Call<T>) = suspendCoroutine<T> { continuation ->
    val call = block()
    call.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                response.body()?.let {
                    continuation.resume(it)
                } ?: continuation.resumeWithException(NullPointerException())
            } else {
                continuation.resumeWithException(HttpException(response))
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            continuation.resumeWithException(t)
        }

    })
}

fun async(context: CoroutineContext = EmptyCoroutineContext, block: suspend AsyncScope.() -> Unit) {
    val completion = AsyncCoroutine(context)
    block.startCoroutine(completion, completion)
}

class AsyncCoroutine(override val context: CoroutineContext = EmptyCoroutineContext) : Continuation<Unit>, AsyncScope {
    override fun resumeWith(result: Result<Unit>) {
        result.getOrThrow()
    }

}

fun main() {
    val retrofit = RetrofitService.getRetrofit
    async {
        val user = await { retrofit.getRepository("Jetbrains", "kotlin") }
        Logit.d(user)
    }
}