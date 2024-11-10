package com.lyflexi.threadpoolpractice.threadpool.executeorsubmit;

/**
 * @Description:
 * @Author: lyflexi
 * @project: java-thread-practice
 * @Date: 2024/11/9 16:24
 */
public class CustomRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("Runnable子线程在进行计算奇数和");
        int oddSum = 0;
        for(int i = 0;i <= 100;i++){
            if(i % 2 != 0){
                oddSum += i;
            }
        }
        System.out.println("奇数和为："+oddSum);
        System.out.println("Runnable子线程执行完毕");
    }
}