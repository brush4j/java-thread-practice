package com.lyflexi.synclockpractice.juc.handsonLockBasedAQS;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: ly
 * @Date: 2024/3/12 16:55
 */
@Slf4j(topic = "c.TestAqs")
public class TestAqs {
    public static void main(String[] args) {
        MyLock lock = new MyLock();
        new Thread(() -> {
            lock.lock();
            try {
                log.info("locking...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
//                sleep(1);
            } finally {
                log.info("unlocking...");
                lock.unlock();
            }
        },"t1").start();

        new Thread(() -> {
            lock.lock();
            try {
                log.info("locking...");
            } finally {
                log.info("unlocking...");
                lock.unlock();
            }
        },"t2").start();
    }
}
