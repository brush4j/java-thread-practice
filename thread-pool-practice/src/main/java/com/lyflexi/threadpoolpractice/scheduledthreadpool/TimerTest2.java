package com.lyflexi.threadpoolpractice.scheduledthreadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

/**
 * @Description:
 * @Author: lyflexi
 * @project: java-thread-practice
 * @Date: 2024/11/10 21:04
 */
@Slf4j
public class TimerTest2 {
    /**
     * timer是单线程的，由于task1抛出了异常，导致task2被执行
     * @param args
     */
    public static void main(String[] args) {
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task 1");
                int a = 10/0;
            }
        };
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                log.debug("task 2");
            }
        };

        log.debug("start...");
        timer.schedule(task1, 1000);
        timer.schedule(task2, 1000);
    }
}
