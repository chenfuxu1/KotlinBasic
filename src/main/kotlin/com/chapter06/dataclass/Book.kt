package com.chapter06.dataclass

import com.chapter04.times

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-12 23:46
 *
 * Desc: 数据类
 */
@PoKo
data class Book(
    val id: Long, val name: String, val author: Person
) {
    init {
        println("Book init...")
    }
}

data class Person(val id: Long, val name: String, val age: Int)

fun main() {
    val book = Book(0, "Kotlin in Action", Person(1, "Dmitry", 40))
    println(book)
    val copy = book.copy()
    println(copy)
    println("=" * 30)

    val book2 = Book::class.java.newInstance()


}