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
    public static void main(String[] args)  {
//        rr();
        rw();
    }

    /**
     * 没有写操作，则乐观读锁永远验戳成功
     */
    private static void rr() {
        DataContainerStamped dataContainer = new DataContainerStamped(0);
        new Thread(() -> {
            dataContainer.read();
        }, "t1").start();
        new Thread(() -> {
            dataContainer.read();
        }, "t2").start();
    }

    /**
     * 写操作介入，乐观读锁验戳失败，乐观读锁就升级为了读锁
     */
    private static void rw()  {
        DataContainerStamped dataContainer = new DataContainerStamped(0);
        //读操作耗时2000
        new Thread(() -> {
            dataContainer.read();
        }, "t1").start();

        //只睡1000，保证下面的写锁干预
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        new Thread(() -> {
            dataContainer.write(1);
        }, "t2").start();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //乐观读锁升级后，新的写线程无法干预了，被阻塞
        new Thread(() -> {
            dataContainer.write(1);
        }, "t3").start();
    }
}
