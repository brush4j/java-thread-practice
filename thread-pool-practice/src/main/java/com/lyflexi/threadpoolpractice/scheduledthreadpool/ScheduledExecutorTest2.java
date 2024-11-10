package com.lyflexi.threadpoolpractice.scheduledthreadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: lyflexi
 * @project: java-thread-practice
 * @Date: 2024/11/10 21:09
 */
@Slf4j
public class ScheduledExecutorTest2 {
    static ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);

    /**
     * 由于线程池大小设置为了1，因此task2在task1之后2秒被打印
     * @param args
     */
    public static void main(String[] args) {
        
        pool.schedule(() -> {
            log.debug("task1");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 1, TimeUnit.SECONDS);

        pool.schedule(() -> {
            log.debug("task2");
        }, 1, TimeUnit.SECONDS);
    }
}
