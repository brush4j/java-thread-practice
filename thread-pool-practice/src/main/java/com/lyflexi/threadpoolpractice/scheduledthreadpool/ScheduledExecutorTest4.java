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
public class ScheduledExecutorTest4 {
    static ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);

    /**
     * scheduleAtFixedRate： 周期反复执行，周期为period,若执行花费大于period，则周期以业务执行花费为准，
     * 
     * 例如这里同时设置了业务执行花费为2秒，period为1秒，则执行周期最终为2秒
     * @param args
     */
    public static void main(String[] args) {

        log.debug("start...");
        pool.scheduleAtFixedRate(() -> {
            log.debug("running...");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, 1, 1, TimeUnit.SECONDS);
    }
}
