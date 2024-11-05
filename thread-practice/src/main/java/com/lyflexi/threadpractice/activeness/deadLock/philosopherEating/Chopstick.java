package com.lyflexi.threadpractice.activeness.deadLock.philosopherEating;

/**
 * @Author: ly
 * @Date: 2024/3/18 15:05
 */
public class Chopstick {
    String name;

    public Chopstick(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "筷子{" + name + '}';
    }
}
