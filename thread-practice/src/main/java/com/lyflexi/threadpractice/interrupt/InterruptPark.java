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
     *这个静态方法interrupted做了两件事，将当前线程的中断状态清除即设为false，同时返回当前线程的中断协商标识：
     *
     * - 如果当前线程没有被中断协商则静态方法返回结果为false
     * - 如果当前线程在调用静态方法interrupted()之前，被调用了中断协商方法interrupt()，则该静态方法返回true
     */
    private static void test1() {
        Thread t1 = new Thread(() -> {
                log.debug("打断状态before..."+Thread.currentThread().isInterrupted());
                log.debug("静态方法返回结果：{}", Thread.interrupted());
                log.debug("打断状态after..."+Thread.currentThread().isInterrupted());
        });
        t1.start();
    }
    /**
     * LockSupport.park(); 被interrupte打断之后，无法再次进入park，原因是interrupte设置了中断标志为true
     * @throws InterruptedException
     */
    private static void test2() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("park...");
            LockSupport.park();
            log.debug("unpark...");
            log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
            LockSupport.park();
            log.debug("被interrupte打断之后，无法再次进入park...");
        }, "t1");
        t1.start();


//        Thread t1 = new Thread(() -> {
//            for (int i = 0; i < 5; i++) {
//                log.debug("park..."+i);
//                LockSupport.park();
//                log.debug("打断状态：{}", Thread.currentThread().isInterrupted());
//            }
//        });
//        t1.start();

        Thread.sleep(1000);
        t1.interrupt();
    }

    /**
     * 让线程t1能够重新被park阻塞的解决方案是t1除非调用这里的静态方法interrupted()将打断标记置为false，效果是
     * - 重设当前线程的打断标记为false，以便于下次可以再次park阻塞
     * - 虽然重设当前线程的打断标记为false，但是被外部线程中断协商interrupte()放行之后，当前线程的静态方法interrupted()返回的是中断协商的结果true
     * @throws InterruptedException
     */
    private static void test3() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("初始打断状态：{}", Thread.currentThread().isInterrupted());
            log.debug("park...");
            LockSupport.park();
            log.debug("unpark...");
            log.debug("被打断后的打断状态：{}", Thread.currentThread().isInterrupted());
            Thread.interrupted();
            log.debug("静态方法Thread.interrupted()重设false打断状态：{}", Thread.currentThread().isInterrupted());
            LockSupport.park();
            log.debug("被interrupte打断之后，无法再次进入park...");
        }, "t1");
        t1.start();

        Thread.sleep(1000);
        t1.interrupt();
    }
    /**
     * 测试假设有两个线程t1、t2，
     *
     * t1正处于LockSupport.park()阻塞中
     *
     * 此时线程t2调用了一次t1.interrupt()方法给t1放行。但是后面如果我们连续调用两次interrupted方法，第一次会返回true，第二次将返回false
     */
    private static void test4() {
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                log.debug("park..."+i);
                LockSupport.park();
                log.debug("打断状态：{}", Thread.interrupted());
                log.debug("打断状态：{}", Thread.interrupted());
            }
        });
        t1.start();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //打断协商一次
        t1.interrupt();
    }

    /**
     * 打断协商两次interrupt();
     *
     * 被打断线程的静态方法也调用两次
     */
    private static void test5() {
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
        //打断协商一次
        t1.interrupt();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //打断协商二次
        t1.interrupt();
    }

    public static void main(String[] args) throws InterruptedException {
//        test1();
//        test2();
        test3();
//        test4();
//        test5();
    }
}
