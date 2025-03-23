package com.lyflexi.threadpractice.interrupt;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: hmly
 * @Date: 2025/3/23 20:07
 * @Project: java-thread-practice
 * @Version: 1.0.0
 * @Description: 测试打断已经终止的线程，打断标记为false
 */
@Slf4j
public class InterruptDead {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("我很快先结束：dead...");
            }
        };
        t1.start();
        log.debug("t1打断标记:{}", t1.isInterrupted());

        Thread.sleep(1000);
        log.debug("interrupt...");
        t1.interrupt();
        log.debug("t1打断标记:{}", t1.isInterrupted());
    }
}
