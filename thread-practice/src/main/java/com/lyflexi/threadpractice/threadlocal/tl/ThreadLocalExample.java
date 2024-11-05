package com.lyflexi.threadpractice.threadlocal.tl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @Author: lyflexi
 * @project: jdk-practice
 * @Date: 2024/11/5 18:46
 */
public class ThreadLocalExample {
    public static void main(String[] args) throws InterruptedException {

        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set(Thread.currentThread().getName());
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {

            System.out.println("子线程的threadloacl值为空"+Thread.currentThread().getName() + " : " + threadLocal.get());
        });
        Thread.sleep(10);
        System.out.println("主线程的threadloacl值为"+Thread.currentThread().getName() + " : " + threadLocal.get());
        executor.shutdown();
    }
}