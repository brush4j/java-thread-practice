package com.lyflexi.threadlocalpractice.ttl;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: lyflexi
 * @project: debuginfo_jdkToFramework
 * @Date: 2024/7/27 12:06
 */
public class TTLByDecorateExcutor {
    private static TransmittableThreadLocal<String> threadLocal = new TransmittableThreadLocal<>();

    public static void main(String[] args) {
        // 创建一个固定大小的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        // 在主线程中设置TransmittableThreadLocal的值
        threadLocal.set("value-set-in-parent-before");

        // 使用TtlExecutors包装线程池
        ExecutorService ttlExecutorService = TtlExecutors.getTtlExecutorService(executorService);

        // 提交Runnable任务到线程池中
        ttlExecutorService.submit(() -> {
            String value = threadLocal.get();
            System.out.println("RunnableTask is running in thread: " + Thread.currentThread().getName() + ", TransmittableThreadLocal value: " + value);
        });
        // 提交Callable任务到线程池中
        ttlExecutorService.submit(() -> {
            String value = threadLocal.get();
            System.out.println("CallableTask is running in thread: " + Thread.currentThread().getName() + ", TransmittableThreadLocal value: " + value);
            return threadLocal.get();
        });

        //重新赋值
        threadLocal.set("value-set-in-parent-after");
        // 提交Runnable任务到线程池中
        ttlExecutorService.submit(() -> {
            String value = threadLocal.get();
            System.out.println("RunnableTask is running in thread: " + Thread.currentThread().getName() + ", TransmittableThreadLocal value: " + value);
        });
        // 提交Callable任务到线程池中
        ttlExecutorService.submit(() -> {
            String value = threadLocal.get();
            System.out.println("CallableTask is running in thread: " + Thread.currentThread().getName() + ", TransmittableThreadLocal value: " + value);
            return threadLocal.get();
        });
        
        // 关闭线程池
        ttlExecutorService.shutdown();
    }
}
