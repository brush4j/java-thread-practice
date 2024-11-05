package com.lyflexi.threadpractice.monitor_synchronized.upgrade;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;


public class B_Biasable {
    public static void main(String[] args) throws InterruptedException {
        //先睡眠5秒，保证开启偏向锁
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object o = new Object();

        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        synchronized (o) {
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        // 新的线程尝试重新偏向，即使偏向锁不出现竞争，偏向锁也会升级为轻量级锁，这说明偏向锁是一次性的
        Thread.sleep(10000);

        new Thread(() -> {
            synchronized (o) {
                System.out.println("3"+ClassLayout.parseInstance(o).toPrintable());
            }
            System.out.println("4"+ClassLayout.parseInstance(o).toPrintable());
        }).start();
    }
}
