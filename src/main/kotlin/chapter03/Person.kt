package chapter03

class Person(age: Int, name: String) {
    /**
     * kotlin 中 var age: Int = age 表示一个 property
     * 等价于 java 中 int age = 0 加 set 方法加 get 方法
     *
     * field 等价于 java 中的 int age = 0
     */
    var age: Int = age // 表示一个 property
        get() {
            return field
        }
        set(value) {
            field = value
        }
    var name: String = name
}

fun main() {
    // 可以接收属性的引用
    val ageRef = Person::age
    val person = Person(11, "张三")
    /**
     * 参数 1：receiver
     * 参数 2：具体 value
     */
    ageRef.set(person, 100)
    println(ageRef.get(person))
}