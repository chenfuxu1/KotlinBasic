package com.test.suspend

import kotlinx.coroutines.delay

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-07 16:42
 *
 * Desc: 挂起函数
 */

/**
 * 1.不会真正的挂起，suspend 关键字可以省略
 */
// suspend fun noSuspend1(): String {
//     return "noSuspend1"
// }

/**
 * 2.不会真正挂起，也没有返回值的函数，suspend 关键字可以省略
 */
// suspend fun noSuspendEmpty() {
//     // 没有返回值
// }


/**
 * 3.真正挂起函数
 * DelayKt.delay 是一个挂起函数，它会立马返回一个值 IntrinsicsKt.COROUTINE_SUSPENDED
 * 表示该函数已被挂起，这里就直接 return 了，该函数被挂起
 * 恢复执行：在 DelayKt.delay 内部，到了指定的时间后就会调用 ContinuationImpl 这个 Callback 的 invokeSuspend
 */
suspend fun suspendEmpty() {
    delay(1000)
}