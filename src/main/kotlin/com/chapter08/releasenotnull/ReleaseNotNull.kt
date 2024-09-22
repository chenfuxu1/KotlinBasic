package com.chapter08.releasenotnull

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-22 17:09
 *
 * Desc: 释放不可空类型
 */
fun <T : Any> releasableNotNull() = ReleasableNotNull<T>()

fun main() {
    val activity = Activity()
    activity.onCreate()
    activity.onDestroy()
}