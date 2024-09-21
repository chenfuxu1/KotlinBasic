package com.chapter07.example.mvvmtwo

import kotlin.reflect.KProperty

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-21 20:27
 *
 * Desc:
 */
object ModelDelegate {
    operator fun <T : AbsModel> getValue(thisRef: Any, property: KProperty<*>): T {
        return Models.run {
            // property.name 传进来的属性名称
            property.name.capitalize().get()
        }
    }
}