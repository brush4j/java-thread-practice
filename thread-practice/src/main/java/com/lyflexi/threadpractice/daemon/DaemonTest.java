package com.lyflexi.threadpractice.daemon;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: ly
 * @Date: 2024/3/14 17:13
 * 默认情况下，Java 进程需要等待所有线程都运行结束，才会结束，否则进程不会终止。
 *
 * 有一种特殊的线程叫做守护线程，只要其它主线程运行结束了，即使守护线程的代码没有执行完也会强制结束，整个进程终止。
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
