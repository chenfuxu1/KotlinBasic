package chapter04

/**
 * 中缀表达式
 */
fun main() {
    println("HelloWorld" rotate 5)

    val book = Book("三国演义")
    val desk = Desk("书桌")
    println(book on desk)
}

infix fun String.rotate(count: Int): String {
    val index = count % length
    return this.substring(index) + this.substring(0, index)
}

class Book(var name: String)

class Desk(var name: String)

infix fun Book.on(desk: Desk): String {
    return this.name + " is on the " + desk.name
}