package com.lyflexi.threaddesignpattern.syncParten.alternatelySequence.byLockSupport2;

import java.util.concurrent.locks.LockSupport;

/**
 * @Author: ly
 * @Date: 2024/3/18 22:00
 */
public class ParkUnpark2 {
    private int loopNumber;
    private Thread[] threads;
    public ParkUnpark2(int loopNumber) {
        this.loopNumber = loopNumber;
    }
    //进一步封装线程数组于ParkUnpark2内部，让我们的SyncPark更加健壮
    public void setThreads(Thread... threads) {
        this.threads = threads;
    }
    public void print(String str) {
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            System.out.print(str);
            LockSupport.unpark(nextThread());
        }
    }
    //根据Thread.currentThread()寻找下一个线程
    private Thread nextThread() {
        Thread current = Thread.currentThread();
        int index = 0;
        for (int i = 0; i < threads.length; i++) {
            if(threads[i] == current) {
                index = i;
                break;
            }
        }
        if(index < threads.length - 1) {
            return threads[index+1];
        } else {
            return threads[0];
        }
    }
    public void start() {
        for (Thread thread : threads) {
            thread.start();
        }
        LockSupport.unpark(threads[0]);
    }
}
