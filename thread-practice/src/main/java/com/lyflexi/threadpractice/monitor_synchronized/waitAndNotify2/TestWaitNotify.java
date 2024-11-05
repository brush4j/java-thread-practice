package com.lyflexi.threadpractice.monitor_synchronized.waitAndNotify2;

/**
 * @Author: ly
 * @Date: 2024/3/13 14:21
 */
public class TestWaitNotify {
    final static Object obj = new Object();

    public static void main(String[] args) throws InterruptedException {

        new Thread(() -> {
            synchronized (obj) {
                System.out.println("执行....");
                try {
                    obj.wait(); // 让线程在obj上一直等待下去
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("其它代码....");
            }
        },"t1").start();

        new Thread(() -> {
            synchronized (obj) {
                System.out.println("执行....");
                try {
                    obj.wait(); // 让线程在obj上一直等待下去
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("其它代码....");
            }
        },"t2").start();

        // 主线程两秒后执行
        Thread.sleep(500);
        System.out.println("唤醒 obj 上其它线程");
        synchronized (obj) {
//            obj.notify(); // 唤醒obj上一个线程
            obj.notifyAll(); // 唤醒obj上所有等待线程
        }
    }
}
