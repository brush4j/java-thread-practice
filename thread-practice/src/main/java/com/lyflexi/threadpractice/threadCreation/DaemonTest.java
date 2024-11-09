package com.lyflexi.threadpractice.threadCreation;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: ly
 * @Date: 2024/3/14 17:13
 */
@Slf4j(topic = "c.Test15")
public class DaemonTest {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                log.debug("子线程正在循环");
            }
            log.debug(Thread.currentThread()+"结束");
        }, "t1");
        t1.setDaemon(true);
        t1.start();

        Thread.sleep(1);
        log.debug("main-结束");
    }
}
