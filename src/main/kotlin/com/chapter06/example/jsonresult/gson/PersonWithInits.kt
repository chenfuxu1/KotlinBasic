package com.chapter06.example.jsonresult.gson

import com.chapter06.dataclass.PoKo

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-20 23:35
 *
 * Desc: 实体类带有 init 块
 */
@PoKo
data class PersonWithInits(val name: String, val age: Int) {
    val firstName by lazy {
        name.split(" ")[0]
    }

    val lastName by lazy {
        name.split(" ")[1]
    }
}