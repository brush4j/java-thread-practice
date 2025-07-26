package com.lyflexi.synclockpractice.juc.rwlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/7/26
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Slf4j
public class RWCache  //资源类，模拟一个简单的缓存
{
    private Integer cache = 0;

    ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void write(Integer v) {
        rwLock.writeLock().lock();
        try {
            log.info("[{}]\t正在写入",Thread.currentThread().getName());
            cache = v;
            //暂停毫秒
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("[{}]\t完成完入",Thread.currentThread().getName());
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public void read() {
        rwLock.readLock().lock();
        try {
            log.info("[{}]\t正在读取",Thread.currentThread().getName());
            //暂停200毫秒
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("[{}]\t完成读取,读到的值[{}]",Thread.currentThread().getName(),cache);
        } finally {
            rwLock.readLock().unlock();
        }
    }
}