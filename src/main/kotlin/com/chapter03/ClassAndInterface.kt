package com.chapter03


/**
 * 类和接口
 */
abstract class AbsClass {
    abstract fun absMethod()

    open fun overriidable() {

    }

    // 不加 open 子类无法复写
    fun nonOverridable() {

    }
}

interface SimpleInf {
    val simpleProperty: Int

    fun simpleMethod()
}

// 继承抽象类 AbsClass，需要写成 AbsClass()
class SimpleClass(var x: Int, val y: String) : AbsClass(), SimpleInf {
    // 初始化必须赋值
    val z: Long = 0L

    override fun absMethod() {
        TODO("Not yet implemented")
    }

    // 重写 SimpleInf 的属性 simpleProperty
    override val simpleProperty: Int
        get() {
            return 2
        }

    override fun simpleMethod() {
        TODO("Not yet implemented")
    }

    fun y() {

    }
}

fun main() {
    val simpleClass = SimpleClass(11, "张三")
    println(simpleClass.simpleProperty)
    println(simpleClass.x)
    println(simpleClass.y)
    println(simpleClass.z)
    simpleClass.y()
}
