package com.lyflexi.threadpoolpractice.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Description:
 * @Author: lyflexi
 * @project: java-thread-practice
 * @Date: 2024/11/10 21:23
 */
@Slf4j
public class ThreadPoolTaskExceptionSample3 {
    /**
     * 利用Callable和Future，当任务执行异常时候，get会取到异常信息
     * @param args
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        Future<Boolean> f = pool.submit(() -> {

            log.debug("task");
            int i = 1 / 0;
            return true;

        });
        
        log.debug("task:{}", f.get());
    }
}
