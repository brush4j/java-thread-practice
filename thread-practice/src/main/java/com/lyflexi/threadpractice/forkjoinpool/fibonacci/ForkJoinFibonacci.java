package com.lyflexi.threadpractice.forkjoinpool.fibonacci;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinWorkerThread;
import java.util.concurrent.RecursiveTask;

/**
 * @Author: ly
 * @Date: 2024/3/28 20:44
 */
@Slf4j(topic = "c.ForkJoinFibonacci")
public class ForkJoinFibonacci {
    public static void main(String[] args) {
        //forkjoin线程数量
        int ForkJoinNum = 5;
        //求斐波那契f(20)
        int n = 20;

        // 为了追踪子线程名称，需要重写 ForkJoinWorkerThreadFactory 的方法
        final ForkJoinPool.ForkJoinWorkerThreadFactory factory = pool -> {
            final ForkJoinWorkerThread worker = ForkJoinPool.defaultForkJoinWorkerThreadFactory.newThread(pool);
            worker.setName("my-thread" + worker.getPoolIndex());
            return worker;
        };

        //创建分治任务线程池，可以追踪到线程名称
        ForkJoinPool forkJoinPool = new ForkJoinPool(ForkJoinNum, factory, null, false);

        // 快速创建 ForkJoinPool 方法
        // ForkJoinPool forkJoinPool = new ForkJoinPool(4);

        //创建分治任务
        Fibonacci fibonacci = new Fibonacci(n);

        //调用 invoke 方法启动分治任务
        Integer result = forkJoinPool.invoke(fibonacci);
        log.info("Fibonacci {} 的结果是 {}", n, result);
    }

    // 定义计算逻辑RecursiveTask
    private static class Fibonacci extends RecursiveTask<Integer> {
        final int n;

        Fibonacci(int n) {
            this.n = n;
        }

        @Override
        public Integer compute() {
            //和递归类似，定义可计算的最小单元，f(0)=0, f(1)=1
            if (n <= 1) {
                return n;
            }
            // 想查看子线程名称输出的可以打开下面注释
            log.info(Thread.currentThread().getName());


            //将任务拆分为计算第n-1个数和第n-2个数的两个子任务，
            // f1.fork()创建f1子任务
            // f1.join()等待子任务f1执行结果
            Fibonacci f1 = new Fibonacci(n - 1);
            f1.fork();
            Fibonacci f2 = new Fibonacci(n - 2);
            // f2.compute()需要f2参与递归
            return f2.compute() + f1.join();
        }
    }
}