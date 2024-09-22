package com.chapter08.modelmapping

import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.primaryConstructor

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-22 16:33
 *
 * Desc: Model 映射
 */
fun main() {
    val userDTO = UserDTO(
        0,
        "荒天帝",
        "https://avatars2.githubusercontent.com/u/30511713?v=4",
        "https://api.github.com/users/bennyhuo",
        "https://github.com/bennyhuo"
    )
    val userVO: UserVO = userDTO.mapAs()
    println(userVO)

    val userMap = mapOf(
        "id" to 0,
        "login" to "Bennyhuo",
        "avatarUrl" to "https://api.github.com/users/bennyhuo",
        "url" to "https://api.github.com/users/bennyhuo"
    )

    val userVOFromMap: UserVO = userMap.mapAs()
    println(userVOFromMap)
}

inline fun <reified From : Any, reified To : Any> From.mapAs(): To {
    return From::class.memberProperties.map { it.name to it.get(this) }.toMap().mapAs()
}

inline fun <reified To : Any> Map<String, Any?>.mapAs(): To {
    return To::class.primaryConstructor!!.let {
        it.parameters.map { parameter ->
            parameter to (this[parameter.name]
                ?: (if (parameter.type.isMarkedNullable) null else throw IllegalArgumentException("${parameter.name} is required but missing")))
        }.toMap().let(it::callBy)
    }
}