package chapter06.enumclass

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-13 23:44
 *
 * Desc: 实现接口，统一实现
 */
enum class State3: Runnable {
    Idle, Busy;

    override fun run() {
        println("统一实现接口方法")
    }
}