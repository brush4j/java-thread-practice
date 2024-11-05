package com.lyflexi.threadpractice.forkjoinpool.hugecomputing2;

/**
 * @Author: ly
 * @Date: 2024/3/28 21:08
 */

import lombok.extern.slf4j.Slf4j;
import com.lyflexi.threadpractice.forkjoinpool.hugecomputing1.ExecutorServiceCalculator;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
@Slf4j(topic = "c.ForkJoinCalculator")
public class ForkJoinCalculator implements Calculator {

    private ForkJoinPool pool;

    // 1. 定义计算逻辑
    private static class SumTask extends RecursiveTask<Long> {
        private long[] numbers;
        private int from;
        private int to;

        public SumTask(long[] numbers, int from, int to) {
            this.numbers = numbers;
            this.from = from;
            this.to = to;
        }

        //此方法为ForkJoin的核心方法：对任务进行拆分  拆分的好坏决定了效率的高低
        @Override
        protected Long compute() {

            // 当需要计算的数字个数小于6时，直接采用for loop方式计算结果
            if (to - from < 6) {
                long total = 0;
                for (int i = from; i <= to; i++) {
                    total += numbers[i];
                }
                return total;
            } else {
                // 否则，把任务一分为二，递归拆分(注意此处有递归)到底拆分成多少分 需要根据具体情况而定
                int middle = (from + to) / 2;
                SumTask taskLeft = new SumTask(numbers, from, middle);
                SumTask taskRight = new SumTask(numbers, middle + 1, to);
                taskLeft.fork();
                taskRight.fork();
                return taskLeft.join() + taskRight.join();
            }
        }
    }

    public ForkJoinCalculator() {
        // 也可以使用公用的线程池 ForkJoinPool.commonPool()：
        // pool = ForkJoinPool.commonPool()
        pool = new ForkJoinPool();
    }

    @Override
    public long sumUp(long[] numbers) {
        Long result = pool.invoke(new SumTask(numbers, 0, numbers.length - 1));
        pool.shutdown();
        return result;
    }

    public static void main(String[] args) {
        ForkJoinCalculator forkJoinCalculator = new ForkJoinCalculator();
        long[] numbers = new long[100000000];

        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = 1;
        }
        long answer = forkJoinCalculator.sumUp(numbers);
        System.out.println(answer);
    }
}

