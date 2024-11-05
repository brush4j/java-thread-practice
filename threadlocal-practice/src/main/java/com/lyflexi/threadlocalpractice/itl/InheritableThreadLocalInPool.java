package com.lyflexi.threadlocalpractice.itl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: lyflexi
 * @project: debuginfo_jdkToFramework
 * @Date: 2024/7/27 9:39
 */
public class InheritableThreadLocalInPool {
    public static void main(String[] args) {
        InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
        inheritableThreadLocal.set(Thread.currentThread().getName());

        ExecutorService executor = Executors.newFixedThreadPool(20);

        // 提交20个任务到线程池
        for (int i = 0; i < 20; i++) {
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " : " + inheritableThreadLocal.get());
            });
        }
        inheritableThreadLocal.set("修改当前的主线程变量");

        for (int i = 0; i < 20; i++) {
            executor.execute(() -> {

                System.out.println(Thread.currentThread().getName() + " : " + inheritableThreadLocal.get());
            });
        }
        executor.shutdown();
    }

}

