package com.lyflexi.synclockpractice.juc.countDownLatch;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/7/6
 * @description：倒计时锁CountDownLatch
 * @modifiedBy：
 * @version: 1.0
 */
@Slf4j
public class CountDownLatchThreadPool {
    final static ExecutorService service = Executors.newFixedThreadPool(4);

    /**
     * 虽然使用join也可以达到CountDownLatch的效果
     *
     * 但是join需要等待线程结束，往往线程池场景下线程是一直处于工作状态不会结束，这个时候使用join死等就不合适了
     *
     * 因此线程池场景需要搭配CountDownLatch来使用
     * @param args
     */
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(3);
        service.submit(() -> {
            log.debug("begin...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
            log.debug("end...{}", latch.getCount());
        });
        service.submit(() -> {
            log.debug("begin...");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
            log.debug("end...{}", latch.getCount());
        });
        service.submit(() -> {
            log.debug("begin...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            latch.countDown();
            log.debug("end...{}", latch.getCount());
        });
        service.submit(()->{
            try {
                log.debug("waiting...");
                latch.await();
                log.debug("wait end...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
