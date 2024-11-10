package com.lyflexi.threaddesignpattern.syncParten.guardedSuspension.guardedSuspensionHasTime;

/**
 * @Author: ly
 * @Date: 2024/3/15 20:04
 */

public class GuardedObjectHasTime {

    private Object response;
    private final Object lock = new Object();
    //最终版代码
    public Object get(long timeout) {

        synchronized (lock) {
            long start = System.currentTimeMillis();
            long passTime = 0;
            while (response == null) {
                long waitTime = timeout - passTime;
                if (waitTime<=0){
                    break;
                }
                try {
                    lock.wait(waitTime);//兼容虚假唤醒，所谓虚假唤醒就是条件不满足的时候唤醒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                passTime = System.currentTimeMillis() - start;
            }

            return response;

        }
    }
    //思路版代码
//    public Object get(long timeout) {
//
//        synchronized (lock) {
//            long start = System.currentTimeMillis();
//            long passTime = 0;
//            while (response == null) {
//                if (passTime>=timeout){
//                    break;
//                }
//                try {
//                    lock.wait(timeout-passTime);//兼容虚假唤醒，所谓虚假唤醒就是条件不满足的时候唤醒
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                passTime = System.currentTimeMillis() - start;
//            }
//
//            return response;
//
//        }
//    }
    public void complete(Object response) {
        synchronized (lock) {
            // 条件满足，通知等待线程
            this.response = response;
            lock.notifyAll();
        }
    }
}
