package com.chapter08.releasenotnull

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-22 17:14
 *
 * Desc: 属性代理类
 * 该类一直是不可空的，实际释放的是内部的 value，value 是可空的
 */
class ReleasableNotNull<T : Any> : ReadWriteProperty<Any, T> {
    private var value: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return value ?: throw IllegalStateException("Not initialized or released already")
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        this.value = value
    }

    fun isInitialized() = value != null

    fun release() {
        value = null
    }
}

inline val KProperty0<*>.isInitialized: Boolean
    get() {
        isAccessible = true
        return (this.getDelegate() as? ReleasableNotNull<*>)?.isInitialized()
            ?: throw IllegalAccessException("Delegate is not an instance of ReleasableNotNull or is null")
    }

fun KProperty0<*>.release() {
    isAccessible = true
    (this.getDelegate() as? ReleasableNotNull<*>)?.release()
        ?: throw IllegalAccessException("Delegate is not an instance of ReleasableNotNull or is null")
}