package com.lyflexi.threadpractice.threadCreation;

/**
 * @Author: ly
 * @Date: 2024/2/6 13:59
 */
public class DependRunnable {
    public static void main(String[] args) {
        Thread lambdaThread = new Thread(() -> System.out.println("使用lambda表达式创建的线程运行中..."));
        lambdaThread.start();
    }
}
