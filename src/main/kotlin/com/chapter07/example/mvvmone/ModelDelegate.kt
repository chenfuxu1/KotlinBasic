package com.chapter07.example.mvvmone

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-21 20:27
 *
 * Desc:
 */
class ModelDelegate<T : AbsModel>(val kClass: KClass<T>) : ReadOnlyProperty<Any, T> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return Models.run {
            kClass.get()
        }
    }
}

inline fun <reified T : AbsModel> modelOf(): ModelDelegate<T> {
    return ModelDelegate(T::class)
}