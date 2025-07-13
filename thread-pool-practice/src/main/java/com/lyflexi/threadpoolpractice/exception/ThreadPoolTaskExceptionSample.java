package com.lyflexi.threadpoolpractice.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @Author: lyflexi
 * @project: java-thread-practice
 * @Date: 2024/11/10 21:23
 */
@Slf4j
public class ThreadPoolTaskExceptionSample {
    static ExecutorService pool = Executors.newFixedThreadPool(1);

    /**
     * 控制台默认没有打印异常信息: submit不打印异常信息，只有execute会打印异常信息！，
     * @param args
     */
    public static void main(String[] args) {
        testExecute(pool);
    }

    /**
     * @description: execute会打印异常信息！，
     * @author: hmly
     * @date: 2025/7/13 14:38
     * @param: [pool]
     * @return: void
     **/
    private static void testExecute(ExecutorService pool) {
        pool.execute(() -> {
            log.debug("task");
            int i = 1 / 0;

        });
    }

    /**
     * @description: submit不打印异常信息！，
     * @author: hmly
     * @date: 2025/7/13 14:38
     * @param: [pool]
     * @return: void
     **/
    private static void testSubmit(ExecutorService pool) {
        pool.submit(() -> {
            log.debug("task");
            int i = 1 / 0;

        });
    }
}
