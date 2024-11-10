package com.lyflexi.synclockpractice.activeness.multiLock;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: ly
 * @Date: 2024/3/18 14:06
 */
@Slf4j(topic = "c.Room")
public class Room {
    private final Object studyRoom = new Object();

    private final Object bedRoom = new Object();

    public void sleep() throws InterruptedException {
        synchronized (bedRoom) {
            log.debug("sleeping 2 小时");
            Thread.sleep(2000);
        }
    }

    public void study() throws InterruptedException{
        synchronized (studyRoom) {
            log.debug("study 1 小时");
            Thread.sleep(1);
        }
    }
}
