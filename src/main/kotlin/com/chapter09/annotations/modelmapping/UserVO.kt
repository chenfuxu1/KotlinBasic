package com.chapter09.annotations.modelmapping

import java.lang.StringBuilder
import kotlin.reflect.KClass

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-22 16:34
 *
 * Desc:
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class FieldName(val name: String)

// 定义转换策略的注解
@Target(AnnotationTarget.CLASS)
annotation class MappingStrategy(val kClass: KClass<out NameStrategy>)

// 名称转换策略接口
interface NameStrategy {
    fun mapTo(name: String): String
}

// 下划线转驼峰
class UnderScoreToCamel: NameStrategy {
    override fun mapTo(name: String): String {
        return name.toCharArray().fold(StringBuilder()) { acc, c ->
            when(acc.lastOrNull()) {
                '_' -> acc[acc.lastIndex] = c.toUpperCase()
                else -> acc.append(c)
            }
            acc
        }.toString()
    }

}

// 驼峰转下划线
class CamelToUnderScore: NameStrategy {
    override fun mapTo(name: String): String {
        return name.toCharArray().fold(StringBuilder()) { acc, c ->
            when {
                c.isUpperCase() -> acc.append("_").append(c.toLowerCase())
                else -> acc.append(c)
            }
            acc
        }.toString()
    }
}

@MappingStrategy(CamelToUnderScore::class)
data class UserVO(
    val login: String,
    // @FieldName("avatar_url") // 这种，每个注解都有标记转换的名称，很麻烦
    val avatarUrl: String) {

}