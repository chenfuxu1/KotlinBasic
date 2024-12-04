import com.chapter13.suspendfunc.SuspendTestKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-12-01 17:26
 * <p>
 * Desc:
 */
public class SuspendTest {
    public static void main(String[] args) {
        CompletableFuture<String> future = new CompletableFuture<>();
        /**
         * 如果不是挂起函数，直接返回结果
         * 如果是挂起函数，先返回 COROUTINE_SUSPENDED 标记
         */
        Object result = SuspendTestKt.INSTANCE.suspendableApi(new Continuation<String>() {
            @NotNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NotNull Object o) {
                if (o instanceof String) {
                    future.complete((String) o);
                } else {
                    future.completeExceptionally((Throwable) o);
                }
            }
        });

        /**
         * 如果不是 COROUTINE_SUSPENDED 挂起函数，直接 future.complete 回调输出结果
         * 如果是 COROUTINE_SUSPENDED 挂起函数，会等待挂起函数执行完毕，回调 resumeWith，然后 future.complete() 输出结果
         */
        if (result != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            future.complete((String) result);
        }

        System.out.println(future.join());
    }
}
