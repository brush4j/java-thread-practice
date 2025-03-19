package com.lyflexi.threadpoolpractice.forkjoinpool.one2n;

import java.util.concurrent.ForkJoinPool;

/**
 * @Author: hmly
 * @Date: 2025/3/19 21:06
 * @Project: java-thread-practice
 * @Version: 1.0.0
 * @Description: 由于ForkJoinPool针对的是CPU密集型程序，因此ForkJoinPool默认线程数目是CPU核数
 */
public class TestForkJoinOptimize {
    public static void main(String[] args) {
        //指定线程数为4
        ForkJoinPool pool = new ForkJoinPool(4);
        System.out.println(pool.invoke(new ForkJoinAddTaskOptimize(1,5)));
    }
}
