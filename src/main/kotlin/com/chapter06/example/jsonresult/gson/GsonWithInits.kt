package com.chapter06.example.jsonresult.gson

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-20 23:46
 *
 * Desc: 测试 Gson 序列化
 *
 * 如果 PersonWithInits 不加 @PoKo 注解，且 invokeInitializers = false，则无法完成
 * init 的初始化，那么 firstName 就会发成空指针异常
 */
fun main(){
    val gson = GsonBuilder()
        .addSerializationExclusionStrategy(LazyExclusionStrategy)
        .addDeserializationExclusionStrategy(LazyExclusionStrategy)
        .create()
    println(gson.toJson(PersonWithInits("Zhang San", 333))) // {"name":"Zhang San","age":333}
    val personWithInits = gson.fromJson("""{"name":"Li Si","age":555}""", PersonWithInits::class.java)
    println(personWithInits.firstName) // Li
    println(personWithInits.lastName) // Si
}

// Exclude Lazy values.
object LazyExclusionStrategy: ExclusionStrategy {
    override fun shouldSkipClass(clazz: Class<*>) = false

    override fun shouldSkipField(f: FieldAttributes): Boolean {
        return f.declaredType == Lazy::class.java
    }
}