package com.lyflexi.threadpoolpractice.handsonpool_ss_v2;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author liuyanoutsee@outlook.com
 **/
public class Main {
    public static void main(String[] args) {
//        test01();
//        test02();
        test03();
    }

    /**
     * 测试充分利用容量：核心线程+辅助线程+阻塞队列=6
     *
     * 总共四个线程：
     * 核心：Thread-0 Thread-1
     * 辅助：Thread-2 Thread-3
     * 最终Thread-2 Thread-3辅助消亡
     *
     * 符合预期
     */
    public static void test01(){
        ThreadPoolV2 threadPoolV2 = new ThreadPoolV2(2, 4, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2),new ThrowRejectHandle());
        for (int i = 0; i < 6; i++) {
            final int taski = i+1;
            threadPoolV2.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "@执行任务" + taski +" 结束...");
            });
        }
        System.out.println("主线程没有被阻塞");
    }

    /**
     * 测试极端情况，核心线程+辅助线程+阻塞队列=6<8，则抛异常
     *
     * 现象是任务7和任务8丢失，相应的execute方法执行失败
     *
     * 符合预期
     */
    public static void test02(){
        ThreadPoolV2 threadPoolV2 = new ThreadPoolV2(2, 4, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2),new ThrowRejectHandle());
        for (int i = 0; i < 8; i++) {
            final int taski = i+1;
            threadPoolV2.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "@执行任务" + taski +" 结束...");
            });
        }
        System.out.println("主线程没有被阻塞");
    }

    /**
     * 测试极端情况，核心线程+辅助线程+阻塞队列=6<8，自定义拒绝策略，丢弃阻塞队列中已有的一个任务。执行最新提交的任务
     *
     * 现象是有两个任务丢失，原因是当任务7和任务8到来的时候，阻塞队列满，队列满则从阻塞队列中连续丢弃两个阻塞任务，立即执行最新到来的任务
     *
     * 但不抛异常
     */
    public static void test03(){
        ThreadPoolV2 threadPoolV2 = new ThreadPoolV2(2, 4, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2),new DiscardRejectHandle());
        for (int i = 0; i < 8; i++) {
            final int taski = i+1;
            threadPoolV2.execute(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + "@执行任务" + taski +" 结束...");
            });
        }
        System.out.println("主线程没有被阻塞");
    }

}
