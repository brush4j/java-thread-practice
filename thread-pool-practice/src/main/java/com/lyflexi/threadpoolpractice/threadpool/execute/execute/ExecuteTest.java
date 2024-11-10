package com.lyflexi.threadpoolpractice.threadpool.execute.execute;



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
        service.execute(new CustomRunnable());//适合适用于Runnable
        
        //3.关闭连接池
        service.shutdown();
        System.out.println("所有任务执行完毕");
    }
    static class CustomRunnable implements Runnable{
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
}
