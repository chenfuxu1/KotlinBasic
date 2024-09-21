package com.chapter06.example.jsonresult.ks

import kotlinx.serialization.Serializable

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-20 23:35
 *
 * Desc: 实体类带有 init 块
 */
@Serializable
data class PersonWithInits(val name: String, val age: Int) {
    val firstName by lazy {
        name.split(" ")[0]
    }

    // @Transient
    val lastName = name.split(" ")[1]
}