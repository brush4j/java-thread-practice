package com.lyflexi.threadpoolpractice.threadpool.ofexecutors;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

/**
 * @Description:
 * @Author: lyflexi
 * @project: java-thread-practice
 * @Date: 2024/11/10 19:46
 */
@Slf4j
public class SingleThreadExecutorTest {
    /**
     * 自己创建一个单线程串行执行任务，如果任务执行失败而终止那么没有任何补救措施，
     * 
     * 而SingleThreadExecutor线程池当务执行失败而终止之后还会新建一个线程，保证池的正常工作
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        pool.execute(() -> {
            log.debug("1");
            int i = 1 / 0;
        });

        sleep(1000);

        pool.execute(() -> {
            log.debug("2");
        });

        pool.execute(() -> {
            log.debug("3");
        });
    }
}
