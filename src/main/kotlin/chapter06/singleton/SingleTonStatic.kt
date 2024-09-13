package chapter06.singleton

/**
 * object 的静态成员 @JvmStatic
 * 其实 kotlin 是跨平台的语音，是没有静态成员、静态方法的
 * 但是加上 @JvmStatic 后，在 java 代码可以像静态成员、静态方法一样访问
 */
object SingleTonStatic {
    const val TAG = "SingleTonStatic"

    @JvmStatic var x: Int = 100

    @JvmStatic fun y() {
        println("$TAG y...")
    }
}