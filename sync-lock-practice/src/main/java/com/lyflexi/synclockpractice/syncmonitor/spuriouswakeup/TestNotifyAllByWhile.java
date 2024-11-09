package com.lyflexi.synclockpractice.syncmonitor.spuriouswakeup;

/**
 * 一个线程可能会在被notifyAll时虚假唤醒，
 * 
 * 解决虚假唤醒的方式是将if条件判断改为while条件判断，形如while+wait+notifyAll组合的方式
 */
public class TestNotifyAllByWhile {
    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;

    // 虚假唤醒
    public static void main(String[] args) throws InterruptedException{
        new Thread(() -> {
            synchronized (room) {
                System.out.println("有烟没？[{}]"+ hasCigarette);
                while (!hasCigarette) {
                    System.out.println("没烟，先歇会！");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("有烟没？[{}]"+ hasCigarette);
                if (hasCigarette) {
                    System.out.println("可以开始干活了"+System.currentTimeMillis());
                } else {
                    System.out.println("没干成活...");
                }
            }
        }, "小南").start();

        new Thread(() -> {
            synchronized (room) {
                Thread thread = Thread.currentThread();
                System.out.println("外卖送到没？[{}]"+ hasTakeout);
                if (!hasTakeout) {
                    System.out.println("没外卖，先歇会！"+System.currentTimeMillis());
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("外卖送到没？[{}]"+ hasTakeout);
                if (hasTakeout) {
                    System.out.println("可以开始干活了"+System.currentTimeMillis());
                } else {
                    System.out.println("没干成活...");
                }
            }
        }, "小女").start();

        Thread.sleep(1000);
        new Thread(() -> {
            synchronized (room) {
                hasTakeout = true;
                System.out.println("外卖到了噢！");
                room.notifyAll();
            }
        }, "送外卖的").start();


    }

}
