package com.chapter11.coroutinelite.cancel

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-17 0:17
 *
 * Desc:
 */
sealed class CancelState {
    object InComplete : CancelState()

    class Complete<T>(val value: T? = null, val exception: Throwable? = null) : CancelState()

    object Cancelled: CancelState()

    override fun toString(): String {
        return "CancelState.${javaClass.simpleName}"
    }
}