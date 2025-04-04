package com.lyflexi.threadpractice.creation;

/**
 * @Author: liuyanoutsee@outlook.com
 * @Date: 2025/4/4 16:11
 * @Project: java-thread-practice
 * @Version: 1.0.0
 * @Description: 组合Runnable接口（推荐）
 */
public class ACompositeRunnable {
    public static void main(String[] args) {
        // 创建一个Runnable对象
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("使用Runnable接口创建的线程运行中...");
            }
        };

        // 创建一个Thread对象，并将Runnable对象作为参数传入
        Thread thread = new Thread(runnable);

        // 启动线程, 必须由start启动线程
        thread.start();

        // 主线程输出
        System.out.println("主线程运行中...");
    }
}
