package com.lyflexi.threaddesignpattern.defensivecopyParten;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

/**
 * @Description:
 * @Author: lyflexi
 * @project: java-thread-practice
 * @Date: 2024/11/10 11:35
 */
@Slf4j
public class SafeDataFormatSolution {
    /**
     * 直接加同步锁synchronized
     * @param args
     */
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 50; i++) {
            new Thread(() -> {
                synchronized (sdf) {
                    try {
                        log.debug("{}", sdf.parse("1951-04-21"));
                    }
                    catch (Exception e) {
                        log.error("{}", e);
                    }
                }
            }).start();
        }
    }
}
