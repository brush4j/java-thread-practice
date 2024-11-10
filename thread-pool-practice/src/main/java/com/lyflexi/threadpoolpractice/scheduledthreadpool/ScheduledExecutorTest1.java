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
public class ScheduledExecutorTest1 {
    static ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);

    /**
     * task1和task2同时被打印
     * @param args
     */
    public static void main(String[] args) {
        
        pool.schedule(() -> {
            log.debug("task1");
        }, 1, TimeUnit.SECONDS);

        pool.schedule(() -> {
            log.debug("task2");
        }, 1, TimeUnit.SECONDS);
    }
}
