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
public class ScheduledExecutorTest3 {
    static ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);

    /**
     * 即使task1带有异常， task2也能正常执行
     * @param args
     */
    public static void main(String[] args) {
        
        pool.schedule(() -> {
            log.debug("task1");
            try {
                int a = 10/0;
            } catch (Exception e) {
                //这里必须打印才能有异常信息
                e.printStackTrace();
            }
        }, 1, TimeUnit.SECONDS);

        pool.schedule(() -> {
            log.debug("task2");
        }, 1, TimeUnit.SECONDS);
    }
}
