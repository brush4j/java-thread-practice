package com.lyflexi.threadlocalpractice.ttl;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlCallable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lyflexi
 * @project: debuginfo_jdkToFramework
 * @Date: 2024/7/27 12:06
 */
public class TTLByDecorateCallable {

    private static TransmittableThreadLocal<String> threadLocal = new TransmittableThreadLocal<>();

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        // 在主线程中设置TransmittableThreadLocal的值
        threadLocal.set("value-set-in-parent-before");


        // 额外的处理，生成修饰了的对象ttlCallable
        Callable<String> ttlCallable1 = TtlCallable.get(()-> {
            String value = threadLocal.get();
            System.out.println("TransmittableThreadLocal value in new thread: " + value);
            return threadLocal.get();
        });
        executorService.submit(ttlCallable1);
        
        threadLocal.set("value-set-in-parent-after");
        Callable<String> ttlCallable2 = TtlCallable.get(()-> {
            String value = threadLocal.get();
            System.out.println("TransmittableThreadLocal value in new thread: " + value);
            return threadLocal.get();
        });
        executorService.submit(ttlCallable2);
        
        // 等待任务执行完成
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
    }
}
