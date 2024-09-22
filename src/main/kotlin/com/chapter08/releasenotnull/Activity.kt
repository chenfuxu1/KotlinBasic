package com.chapter08.releasenotnull

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-22 17:10
 *
 * Desc: 对 bitmap 进行属性代理，代理类是不可空的
 * 释放的对象是 bitmap 的代理对象
 */
class Activity {
    private var bitmap by releasableNotNull<Bitmap>()

    fun onCreate() {
        println(::bitmap.isInitialized)
        bitmap = Bitmap(111, 222)
        println(::bitmap.isInitialized)
    }

    fun onDestroy() {
        println(::bitmap.isInitialized)
        ::bitmap.release()
        println(::bitmap.isInitialized)
    }
}