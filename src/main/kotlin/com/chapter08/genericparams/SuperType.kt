package com.chapter08.genericparams

import java.lang.reflect.ParameterizedType

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-22 11:40
 *
 * Desc:
 */
abstract class SuperType<T> {
    // 拿到父类的 T
    val typeParameter by lazy {
        /**
         * this::class 是子类的类型
         * this::class.supertypes 是父类的 types
         */
        this::class.supertypes.first().arguments.first().type
    }

    val typeParameterJava by lazy {
        this.javaClass.genericSuperclass.safeAs<ParameterizedType>()?.actualTypeArguments?.first()
    }
}