package com.lyflexi.synclockpractice.juc.rwlock.wlockhunger;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/6/29
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class WriteLockHunger {
    public static void main(String[] args) throws InterruptedException {
//        rr();
        rw();

    }

    /**
     * 读读共享
     */
    private static void rr() {
        DataContainer dataContainer = new DataContainer();
        new Thread(() -> {
            dataContainer.read();
        }, "t1").start();

        new Thread(() -> {
            dataContainer.read();
        }, "t2").start();
    }

    /**
     * 读写互斥, 写锁必须等待读锁释放之后才能获取到，造成写锁饥饿现象
     */
    private static void rw() {
        DataContainer dataContainer = new DataContainer();
        new Thread(() -> {
            dataContainer.read();
        }, "t1").start();

        new Thread(() -> {
            dataContainer.write();
        }, "t2").start();
    }
}
