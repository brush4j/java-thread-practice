package com.lyflexi.threadpoolpractice.forkjoinpool.hugecomputing1;

/**
 * @Author: ly
 * @Date: 2024/3/28 20:59
 */
public interface Calculator {
    /**
     * 把传进来的所有numbers 做求和处理
     *
     * @param numbers
     * @return 总和
     */
    long sumUp(long[] numbers);
}

