package com.chapter08.genericparams

import com.chapter04.times
import java.lang.reflect.ParameterizedType
import kotlin.reflect.full.declaredFunctions

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-22 11:38
 *
 * Desc: 获取泛型参数
 */
fun main() {
    // 1.拿到方法
    Api::class.declaredFunctions.forEach {
        println(it.name) // getUsers
    }

    val firstFunc = Api::class.declaredFunctions.first()
    println(firstFunc)
    /**
     * 2.firstFunc.returnType 拿到返回 type
     * firstFunc.returnType.arguments 找到返回类型的具体参数
     */
    firstFunc.returnType.arguments.forEach {
        println(it)
    }

    /**
     * 3.java 中获取
     */
    println("=" * 30)
    val declaredMethod = Api::class.java.getDeclaredMethod("getUsers")
    println(declaredMethod)
    // (declaredMethod.genericReturnType as ParameterizedType).actualTypeArguments.forEach {
    //     println(it)
    // }
    declaredMethod.genericReturnType.safeAs<ParameterizedType>()?.actualTypeArguments?.forEach {
        println(it)
    }

    /**
     * 4.获取父类的泛型参数
     */
    val subType = SubType()
    subType.typeParameter.let(::println) // kotlin.String
    subType.typeParameterJava.let(::println) // class java.lang.String
}

fun <T> Any.safeAs(): T? {
    return this as? T
}