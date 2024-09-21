package com.chapter07

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-21 16:17
 *
 * Desc: 协变点的意义
 */
// 书籍
interface Book

// 教育书籍
interface EduBook : Book

/**
 * 协变点主要是泛型类型作为生产者的情况
 * 子类提供子类，父类提供父类
 * 子类也可以提供父类
 */
class BookStore<out T : Book> {
    fun getBook(): T { // 协变点
        TODO()
    }
}

fun covariant() {
    // 教育书店
    val eduBookStore: BookStore<EduBook> = BookStore<EduBook>()
    // 书店
    val bookStore: BookStore<Book> = eduBookStore

    val book: Book = bookStore.getBook()
    val eduBook: EduBook = eduBookStore.getBook()
    // val eduBook2: EduBook = bookStore.getBook() 获取不到，返回的是父类，拿不到子类的类型
}
