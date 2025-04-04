package com.lyflexi.threadpoolpractice.handsonpool_1988_v4;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //你会发现到最后，核心线程数均销毁了，原因是发生了线程安全问题
        /**
         * - 核心线程为1
         * - 最大线程为3
         * - 超时时间为2
         * - 队列长度为2
         * - 客户提交了4个任务
         * 这个场景下核心线程数创建了1个，辅助线程数创建了1个，但你发现到最后，两个线程均销毁了，原因是发生了线程安全问题
         *
         *
         * 提交任务1START
         * 提交任务1END
         * 提交任务2START
         * 提交任务2END
         * 提交任务3START
         * 提交任务3END
         * 提交任务4START
         * 提交任务4END
         * Thread-0：任务1开始
         * Thread-1：任务4开始
         * Thread-0：任务1结束
         * Thread-1：任务4结束
         * Thread-0：任务2开始
         * Thread-1：任务3开始
         * Thread-0：任务2结束
         * Thread-1：任务3结束
         * Thread-1被销毁
         * Thread-0被销毁
         *
         * 根源是下面的代码多个线程可以同时访问，导致核心线程数被销毁
         *             // 池中线程数超过corePoolSize，尝试销毁一部分线程
         *             boolean tryDestroy = workers.size() > corePoolSize;
         *
         *             // 尝试销毁线程 && 已经超时了
         *             if (tryDestroy && timedOut) {
         *                 return null;
         *             }
         */
        //ThreadPool3使用原子计数器解决了线程安全问题
        ThreadPool4 threadPool = new ThreadPool4(1, 3, 2, TimeUnit.SECONDS, new ArrayBlockingQueue<>(2));

        for (int i = 1; i <= 4; i++) {
            int index = i;
            System.out.println("提交任务" + index + "START");
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "：任务" + index + "开始");
                    sleep(3);
                    System.out.println(Thread.currentThread().getName() + "：任务" + index + "结束");
                }
            });
            System.out.println("提交任务" + index + "END");
        }

        sleep(100);
    }

    private static void sleep(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            System.out.println("sleep exception");
        }
    }
}