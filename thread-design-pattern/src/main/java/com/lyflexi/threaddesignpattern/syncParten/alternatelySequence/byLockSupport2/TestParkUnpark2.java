package com.lyflexi.threaddesignpattern.syncParten.alternatelySequence.byLockSupport2;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: ly
 * @Date: 2024/3/18 22:02
 */
@Slf4j(topic = "c.TestParkUnpark")
public class TestParkUnpark2 {


    public static void main(String[] args) {
        ParkUnpark2 parkUnpark2 = new ParkUnpark2(5);
        Thread t1 = new Thread(() -> {
            parkUnpark2.print("a");
        });
        Thread t2 = new Thread(() -> {
            parkUnpark2.print("b");
        });
        Thread t3 = new Thread(() -> {
            parkUnpark2.print("c");
        });
        parkUnpark2.setThreads(t1, t2, t3);
        //启动者
        parkUnpark2.start();

    }
}
