package com.lyflexi.synclockpractice.juc;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierDemo {
    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    //当拦截线程数达到4时，便优先执行barrierAction，然后再执行被拦截的线程。
    private static final CyclicBarrier cb = new CyclicBarrier(4, () -> System.out.println("寝室四兄弟一起出发去球场"));

    private static class MyThread extends Thread {
        private String name;
        public MyThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(name + "开始从宿舍出发");
            try {
                cb.await();
                //线程的具体业务操作
                TimeUnit.SECONDS.sleep(1);
                System.out.println(name + "从楼底下出发");
                TimeUnit.SECONDS.sleep(1);
                System.out.println(name + "到达操场");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        String[] str = {"李明", "王强", "刘凯", "赵杰"};
        for (int i = 0; i < 4; i++) {
            threadPool.execute(new MyThread(str[i]));
        }

        try {
            Thread.sleep(4000);
            System.out.println("四个人一起到达球场，现在开始打球");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String[] str1= {"王二","洪光","雷兵","赵三"};
        for (int i = 0; i < 4; i++) {
            threadPool.execute(new MyThread(str1[i]));
        }
        try {
            Thread.sleep(4000);
            System.out.println("四个人一起到达球场，表示愿意一起打球，现在八个人开始打球");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }
}
