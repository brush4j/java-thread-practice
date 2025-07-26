package com.lyflexi.synclockpractice.juc.stampedLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.StampedLock;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/7/5
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Slf4j
public class DataContainerStamped {
    private int localData;
    private final StampedLock lock = new StampedLock();

    public DataContainerStamped(int data) {
        this.localData = data;
    }

    public int read() {
        long stamp = lock.tryOptimisticRead();
        log.debug("optimistic read locking...{}", stamp);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (lock.validate(stamp)) {
            log.debug("read finish...{}, data:{}", stamp, localData);
            return localData;
        }

        // 乐观读锁升级 - 读锁
        try {
            stamp = lock.readLock();
            log.debug("updating to read lock...{}", stamp);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("read finish...{}, data:{}", stamp, localData);
            return localData;
        } finally {
            lock.unlockRead(stamp);
            log.debug("read unlock {}", stamp);
        }
    }

    public void write(int newData) {
        long stamp = lock.writeLock();
        log.debug("write lock {}", stamp);
        this.localData = newData;
        lock.unlockWrite(stamp);
        log.debug("write unlock {}", stamp);
    }
}
