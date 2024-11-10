package com.lyflexi.threadpoolpractice.threadpool.execute.submit;


import jdk.dynalink.beans.StaticClass;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Description:
 * @Author: lyflexi
 * @project: java-thread-practice
 * @Date: 2024/11/9 16:24
 */
public class ExecuteTest {
    public static void main(String[] args) {
        System.out.println("主线程在执行任务");
        //1. 提供指定线程数量的线程池
        ExecutorService service = Executors.newFixedThreadPool(10);

        //2.执行指定的线程的操作。需要提供实现Runnable接口或Callable接口实现类的对象

        try {
            Future<?> future = service.submit(new CustomCallable());//适合使用于Callable
            System.out.println("偶数和为：" + future.get());//阻塞获取结果
            System.out.println("Callable子线程执行完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //3.关闭连接池
        service.shutdown();
        System.out.println("所有任务执行完毕");
    }
   static class  CustomCallable implements Callable {
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
}
