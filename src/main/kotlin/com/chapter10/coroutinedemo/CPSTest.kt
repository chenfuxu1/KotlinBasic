package com.chapter10.coroutinedemo

import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-11-12 22:46
 *
 * Desc:
 */
object CPSTest {
    // 伪挂起函数
    suspend fun getUserName(): String {
        return "Tom"
    }

    // 真挂起函数
    suspend fun getRealUserName(): String {
        delay(1000)
        return "Tom"
    }

    private suspend fun test2(){
        suspendCancellableCoroutine<String> {
            print("suspendCancellableCoroutine test")
        }
    }
}

/*
package com.chapter10.coroutinedemo;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.DelayKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(
   mv = {1, 5, 1},
   k = 1,
   d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0011\u0010\u0003\u001a\u00020\u0004H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0005J\u0011\u0010\u0006\u001a\u00020\u0004H\u0086@ø\u0001\u0000¢\u0006\u0002\u0010\u0005\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007"},
   d2 = {"Lcom/chapter10/coroutinedemo/CPSTest;", "", "()V", "getRealUserName", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getUserName", "KotlinBasic"}
)
public final class CPSTest {
   @NotNull
   public static final CPSTest INSTANCE;

   @Nullable
   public final Object getUserName(@NotNull Continuation $completion) {
      return "Tom";
   }

   @Nullable
   public final Object getRealUserName(@NotNull Continuation var1) {
      Object $continuation;
      label20: {
         // 不是第一次进入，则走这里，把 continuation 转成 ContinuationImpl，ContinuationImpl 只会生成一个实例，不会每次都生成
         if (var1 instanceof <undefinedtype>) {
            $continuation = (<undefinedtype>)var1;
            if ((((<undefinedtype>)$continuation).label & Integer.MIN_VALUE) != 0) {
               ((<undefinedtype>)$continuation).label -= Integer.MIN_VALUE;
               break label20;
            }
         }

         // 如果是第一次进入 getRealUserName，则 ContinuationImpl 还没被创建，会走到这里，此时先去创建一个 ContinuationImpl
         $continuation = new ContinuationImpl(var1) {
            // 保存 invokeSuspend 回调时吐出来的结果
            Object result;
            // 协程状态机当前的状态
            int label;

            // 是状态机的入口，会将执行流程交给 getRealUserName 再次调用
            // 协程的本质，就是 CPS + 状态机
            // invokeSuspend 是一个抽象方法，当协程从挂起状态想要恢复时，就得调用这个 invokeSuspend，然后继续走状态机逻辑，继续执行后面的代码
            @Nullable
            public final Object invokeSuspend(@NotNull Object $result) {
               // callback 回调时会把结果带出来
               this.result = $result;
               this.label |= Integer.MIN_VALUE;
               // 开启协程状态机
               return CPSTest.this.getRealUserName(this);
            }
         };
      }

      // 将之前执行的结果取出来
      Object $result = ((<undefinedtype>)$continuation).result;
      // 挂起的标志, 如果需要挂起的话, 就返回这个 flag
      Object var4 = IntrinsicsKt.getCOROUTINE_SUSPENDED();

      // 状态机
      switch(((<undefinedtype>)$continuation).label) {
      case 0:
         // 检测异常
         ResultKt.throwOnFailure($result);
         // 将 label 的状态改成 1, 方便待会儿执行 delay 后面的代码
         ((<undefinedtype>)$continuation).label = 1;

         // 1.调用 DelayKt.delay 函数
         // 2.将 ContinuationImpl 传了进去
         // 3.DelayKt.delay 是一个挂起函数，正常情况下，它会立马返回一个值：IntrinsicsKt.COROUTINE_SUSPENDED（也就是这里的 flag ）
         // 表示该函数已被挂起，这里就直接 return 了，该函数被挂起
         // 4.恢复执行：在 DelayKt.delay 内部，到了指定的时间后就会调用 ContinuationImpl 这个 Callback 的 invokeSuspend
         // 5.invokeSuspend 中又将执行 getRealUserName 函数，同时将之前创建好的 ContinuationImpl 传入其中，开始执行后面的逻辑(label 为 1 的逻辑)，该函数继续往后面执行(也就是恢复执行)
         if (DelayKt.delay(1000L, (Continuation)$continuation) == var4) {
            return var4;
         }
         break;
      case 1:
         // 检测异常
         ResultKt.throwOnFailure($result);
          // label 1 这里没有 return, 而是会走到下面的 return "Tom" 语句
         break;
      default:
         throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
      }

      return "Tom";
   }

   private CPSTest() {
   }

   static {
      CPSTest var0 = new CPSTest();
      INSTANCE = var0;
   }
}
 */
