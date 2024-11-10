package com.lyflexi.threadpoolpractice.threadpool.ofexecutors;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description:
 * @Author: lyflexi
 * @project: java-thread-practice
 * @Date: 2024/11/10 19:24
 */

/**
 * 核心线程数 == 最大线程数（没有救急线程被创建），因此也无需超时时间
 * 阻塞队列是无界的，可以放任意数量的任务
 */
@Slf4j
public class FixedThreadPoolTest {
    public static void main(String[] args) {
        //线程工厂可以自定义线程名称
        ExecutorService pool = Executors.newFixedThreadPool(2, new ThreadFactory() {
            private AtomicInteger t = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "mypool_t" + t.getAndIncrement());
            }
        });

        pool.execute(() -> {
            log.debug("1");
        });

        pool.execute(() -> {
            log.debug("2");
        });

        pool.execute(() -> {
            log.debug("3");
        });
    }
}
