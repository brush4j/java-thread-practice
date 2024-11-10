package com.lyflexi.threadpoolpractice.scheduledthreadpool;

import org.apache.tomcat.util.threads.TaskQueue;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: lyflexi
 * @project: java-thread-practice
 * @Date: 2024/11/10 21:27
 */
public class HandsOnScheduler {

    /**
     * 如何让每周四 18:00:00 定时执行任务？
     *
     * @param args
     */
    public static void main(String[] args) {
        //  获取当前时间
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now);

        LocalDateTime timeThursday = obtainThursday(now);

        // initailDelay 代表当前时间和周四的时间差
        long initailDelay = Duration.between(now, timeThursday).toMillis();
        // period 一周的间隔时间
        long period = 1000 * 60 * 60 * 24 * 7;

        executeSchedule(initailDelay, period, TimeUnit.MILLISECONDS);

    }

    /**
     * 执行定时任务
     *
     * @param initailDelay
     * @param period
     * @param timeUnit
     */
    private static void executeSchedule(long initailDelay, long period, TimeUnit timeUnit) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.scheduleAtFixedRate(() -> {
            System.out.println("running...");
        }, initailDelay, period, timeUnit);
    }

    /**
     * 根据当前时间修改为周四时间
     *
     * @param now
     * @return
     */
    private static LocalDateTime obtainThursday(LocalDateTime now) {
        // 获取周四时间
        LocalDateTime time = now.withHour(18).withMinute(0).withSecond(0).withNano(0).with(DayOfWeek.THURSDAY);
        // 如果 当前时间 > 本周周四，必须找到下周周四
        if (now.compareTo(time) > 0) {
            time = time.plusWeeks(1);
        }
        System.out.println(time);
        return time;
    }
}


      
