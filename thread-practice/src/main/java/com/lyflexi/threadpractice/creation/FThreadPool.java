package com.lyflexi.threadpractice.creation;

import java.util.concurrent.*;

/**
 * @Author: liuyanoutsee@outlook.com
 * @Date: 2025/4/4 16:18
 * @Project: java-thread-practice
 * @Version: 1.0.0
 * @Description: 基于线程池
 */
public class FThreadPool {
    static ExecutorService pool = Executors.newFixedThreadPool(5);

    public static void main(String[] args) {
        Future<String> f = pool.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                System.out.println("子线程在进行计算");
                return "success";
            }
        });
        try {
            f.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        System.out.println("主线程在执行任务");
    }
}
