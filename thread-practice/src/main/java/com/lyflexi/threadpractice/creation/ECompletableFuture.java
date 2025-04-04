package com.lyflexi.threadpractice.creation;

import java.util.concurrent.*;

/**
 * @Author: ly
 * @Date: 2024/2/6 14:05
 */
public class ECompletableFuture {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService threadPool = Executors.newFixedThreadPool(3);
//        asyncFutureNormal(threadPool);
//        asyncFutureException(threadPool);
        asyncFutureHandle(threadPool);

    }

    private static void asyncFutureNormal(ExecutorService threadPool) throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "----come in");
            int result = ThreadLocalRandom.current().nextInt(10);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("-----1秒钟后出结果：" + result);
            return result;
        }, threadPool).whenCompleteAsync((v, e) -> {
            if (e == null) {
                System.out.println("-----计算完成，更新系统UpdateValue：" + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            System.out.println("异常情况：" + e.getCause() + "\t" + e.getMessage());
            return null;
        });

        System.out.println(Thread.currentThread().getName() + "线程先去忙其它任务");

        System.out.println(completableFuture.get());
    }

    private static void asyncFutureException(ExecutorService threadPool) {

        CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "----come in");
            int result = ThreadLocalRandom.current().nextInt(10);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("-----1秒钟后出结果：" + result);
            if (result > 2) {
                int i = 10 / 0;
            }
            return result;
        }, threadPool).whenCompleteAsync((v, e) -> {
            if (e == null) {
                System.out.println("-----计算完成，更新系统UpdateValue：" + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            System.out.println("异常情况：" + e.getCause() + "\t" + e.getMessage());
            return null;
        });

        threadPool.shutdown();
        System.out.println(Thread.currentThread().getName() + "线程先去忙其它任务");

    }


    private static void asyncFutureHandle(ExecutorService threadPool) throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("当前线程：" + Thread.currentThread().getId());
            System.out.println("CompletableFuture...");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 10/1;
        },threadPool).handle((t,u)->{ // R apply(T t, U u);
            System.out.println("handle:");
            if (t != null){
                System.out.println("存在返回结果:" + t);
                return 8;
            }
            if (u != null){
                System.out.println("存在异常:" + u);
                return 9;
            }
            return 5;

        });

        System.out.println(Thread.currentThread().getName() + "线程先去忙其它任务");
        //注意此处必须调用线程池的shutdown()方法，或者调用completableFuture2.get()方法来阻塞主线程,否则主线程结束之后，守护线程（子线程）也会提前终止
        Integer integer = completableFuture2.get();
        System.out.println(integer);
//        threadPool.shutdown();//调用shutdown()方法来等待守护线程完成任务。
    }
}
