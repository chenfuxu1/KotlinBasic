package com.chapter04

/**
 * 复数
 */
class Complex(var real: Double, var image: Double) {
    override fun toString(): String {
        return "$real + $image"
    }
}

operator fun Complex.plus(other: Complex): Complex {
    return Complex(this.real + other.real, this.image + other.image)
}

operator fun Complex.minus(other: Complex): Complex {
    return Complex(this.real - other.real, this.image - other.image)
}

operator fun Complex.times(other: Complex): Complex {
    return Complex(this.real * other.real, this.image * other.image)
}

operator fun Complex.div(other: Complex): Complex {
    return Complex(this.real / other.real, this.image / other.image)
}

operator fun Complex.get(index: Int): Double = when (index) {
    0 -> this.real
    1 -> this.image
    else -> throw IndexOutOfBoundsException()
}

fun main() {
    val c1 = Complex(2.0, 3.0)
    val c2 = Complex(3.0, 4.0)

    println(c2.plus(c1))
    println(c2.minus(c1))
    println(c2.times(c1))
    println(c2.div(c1))
    println(c2.get(0))
    println(c2.get(1))
}