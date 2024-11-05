package com.lyflexi.threadpractice.monitor_synchronized.classLevel;

/**
 * @Author: ly
 * @Date: 2024/3/15 13:15
 */
public class SynBlockTest {
    static final Object lock = new Object();
    static int counter = 0;
    public static void main(String[] args) {
        synchronized (lock) {
            counter++;
        }
    }
}