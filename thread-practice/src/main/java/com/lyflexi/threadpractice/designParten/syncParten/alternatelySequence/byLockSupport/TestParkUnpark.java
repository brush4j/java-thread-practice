package com.lyflexi.threadpractice.designParten.syncParten.alternatelySequence.byLockSupport;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author: ly
 * @Date: 2024/3/18 22:02
 */
@Slf4j(topic = "c.TestParkUnpark")
public class TestParkUnpark {
    static Thread t1;
    static Thread t2;
    static Thread t3;
    public static void main(String[] args) {
        ParkUnpark pu = new ParkUnpark(5);
        t1 = new Thread(() -> {
            pu.print("a", t2);
        });
        t2 = new Thread(() -> {
            pu.print("b", t3);
        });
        t3 = new Thread(() -> {
            pu.print("c", t1);
        });
        t1.start();
        t2.start();
        t3.start();

        //启动者
        LockSupport.unpark(t1);
    }
}
