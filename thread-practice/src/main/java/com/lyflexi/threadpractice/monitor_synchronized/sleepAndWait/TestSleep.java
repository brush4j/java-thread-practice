package com.lyflexi.threadpractice.monitor_synchronized.sleepAndWait;





public class TestSleep {
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
                    System.out.println("可以开始干活了"+System.currentTimeMillis());
                }
            }
        }, "小南").start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                synchronized (room) {
                    System.out.println("可以开始干活了");
                }
            }, "其它人").start();
        }

        Thread.sleep(1000);
        new Thread(() -> {
            // 这里不能加 synchronized (room)否则烟永远送不到，因为sleep不会释放锁
//            synchronized (room) {
//                hasCigarette = true;
//                System.out.println("烟到了噢！");
//            }
            hasCigarette = true;
            System.out.println("烟到了噢！");
        }, "送烟的").start();
    }

}
