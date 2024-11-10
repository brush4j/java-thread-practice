package com.lyflexi.threaddesignpattern.syncParten.fixedSequence.bySync;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Test25")
public class FixedSequenceBySync {
    static final Object lock = new Object();
    // 关键点，设置布尔值，表示 t2 是否运行过
    static boolean t2runned = false;

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                while (!t2runned) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("1");
            }
        }, "t1");


        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                log.debug("2");
                t2runned = true;
                lock.notify();
            }
        }, "t2");

        t1.start();
        t2.start();
    }
}
