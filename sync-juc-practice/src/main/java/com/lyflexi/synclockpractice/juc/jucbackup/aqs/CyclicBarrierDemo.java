package com.lyflexi.synclockpractice.juc.jucbackup.aqs;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

//集齐7颗龙珠就能召唤神龙
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        // public CyclicBarrier(int parties, Runnable barrierAction) {}
        CyclicBarrier cyclicBarrier=new CyclicBarrier(7,()->{
            System.out.println("召唤龙珠");
        });
        for (int i = 1; i <=7; i++) {
            final int temp=i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"\t收集到了第"+temp+"颗龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
}

