package com.lyflexi.threadpractice.creation;

/**
 * @Author: liuyanoutsee@outlook.com
 * @Date: 2025/4/4 16:12
 * @Project: java-thread-practice
 * @Version: 1.0.0
 * @Description: 重写Thread类，与Runnable接口无关
 */
public class BReWriteThread {
    public static void main(String[] args) {
        // 创建一个Thread对象
        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("重写Thread类创建的线程运行中...");
            }
        };

        // 启动线程, 必须由start启动线程
        thread.start();

        // 主线程输出
        System.out.println("主线程运行中...");
    }
}
