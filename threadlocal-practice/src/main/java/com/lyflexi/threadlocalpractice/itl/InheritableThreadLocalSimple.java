package com.lyflexi.threadlocalpractice.itl;

/**
 * @Author: lyflexi
 * @project: debuginfo_jdkToFramework
 * @Date: 2024/7/27 9:39
 */
public class InheritableThreadLocalSimple {
    private static InheritableThreadLocal<String> threadLocal = new InheritableThreadLocal<>();
    public static void main(String[] args) throws InterruptedException{
        threadLocal.set("mainThread");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String value = threadLocal.get();
                System.out.println(Thread.currentThread().getName()+":" + value);
            }
        });
        thread.start();
        Thread.sleep(10);
        System.out.println(Thread.currentThread().getName() + " : " + threadLocal.get());

    }
}

