package com.lyflexi.synclockpractice.sync.cigarette;


/**
 * 打印信息，可以看到存在两个问题：
 * - 即使烟只用了1秒就提前送到了，由于sleep在阻塞小南线程无法提前唤醒，必须死等2秒钟
 * - 小南线程阻塞期间不释放锁，两秒内导致其他无关线程也干不了活
 */

public class BySleep {
    static final Object room = new Object();
    static boolean hasCigarette = false; // 有没有烟

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            synchronized (room) {
                System.out.println("有烟没？[{}]"+ hasCigarette);
                if (!hasCigarette) {
                    System.out.println("没烟，先歇会！"+System.currentTimeMillis());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println("有烟没？[{}]"+hasCigarette);
                if (hasCigarette) {
                    System.out.println("小南可以开始干活了"+System.currentTimeMillis());
                }
            }
        }, "小南").start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                synchronized (room) {
                    System.out.println("其它人可以开始干活了");
                }
            }, "其它人").start();
        }

        Thread.sleep(1000);
        new Thread(() -> {
            hasCigarette = true;
            System.out.println("烟到了噢！");
        }, "送烟的").start();
    }

}
