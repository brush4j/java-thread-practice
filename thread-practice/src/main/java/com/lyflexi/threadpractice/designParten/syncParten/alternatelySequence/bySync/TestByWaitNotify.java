package com.lyflexi.threadpractice.designParten.syncParten.alternatelySequence.bySync;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: ly
 * @Date: 2024/3/18 21:23
 */
@Slf4j(topic = "c.TestByWaitNotify")
public class TestByWaitNotify {
    public static void main(String[] args) {
        WaitNotify wn = new WaitNotify(1, 5);
        new Thread(() -> {
            wn.print("a", 1, 2);
        }).start();
        new Thread(() -> {
            wn.print("b", 2, 3);
        }).start();
        new Thread(() -> {
            wn.print("c", 3, 1);
        }).start();
    }
}
