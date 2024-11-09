package com.lyflexi.threadpractice.interrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * 
 * 
 * @Author: ly
 * @Date: 2024/3/14 12:22
 */
@Slf4j(topic = "c.Test14")
public class InterruptPark {



    /**
     * LockSupport.park(); 被interrupte打断之后，无法再次进入park，原因是interrupte设置了中断标志为true
     * @throws InterruptedException
     */
    private static void test1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("park...");
            LockSupport.park();
            log.debug("unpark...");
            log.debug("打断状态：{}", Thread.currentThread().isInterrupted());

            LockSupport.park();
            log.debug("被interrupte打断之后，无法再次进入park...");
        }, "t1");
        t1.start();

        Thread.sleep(1000);
        t1.interrupt();

    }


    /**
     * LockSupport.park(); 被interrupte打断之后，无法再次进入park，原因是interrupte设置了中断标志为true
     */
    private static void test2() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                log.debug("park..."+i);
                LockSupport.park();
                log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
            }
        });
        t1.start();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        t1.interrupt();
    }
    
    /**
     * 上述两个实例方法足够应对大部分场景了************************************************************************************
     * 再补充一个静态方法，这个方法做了两件事：返回当前线程的中断状态，并将当前线程的中断状态清除即设为false
     * 假设有两个线程t1、t2，线程t2调用了t1.interrupt()方法。后面如果我们连续调用两次interrupted方法，第一次会返回true，第二次将返回false
     *
     */
    private static void test3() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                log.debug("park..."+i);
                LockSupport.park();
                log.debug("打断状态：{}", Thread.interrupted());
            }
        });
        t1.start();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        t1.interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
        test3();
    }
}
