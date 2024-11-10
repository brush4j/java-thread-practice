package com.lyflexi.synclockpractice.juc.handsonLockBasedAQS;

/**
 * @Author: ly
 * @Date: 2024/3/12 16:55
 */
public class TestAqs {
    public static void main(String[] args) {
        MyLock lock = new MyLock();
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("locking...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
//                sleep(1);
            } finally {
                System.out.println("unlocking...");
                lock.unlock();
            }
        },"t1").start();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("locking...");
            } finally {
                System.out.println("unlocking...");
                lock.unlock();
            }
        },"t2").start();
    }
}
