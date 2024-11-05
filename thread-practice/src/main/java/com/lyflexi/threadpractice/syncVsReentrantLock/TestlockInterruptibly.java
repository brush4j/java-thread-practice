package com.lyflexi.threadpractice.syncVsReentrantLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: ly
 * @Date: 2024/3/18 16:43
 */
@Slf4j(topic = "c.TestInterrupt")
public class TestlockInterruptibly {
    public static void main(String[] args) {

        ReentrantLock lock = new ReentrantLock();

        Thread t1 = new Thread(() -> {
            log.debug("启动...");
            try {
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("等锁的过程中被打断,子线程返回");
                return;
            }
            try {
                log.debug("获得了锁");
            } finally {
                lock.unlock();
            }
        }, "t1");


        lock.lock();
        log.debug("主线程获得了锁");
        t1.start();
        try {
            Thread.sleep(1000);
            t1.interrupt();
            log.debug("执行打断");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

    }
}
