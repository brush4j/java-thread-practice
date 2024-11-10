package com.lyflexi.threaddesignpattern.syncParten.guardedSuspension;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: ly
 * @Date: 2024/3/15 20:04
 */

public class GuardedObject {

    private Object response;
    private final Object lock = new Object();
    public Object get() {
        synchronized (lock) {
        // 条件不满足则等待
            while (response == null) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return response;

        }
    }
    public void complete(Object response) {
        synchronized (lock) {
            // 条件满足，通知等待线程
            this.response = response;
            lock.notifyAll();
        }
    }
}
