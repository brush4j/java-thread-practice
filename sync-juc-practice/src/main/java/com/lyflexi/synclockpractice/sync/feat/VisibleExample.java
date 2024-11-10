package com.lyflexi.synclockpractice.sync.feat;

/**
 * @Author: ly
 * @Date: 2024/2/24 17:00
 */
public class VisibleExample {

    /**
     * main 方法作为一个主线程
     */
    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        // 开启线程
        myThread.start();

        // （不加synchronized ）你会发现主线程永远都不会输出有点东西这一段代码，
        //  按道理线程MyThread改了flag变量，主线程Main也能访问到的呀？
        for (; ; ) {
            if (myThread.isFlag()) {
                System.out.println("有点东西");
            }
        }
//     （通过加synchronized 可以解决不可见性问题 ）
//      因为当一个线程进入 synchronized 代码块后，线程获取到锁，会清空本地内存，
//      然后从主内存中拷贝共享变量的最新值到本地内存作为副本，将修改后的副本值刷新到主内存中，执行代码，最后线程释放锁。
//      for (; ; ) {
//          synchronized (myThread) {
//              if (myThread.isFlag()) {
//                  System.out.println("主线程访问到 flag 变量");
//                }
//          }
//      }
    }



    /**
     * 子线程类
     */
    static class MyThread extends Thread {

        private boolean flag = false;

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 修改变量值
            flag = true;
            System.out.println("flag = " + flag);
        }

        public boolean isFlag() {
            return flag;
        }

        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }


}
