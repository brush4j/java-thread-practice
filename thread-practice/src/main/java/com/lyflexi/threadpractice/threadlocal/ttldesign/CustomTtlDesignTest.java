package com.lyflexi.threadpractice.threadlocal.ttldesign;

import java.util.concurrent.*;

public class CustomTtlDesignTest {
    private final ThreadLocal<String> contextHolder = ThreadLocal.withInitial(() -> "Main Value");

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        
        CustomTtlDesignTest solution = new CustomTtlDesignTest();


        // 提交任务到线程池
        Future<String> future = executorService.submit(new DecorateTask(solution.contextHolder.get()));

        // 等待任务完成并获取结果
        String result = future.get();

        // 打印结果
        System.out.println("Result: " + result);

        // 关闭线程池
        executorService.shutdown();
    }
}