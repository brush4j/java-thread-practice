package com.lyflexi.threaddesignpattern.threadsyncmodel;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

import static java.lang.Thread.sleep;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/7/6
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Slf4j
public class V2WaitNotifyallThird {
    /**
     * 水开了
     */
    static Boolean kettle = false;
    /**
     * 茶叶来了
     */
    static Boolean teaLeaf = false;
    static final Object lock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            log.debug("洗水壶");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("烧开水");
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (lock) {
                kettle = true;
                lock.notifyAll();
            }
        }, "老王").start();

        new Thread(() -> {
            log.debug("洗茶壶");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("洗茶杯");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("拿茶叶");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (lock) {
                teaLeaf = true;
                lock.notifyAll();
            }
        }, "小王").start();

        /**
         * 第三者
         */
        new Thread(() -> {
            synchronized (lock) {
                while (Objects.equals(kettle,false) || Objects.equals(teaLeaf,false)) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("王夫人拿开水泡茶");
            }
        }, "王夫人").start();

    }

    /**
     * 引入第三者王夫人解耦
     */

}
