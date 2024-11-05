package com.lyflexi.threadpractice.threadlocal.ttl;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author: lyflexi
 * @project: debuginfo_jdkToFramework
 * @Date: 2024/7/27 12:06
 */
public class TTLByDecorateRunnable {

    private static TransmittableThreadLocal<String> threadLocal = new TransmittableThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        // 在主线程中设置TransmittableThreadLocal的值
        threadLocal.set("value-set-in-parent-before");

        // 在线程池中执行任务
        executorService.execute(TtlRunnable.get(() -> {
            String value = threadLocal.get();
            System.out.println("TransmittableThreadLocal value in new thread: " + value);
        }));

        threadLocal.set("value-set-in-parent-after");

        // 在线程池中重复利用线程执行任务
        executorService.execute(TtlRunnable.get(() -> {
            String value = threadLocal.get();
            System.out.println("TransmittableThreadLocal value in new thread: " + value);
        }));

        // 等待任务执行完成
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
    }
}
