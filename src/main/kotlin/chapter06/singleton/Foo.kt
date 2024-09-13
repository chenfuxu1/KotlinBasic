package chapter06.singleton

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-12 0:02
 *
 * Desc:
 */
class Foo {
    // 伴生对象
    companion object {
        const val TAG = "Foo"

        @JvmStatic fun y() {
            println("$TAG y...")
        }

        // 生成的是静态的 Field
        @JvmField var z: Int = 200
    }

    // 生成的是非静态的 Field
    @JvmField var x: Int = 10
}

fun main() {
    val foo = Foo()
    println(foo.x)
    println(Foo.z)
}