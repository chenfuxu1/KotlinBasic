import chapter05.KotlinInterface;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * Project: KotlinBasic
 * Create By: Chen.F.X
 * DateTime: 2024-09-08 13:56
 **/
public class SAM {
    public static void main(String[] args) {
        ExecutorService executorService = new ScheduledThreadPoolExecutor(5);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("java 匿名内部类");
            }
        });

        executorService.submit(() -> {
            System.out.println("java lambda 表达式");
        });

        testJavaInterface(() -> {
            System.out.println("java interface");
        });

        testKotlinInterface(() -> {
            System.out.println("kotlin interface, java can use");
        });
    }

    private static void testJavaInterface(JavaInterface javaInterface) {
        if (javaInterface != null) {
            javaInterface.run();
        }
    }

    private static void testKotlinInterface(KotlinInterface kotlinInterface) {
        if (kotlinInterface != null) {
            kotlinInterface.run();
        }
    }
}


