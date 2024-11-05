package com.lyflexi.threadpractice.designParten.syncParten.fixedSequence.byCondition;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j(topic = "c.FixedSequenceByLock")
public class FixedSequenceByLock {
    static ReentrantLock lock = new ReentrantLock();
    static Condition condition = lock.newCondition();

    // 关键点，设置布尔值，表示 t2 是否运行过
    static boolean t2runned = false;


    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            lock.lock();

            try {
                log.debug("线程1进入try块");
                while (!t2runned) {
                    try {
                        condition.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.debug("1");
            }finally {
                lock.unlock();
            }
        }, "t1");


        Thread t2 = new Thread(() -> {
            lock.lock();

            try {
                log.debug("2");
                t2runned = true;
                condition.signal();
            } finally {
                lock.unlock();
            }


        }, "t2");

        t1.start();
        t2.start();
    }
}
