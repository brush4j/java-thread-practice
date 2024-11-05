package com.lyflexi.threadpractice.designParten.syncParten.alternatelySequence.byLockSupport;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author: ly
 * @Date: 2024/3/18 22:01
 */
public class ParkUnpark {
    //使用ParkUnpark这里无需传递current线程，因为park()这个API就是阻塞当前线程
    public void print(String str, Thread next) {
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            System.out.print(str);
            LockSupport.unpark(next);
        }
    }

    private int loopNumber;

    public ParkUnpark(int loopNumber) {
        this.loopNumber = loopNumber;
    }
}
