package com.lyflexi.synclockpractice.commonwaitApi;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: lyflexi
 * @project: java-thread-practice
 * @Date: 2024/11/10 10:18
 */
public class TestSyncWaitNotify {

    public static void main(String[] args) {
        syncWaitNotify();
    }

    /**
     * 线程1：objectLock.wait();
     *
     * 线程2：objectLock.notify();
     *
     * 正确的等待唤醒顺序：线程t1先等待，线程t2后唤醒t1
     *
     * 若线程t2先唤醒t1，线程t1后调用等待，则会抛出两个异常：
     *
     * wait异常
     *
     * notify异常
     */
    private static void syncWaitNotify() {
        Object objectLock = new Object();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (objectLock) {
                System.out.println(Thread.currentThread().getName() + "\t ----come in");
                try {
                    objectLock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "\t ----被唤醒");
            }
        }, "t1").start();

//        //暂停几秒钟线程
//        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            synchronized (objectLock) {
                objectLock.notify();
                System.out.println(Thread.currentThread().getName() + "\t ----发出通知");
            }
        }, "t2").start();
    }
}
