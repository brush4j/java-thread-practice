package com.lyflexi.caspractice.striped64;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/6/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Slf4j
public class LockWithCas {
    public static void main(String[] args) {
        CasLock casLock = new CasLock();

        new Thread(() -> {
            casLock.lock();
            try {
                log.info("thread1");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            casLock.unlock();
        }).start();


        new Thread(() -> {
            casLock.lock();
            log.info("thread2");
            casLock.unlock();
        }).start();

    }

    /**
     * 使用cas自定义一把锁
     */
    static class CasLock{
        AtomicInteger state = new AtomicInteger(0);

        public void lock(){
            while(true){
                if (state.compareAndSet(0,1)) {
                    log.info("lock...");
                    break;
                }
            }
        }

        /**
         * 解锁不用比较，因为解锁的时候持有锁的线程只有一个当前线程
         */
        public void unlock(){
            state.set(0);
            log.info("unlock...");
        }
    }
}
