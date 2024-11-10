package com.lyflexi.synclockpractice.activeness.deadLock.philosopherEating;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @Author: ly
 * @Date: 2024/3/18 15:06
 */
@Slf4j(topic = "c.Philosopher")
class Philosopher extends Thread {
    Chopstick left;
    Chopstick right;

    public Philosopher(String name, Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    @Override
    public void run() {
        while (true) {
            //　尝试获得左手筷子
            synchronized (left) {
                // 尝试获得右手筷子
                synchronized (right) {
                    try {
                        eat();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

    Random random = new Random();
    private void eat() throws InterruptedException {
        log.debug("eating...");
        Thread.sleep(1000);
    }
}

