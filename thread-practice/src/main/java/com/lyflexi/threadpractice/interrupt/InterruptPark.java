package com.lyflexi.threadpractice.interrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author: ly
 * @Date: 2024/3/14 12:22
 */
@Slf4j(topic = "c.Test14")
public class InterruptPark {
    private static void test2() {
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

    private static void test() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("park...");
            LockSupport.park();
            log.debug("unpark...");
            log.debug("打断状态：{}", Thread.currentThread().isInterrupted());

            LockSupport.park();
            log.debug("unpark...");
        }, "t1");
        t1.start();

        Thread.sleep(1000);
        t1.interrupt();

    }

    public static void main(String[] args) throws InterruptedException {
        test2();
    }
}
