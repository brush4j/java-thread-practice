package com.lyflexi.threadpractice.creation;

/**
 * @Author: liuyanoutsee@outlook.com
 * @Date: 2025/4/4 16:13
 * @Project: java-thread-practice
 * @Version: 1.0.0
 * @Description: 当然你也可以继承+组合的方式使用Thread，重写Thread#run()的同时，也引入成员变量Runnable来组合
 */
public class CReWriteThreadAndCompositeRunnable {
    public static void main(String[] args) {

        // 创建一个Runnable对象
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("使用Runnable接口创建的线程运行中...");
            }
        };

        // 创建一个Thread对象，并将Runnable对象作为参数传入
        Worker worker = new Worker(runnable);

        // 启动线程, 必须由start启动线程
        worker.start();

        // 主线程输出
        System.out.println("主线程运行中...");
    }

   static class Worker extends Thread{
        private Runnable runnable;

        public Worker(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            //重写run的逻辑
            super.run();
            System.out.println("重写Thread类创建的线程运行中...");
        }

    }
}
