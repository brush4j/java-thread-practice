package com.lyflexi.synclockpractice.juc.stampedLock;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/7/5
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class Main {
    public static void main(String[] args) {
//        rr();
        rw();
    }

    /**
     * 没有写操作，则乐观读锁永远验戳成功
     */
    private static void rr() {
        DataContainerStamped dataContainer = new DataContainerStamped(1);
        new Thread(() -> {
            dataContainer.read(1);
        }, "t1").start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(() -> {
            dataContainer.read(0);
        }, "t2").start();
    }

    /**
     * 写操作介入，乐观读锁验戳失败，乐观读锁就升级为了读锁
     */
    private static void rw() {
        DataContainerStamped dataContainer = new DataContainerStamped(1);
        new Thread(() -> {
            dataContainer.read(1);
        }, "t1").start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(() -> {
            dataContainer.write(0);
        }, "t2").start();
    }
}
