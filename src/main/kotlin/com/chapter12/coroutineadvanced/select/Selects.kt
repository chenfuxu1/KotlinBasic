package com.chapter12.coroutineadvanced.select

import com.chapter03.retrofit.RetrofitService
import com.chapter03.retrofit.User
import com.google.gson.Gson
import com.utils.Logit
import kotlinx.coroutines.*
import kotlinx.coroutines.selects.select
import java.io.File

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-30 23:38
 *
 * Desc:
 */
data class Response<T>(val value: T, val isLocal: Boolean)

val localDir = File("localCache").also { it.mkdir() }

val gson = Gson()

fun CoroutineScope.getUserFromApi(login: String) = async(Dispatchers.IO) {
    val retrofit = RetrofitService.getRetrofit
    retrofit.getUserSuspend(login)
}

fun CoroutineScope.getUserFromLocal(login: String) = async(Dispatchers.IO) {
    File(localDir, login).takeIf { it.exists() }?.readText()?.let {
        gson.fromJson(it, User::class.java)
    }
}

fun cacheUser(login: String, user: User) {
    File(localDir, login).writeText(gson.toJson(user))
}

suspend fun main() {
    val login = "chenfuxu1"
    GlobalScope.launch {
        val localDeferred = getUserFromLocal(login)
        val remoteDeferred = getUserFromApi(login)

        val userResponse = select<Response<User?>> {
            // 解决多路复用，谁先到就使用谁
            localDeferred.onAwait {
                Response(it, true)
            }
            remoteDeferred.onAwait {
                Response(it, false)
            }
        }
        userResponse.value?.let { Logit.d(" userResponse: $it") }
        // 如果没有本地文件，将网络数据缓存起来
        userResponse.isLocal.takeIf { it }?.let {
            val userFromApi = remoteDeferred.await()
            cacheUser(login, userFromApi)
            Logit.d("userFromApi: $userFromApi")
        }
    }.join()
}