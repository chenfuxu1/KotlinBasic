package com.chapter12.coroutineadvanced.callbacktosuspend

import com.chapter03.retrofit.RetrofitService
import com.utils.Logit
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-29 22:09
 *
 * Desc:
 */
suspend fun <T> Call<T>.await(): T = suspendCancellableCoroutine { continuation ->
    continuation.invokeOnCancellation {
        cancel()
    }

    enqueue(object : Callback<T> {
        override fun onResponse(p0: Call<T>, response: Response<T>) {
            response.takeIf { it.isSuccessful }?.body()?.also {
                continuation.resume(it)
            } ?: continuation.resumeWithException(HttpException(response))
        }

        override fun onFailure(p0: Call<T>, p1: Throwable) {
            continuation.resumeWithException(p1)
        }

    })
}

suspend fun main() {
   GlobalScope.launch {
       val retrofit = RetrofitService.getRetrofit
       val repository = retrofit.getRepository("Jetbrains", "kotlin").await()
       Logit.d(repository)
   }.cancelAndJoin()
}