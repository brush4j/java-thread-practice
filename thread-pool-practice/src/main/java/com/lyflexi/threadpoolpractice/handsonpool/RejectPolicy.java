package com.lyflexi.threadpoolpractice.handsonpool;

/**
 * @Description:
 * @Author: lyflexi
 * @project: java-thread-practice
 * @Date: 2024/11/10 15:42
 */
@FunctionalInterface // 拒绝策略
interface RejectPolicy<T> {
    void reject(BlockingQueue<T> queue, T task);
}