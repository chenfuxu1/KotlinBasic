package com.chapter08.basics

import com.chapter04.times
import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.typeOf

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-22 0:05
 *
 * Desc: 反射的基本概念
 */
@ExperimentalStdlibApi
fun main() {
    val cls: KClass<String> = String::class
    cls.java // 转换为 java 中的 class
    cls.java.kotlin // 转换为 Kotlin 中的 KClass

    val mapCls = Map::class
    println(cls) // class kotlin.String
    println(mapCls) // class kotlin.collections.Map

    /**
     * cls.declaredMemberProperties 可以拿到定义在 String 中的属性，扩展方法拿不到
     */
    val property = cls.declaredMemberProperties.firstOrNull()

    /**
     * B::class.objectInstance 返回 B 的单例对象实例
     */
    B::class.objectInstance?.f()

    /**
     * type 可以获取未擦除的或泛型参数
     */
    val mapType = typeOf<Map<String, Int>>()
    println(property) // val kotlin.String.length: kotlin.Int
    println(mapType) // kotlin.collections.Map<kotlin.String, kotlin.Int>
    println("=" * 30)
    mapType.arguments.forEach {
        println(it)
    }

    println("=" * 30)
    cls.members.forEach {
        println(it.name)
    }
}

object B {
    fun f() {

    }
}

class A {
    fun String.hello() {

    }
}