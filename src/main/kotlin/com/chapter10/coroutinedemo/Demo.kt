package com.chapter10.coroutinedemo

import com.utils.Logit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-09 23:44
 *
 * https://www.jianshu.com/p/41fb0247e497
 *
 **/
/**
 * 从 launch 开始，在不阻塞当前线程的情况下启动一个新的协程，并将对协程的引用作为 Job 返回，CoroutineScope.launch
 * 中传入了三个参数，第一个 CoroutineContext 为协程的上下文，第二个 CoroutineStart，协程启动选项
 * 默认值为 CoroutineStart.DEFAULT
 * 第三个 block 即协程体中的代码，也就是上面例子中的 loadDataA 和 loadDataB
 * 1.coroutine.start --- Builders.common
 * public fun CoroutineScope.launch(context: CoroutineContext = EmptyCoroutineContext, start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> Unit): Job {
 *     val newContext = newCoroutineContext(context)
 *     val coroutine = if (start.isLazy)
 *         LazyStandaloneCoroutine(newContext, block) else StandaloneCoroutine(newContext, active = true)
 *     // 使用给定的代码块和启动策略启动此协程
 *     coroutine.start(start, coroutine, block)
 *     return coroutine
 * }
 *
 * 2.AbstractCoroutine 启动协程
 * public fun <R> start(start: CoroutineStart, receiver: R, block: suspend R.() -> T) {
 *     start(block, receiver, this)
 * }
 *
 * 3.执行到的是 CoroutineStart#invoke()
 * @InternalCoroutinesApi
 * public operator fun <R, T> invoke(block: suspend R.() -> T, receiver: R, completion: Continuation<T>): Unit =
 *     when (this) {
 *         // 默认的启动策略是 DEFAULT
 *         DEFAULT -> block.startCoroutineCancellable(receiver, completion)
 *         ATOMIC -> block.startCoroutine(receiver, completion)
 *         UNDISPATCHED -> block.startCoroutineUndispatched(receiver, completion)
 *         LAZY -> Unit // will start lazily
 *     }
 *
 * 4.Cancellable#startCoroutineCancellable
 * internal fun <R, T> (suspend (R) -> T).startCoroutineCancellable(receiver: R, completion: Continuation<T>,
 * onCancellation: ((cause: Throwable) -> Unit)? = null) = runSafely(completion) {
 *     createCoroutineUnintercepted(receiver, completion).intercepted().resumeCancellableWith(Result.success(Unit), onCancellation)
 * }
 *
 * 5.Cancellable#runSafely
 * private inline fun runSafely(completion: Continuation<*>, block: () -> Unit) {
 *     try {
 *         block()
 *     } catch (e: Throwable) {
 *         dispatcherFailure(completion, e)
 *     }
 * }
 *
 * 6.IntrinsicsJvm#createCoroutineUnintercepted
 * @SinceKotlin("1.3")
 * public actual fun <R, T> (suspend R.() -> T).createCoroutineUnintercepted(receiver: R, completion: Continuation<T>): Continuation<Unit> {
 *     val probeCompletion = probeCoroutineCreated(completion)
 *     return if (this is BaseContinuationImpl)
 *         create(receiver, probeCompletion)
 *     else {
 *         createCoroutineFromSuspendFunction(probeCompletion) {
 *             (this as Function2<R, Continuation<T>, Any?>).invoke(receiver, it)
 *         }
 *     }
 * }
 *
 * 7.ContinuationImpl#SuspendLambda 创建出 Continuation
 * @SinceKotlin("1.3")
 * // Suspension lambdas inherit from this class
 * internal abstract class SuspendLambda(public override val arity: Int, completion: Continuation<Any?>?) : ContinuationImpl(completion), FunctionBase<Any?>, SuspendFunction {
 *     constructor(arity: Int) : this(arity, null)
 *
 *     public override fun toString(): String =
 *         if (completion == null)
 *             Reflection.renderLambdaToString(this) // this is lambda
 *         else
 *             super.toString() // this is continuation
 * }
 *
 * 8.DispatchedContinuation#resumeCancellableWith
 * @InternalCoroutinesApi
 * public fun <T> Continuation<T>.resumeCancellableWith(result: Result<T>, onCancellation: ((cause: Throwable) -> Unit)? = null): Unit = when (this) {
 *     // 调到 resumeCancellableWith
 *     is DispatchedContinuation -> resumeCancellableWith(result, onCancellation) // result: "kotlin.Unit" onCancellation: null
 *     else -> resumeWith(result)
 * }
 *
 * 9.DispatchedContinuation#resumeCancellableWith
 * @Suppress("NOTHING_TO_INLINE")
 * inline fun resumeCancellableWith(result: Result<T>, noinline onCancellation: ((cause: Throwable) -> Unit)?) {
 *     val state = result.toState(onCancellation)
 *     if (dispatcher.isDispatchNeeded(context)) {
 *         _state = state
 *         resumeMode = MODE_CANCELLABLE
 *         dispatcher.dispatch(context, this)
 *     } else {
 *         executeUnconfined(state, MODE_CANCELLABLE) {
 *             if (!resumeCancelled(state)) {
 *                 resumeUndispatchedWith(result)
 *             }
 *         }
 *     }
 * }
 *
 * 我们接着往下看核心部分 Continuation
 * public interface Continuation<in T> {
 *     public val context: CoroutineContext
 *
 *     public fun resumeWith(result: Result<T>)
 * }
 *
 * 接口实现类 ContinuationImpl
 * Continuation 的具体实现是在 ContinuationImpl 类
 * internal abstract class ContinuationImpl(completion: Continuation<Any?>?, private val _context: CoroutineContext?
 * ) : BaseContinuationImpl(completion) {}
 *
 * 而 ContinuationImpl 继承自 BaseContinuationImpl，在 BaseContinuationImpl 中就可以看到 resumeWith 的具体实现
 * internal abstract class BaseContinuationImpl(public val completion: Continuation<Any?>?) : Continuation<Any?>, CoroutineStackFrame, Serializable {
 *     public final override fun resumeWith(result: Result<Any?>) {
 *         var current = this
 *         var param = result
 *         while (true) {
 *             probeCoroutineResumed(current)
 *             with(current) {
 *                 val completion = completion!! // fail fast when trying to resume continuation without completion
 *                 val outcome: Result<Any?> =
 *                     try {
 *                         val outcome = invokeSuspend(param)
 *                         if (outcome === COROUTINE_SUSPENDED) return
 *                         Result.success(outcome)
 *                     } catch (exception: Throwable) {
 *                         Result.failure(exception)
 *                     }
 *                 releaseIntercepted() // this state machine instance is terminating
 *                 if (completion is BaseContinuationImpl) {
 *                     current = completion
 *                     param = outcome
 *                 } else {
 *                     completion.resumeWith(outcome)
 *                     return
 *                 }
 *             }
 *         }
 *     }
 *  在 resumeWith 实现中，最核心的部分是在 while 循环中，调用 invokeSuspend 并且对返回的标志判断
 *  invokeSuspend 是 lifecycleScope.launch 开启协程内部执行的最后一个方法，invokeSuspend 之后即开始执行协程体中的内容
 */
fun main() {
    val scope = CoroutineScope(EmptyCoroutineContext)
    Logit.d("cfx coroutine start...")
    scope.launch {
        val num = loadDataA()
        loadDataB(num)
    }
    Logit.d("cfx coroutine end...")

    Thread.sleep(10000)
}

private suspend fun loadDataA(): Int {
    delay(3000)
    Logit.d("cfx loadDataA...")
    return 1
}

private suspend fun loadDataB(num: Int) {
    delay(1000)
    Logit.d("cfx loadDataB: $num")
}
