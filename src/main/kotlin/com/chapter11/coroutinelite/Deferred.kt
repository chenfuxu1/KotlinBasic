package com.chapter11.coroutinelite

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-15 16:53
 *
 **/
interface Deferred<T> : Job {
    suspend fun await(): T
}