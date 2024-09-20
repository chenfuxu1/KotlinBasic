package com.chapter06.delegate

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-08 22:26
 *
 * 属性代理：lazy
 **/
class Person(val name: String) {
    /**
     * public actual fun <T> lazy(initializer: () -> T): Lazy<T> = SynchronizedLazyImpl(initializer)
     * lazy 代理了 firstName 的 getter 方法
     *
     * public inline operator fun <T> Lazy<T>.getValue(thisRef: Any?, property: KProperty<*>): T = value
     * thisRef: 这里表示的就是 Person 实例
     * property：这里表示就是 Person::firstName
     */
    val firstName by lazy {
        name.split(" ")[0]
    }
}