package com.chapter07

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-21 16:37
 *
 * Desc: 逆变点的意义
 */
// 垃圾
open class Waste(val name: String = "垃圾")

// 干垃圾
class DryWaste(name: String = "干垃圾"): Waste(name)

class Dustbin<in T: Waste> {
    fun put(t: T) {
        println(t.name + " 放进了桶里")
    }
}

fun contravariant() {
    // 垃圾桶 - 子类
    val dustbin: Dustbin<Waste> = Dustbin<Waste>()
    // 干垃圾桶 - 父类
    val dryWasteDustbin: Dustbin<DryWaste> = dustbin
    println(dustbin::class.java)
    println(dryWasteDustbin::class.java)

    val waste = Waste()
    val dryWaste = DryWaste()

    dustbin.put(waste)
    dustbin.put(dryWaste) // 垃圾桶可以放任何垃圾

    // dryWasteDustbin.put(waste) 干垃圾桶不能放垃圾，只能放干垃圾
    dryWasteDustbin.put(dryWaste)
}

fun main() {
    contravariant()
}
