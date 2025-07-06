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
public class V2WaitNotifyall {
    /**
     * 水开了
     */
    static Boolean kettle = false;
    /**
     * 茶叶来了
     */
    static Boolean teaLeaf = false;
    static final Object lock = new Object();
    static boolean maked = false;

    public static void main(String[] args) {

        /**
         * 给水
         */
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
                kettle = true;//修改同步标记，水开了
                lock.notifyAll();
                while (Objects.equals(teaLeaf,false)) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (!maked) {
                    log.debug("老王拿开水泡茶");
                    maked = true;
                }
            }
        }, "老王").start();

        /**
         * 给茶
         */
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
                teaLeaf = true;//修改同步标记，茶来了
                lock.notifyAll();
                while (Objects.equals(kettle,false)) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (!maked) {
                    log.debug("小王拿开水泡茶");
                    maked = true;
                }
            }
        }, "小王").start();
    }

    /**
     * 解决了V1Join 的问题
     *   eg.小王等老王的水烧开了，小王泡茶，
     *   eg.反过来，老王等小王的茶叶拿来了，老王也可以泡茶
     *
     * 不过老王和小王需要相互等待，不如他们只负责各自的任务，泡茶交给第三人来做
     */
}
