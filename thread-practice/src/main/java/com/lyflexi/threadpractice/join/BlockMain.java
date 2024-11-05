package com.lyflexi.threadpractice.join;

/**
 * @Author: ly
 * @Date: 2024/3/14 9:52
 */
public class BlockMain {
    public static void main(String[] args) {
        Thread t = new Thread(() -> System.out.println("test"));
        t.start();
        System.out.println("main1");
        try {
            t.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("main2");
    }
}
