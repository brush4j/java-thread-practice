package com.lyflexi.threadpoolpractice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: ly
 * @Date: 2024/2/22 10:12
 */
public class ThreadPoolExample {

    public static void main(String[] args) throws InterruptedException
    {
        ThreadLocal<Integer> tlField = ThreadLocal.withInitial(() -> 0);
        
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        try
        {
            for (int i = 0; i < 10; i++) {
                threadPool.submit(() -> {
                    try {
                        Integer beforeInt = tlField.get();
                        tlField.set(1 + tlField.get());
                        Integer afterInt = tlField.get();
                        System.out.println(Thread.currentThread().getName()+"\t"+"beforeInt:"+beforeInt+"\t afterInt: "+afterInt);
                    } finally {
                        tlField.remove();
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }

    }



}
