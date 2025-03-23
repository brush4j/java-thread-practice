package com.lyflexi.threadpractice.interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: ly
 * @Date: 2024/2/24 19:35
 */
public class InterruptRunning {
    static volatile boolean isStop = false;
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println(Thread.currentThread().getName() + "\t isInterrupted()返回true，程序员自行写响应逻辑，比如设置t1线程终止");
                    System.out.println("-----t1的中断标志位：" + Thread.currentThread().isInterrupted());
                    break;
                }
                System.out.println("t1 -----hello interrupt api");
            }
        }, "t1");
        t1.start();

        System.out.println("-----t1的默认中断标志位：" + t1.isInterrupted());

        //暂停毫秒
        try {
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //t2向t1发出协商，将t1的中断标志位设为true希望t1停下来
        new Thread(() -> {
            t1.interrupt();
        }, "t2").start();
        //t1.interrupt();，t1也可以自己给自己协商

    }
}
