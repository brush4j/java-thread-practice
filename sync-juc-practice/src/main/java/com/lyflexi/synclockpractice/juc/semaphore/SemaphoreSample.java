package com.lyflexi.synclockpractice.juc.semaphore;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/7/6
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Slf4j
public class SemaphoreSample {
    public static void main(String[] args) {
        // 1. 创建 semaphore 对象
        Semaphore semaphore = new Semaphore(3);

        // 2. 10个线程同时运行
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    log.debug("running...");
                    Thread.sleep(1000);
                    log.debug("end...");
                } catch (java.lang.InterruptedException e){

                }
                    finally {
                    semaphore.release();
                }
            }).start();
        }
    }
}
