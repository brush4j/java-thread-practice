package com.lyflexi.threadpractice.interrupt;

import lombok.extern.slf4j.Slf4j;

/**
 * 如果t1线程处于被阻塞状态，例如处于sleep，wait，join等状态，当线程t2调用t1.interrupt()时，
 * 那么t1线程将立即退出被阻塞状态并且抛出interruptedException异常并且把中断状态清除，事后调用isInterrupted将返回false
 * @Author: ly
 * @Date: 2024/3/14 9:54
 */
@Slf4j(topic = "c.Test7")
public class InterruptSleep {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("enter sleep...");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    log.debug("wake up...");
                    e.printStackTrace();
                }
            }
        };
        t1.start();

        Thread.sleep(1000);
        log.debug("interrupt...");
        t1.interrupt();
        log.debug("打断标记:{}", t1.isInterrupted());
    }
}
