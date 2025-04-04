package com.lyflexi.threadpractice.creation;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @Author: ly
 * @Date: 2024/2/6 14:00
 */
public class DCompositeFutureTask {
    public static void main(String[] args) {
        //3.创建Callable接口实现类的对象
        MyCallable mc = new MyCallable();
        //4.将此Callable接口实现类的对象作为传递到FutureTask构造器中，创建FutureTask的对象
        FutureTask ft = new FutureTask<>(mc);
        //5.将FutureTask的对象作为参数传递到Thread类的构造器中，创建Thread对象，并调用start()
        new Thread(ft).start();

        System.out.println("主线程在执行任务");

        //6.get()接收返回值
        try {
            Object sum = ft.get();
            System.out.println("和为："+sum);
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("所有任务执行完毕");
    }
    //1.创建一个实现Callable的实现类
    static class MyCallable implements Callable {
        //2.实现call方法，将此线程需要执行的操作声明在call()中
        @Override
        public Object call() throws Exception {
            System.out.println("子线程在进行计算");
            int sum = 0;
            for (int i = 0;i < 5; i++) {
                sum = sum + i;
            }
            return sum;
        }
    }
}


