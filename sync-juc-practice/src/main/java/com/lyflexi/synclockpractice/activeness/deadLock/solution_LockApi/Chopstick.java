package com.lyflexi.synclockpractice.activeness.deadLock.solution_LockApi;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author: ly
 * @Date: 2024/3/18 15:05
 */

class Chopstick extends ReentrantLock {
    String name;

    public Chopstick(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "筷子{" + name + '}';
    }
}
