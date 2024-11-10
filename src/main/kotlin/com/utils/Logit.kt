package com.utils

import java.text.SimpleDateFormat
import java.util.Calendar

/**
 * Project: CoroutineBase
 * Create By: Chen.F.X
 * DateTime: 2024-10-27 11:37
 *
 * Desc:
 */
object Logit {
    private val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")

    fun d(msg: Any) {
        val formatStr = simpleDateFormat.format(Calendar.getInstance().time)
        println("$formatStr\t${msg}")
    }

    fun d(tag: String, msg: Any) {
        val formatStr = simpleDateFormat.format(Calendar.getInstance().time)
        println("$formatStr\t$tag\t$msg")
    }

    fun d(tag: String, msg: Any, throwable: Throwable) {
        val formatStr = simpleDateFormat.format(Calendar.getInstance().time)
        println("$formatStr\t$tag\t$msg \n ${throwable.message}")
    }
}