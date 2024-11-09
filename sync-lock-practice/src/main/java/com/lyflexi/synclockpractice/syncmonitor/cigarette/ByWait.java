package com.lyflexi.synclockpractice.syncmonitor.cigarette;


public class ByWait {
    static final Object room = new Object();
    static boolean hasCigarette = false;


    public static void main(String[] args) throws InterruptedException{
        new Thread(() -> {
            synchronized (room) {
                System.out.println("有烟没？[{}]"+ hasCigarette);
                if (!hasCigarette) {
                    System.out.println("没烟，先歇会！"+System.currentTimeMillis());
                    try {
                        room.wait(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("有烟没？[{}]"+ hasCigarette);
                if (hasCigarette) {
                    System.out.println("小南可以开始干活了"+System.currentTimeMillis());
                }
            }
        }, "小南").start();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                synchronized (room) {
                    System.out.println("其他人可以开始干活了");
                }
            }, "其它人").start();
        }

        Thread.sleep(1000);
        new Thread(() -> {
            synchronized (room) {
                hasCigarette = true;
                System.out.println("烟到了噢！");
                room.notify();
            }
        }, "送烟的").start();
    }

}
