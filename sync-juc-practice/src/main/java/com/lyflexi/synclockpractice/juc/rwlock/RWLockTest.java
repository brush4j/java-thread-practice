package com.lyflexi.synclockpractice.juc.rwlock;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/7/26
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class RWLockTest {
    public static void main(String[] args) {
        RWCache RWCache = new RWCache();

        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            new Thread(() -> {
                RWCache.write(finalI);
            }, String.valueOf("写线程" + i)).start();

            new Thread(() -> {
                RWCache.read();
            }, String.valueOf("读线程" + i)).start();
        }

    }
}
