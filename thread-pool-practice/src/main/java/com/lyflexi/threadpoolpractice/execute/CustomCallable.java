package com.lyflexi.threadpoolpractice.execute;

import java.util.concurrent.Callable;

/**
 * @Description:
 * @Author: lyflexi
 * @project: java-thread-practice
 * @Date: 2024/11/9 16:26
 */
class CustomCallable implements Callable {
    //实现call方法，将此线程需要执行的操作声明在call()中
    @Override
    public Object call() {
        System.out.println("Callable子线程在进行计算偶数和");
        int evenSum = 0;
        for(int i = 0;i <= 100;i++){
            if(i % 2 == 0){
                evenSum += i;
            }
        }
        return evenSum;
    }
}