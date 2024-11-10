package com.lyflexi.threaddesignpattern.syncParten.guardedSuspension.guardedSuspensionHasTime;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: ly
 * @Date: 2024/3/15 20:10
 */
@Slf4j(topic = "c.GuardedObjectTest")
public class TestHasTime {
    public static void main(String[] args) {
        GuardedObjectHasTime guardedObject = new GuardedObjectHasTime();

        new Thread(()->{
            log.debug("begin");
            Object response = guardedObject.get(2000);//等待2s
            log.debug("结果是{}",response);

        },"t1").start();

        new Thread(()-> {
            log.debug("begin");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            guardedObject.complete(null);//等待1s后测试虚假唤醒，发现t1不会等待3s，正确的等待2s

        },"t2").start();


    }
}
