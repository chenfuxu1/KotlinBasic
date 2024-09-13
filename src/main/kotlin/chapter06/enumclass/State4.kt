package chapter06.enumclass

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-13 23:44
 *
 * Desc: 实现接口，各自实现
 */
enum class State4 : Runnable {
    Idle {
        override fun run() {
            println("Idle 实现接口方法")
        }
    },
    Busy {
        override fun run() {
            println("Busy 实现接口方法")
        }
    };

    override fun run() {
        println("统一实现接口方法")
    }
}

fun main() {
    State4.Idle.run()
    State4.Busy.run()
}