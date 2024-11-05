package com.lyflexi.threadpractice.monitor_synchronized.notifyAll;

public class TestNotifyAll {
    static final Object room = new Object();
    static boolean hasCigarette = false;
    static boolean hasTakeout = false;

    // 虚假唤醒
    public static void main(String[] args) throws InterruptedException{
        new Thread(() -> {
            synchronized (room) {
                System.out.println("有烟没？[{}]"+ hasCigarette);
                if (!hasCigarette) {
                    System.out.println("没烟，先歇会！"+System.currentTimeMillis());
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

