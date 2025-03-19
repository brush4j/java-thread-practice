package com.lyflexi.threadpoolpractice.forkjoinpool.one2n;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RecursiveTask;

/**
 * @Author: hmly
 * @Date: 2025/3/19 21:03
 * @Project: java-thread-practice
 * @Version: 1.0.0
 * @Description:
 */
@Slf4j
public class ForkJoinAddTask extends RecursiveTask<Integer> {

    private int n;

    public ForkJoinAddTask(int n) {
        this.n = n;
    }

    /**
     * 重写 toString 方法，方便查看ForkJoinAddTask
     */
    @Override
    public String toString() {
        return "{" + n + '}';
    }

    @Override
    protected Integer compute() {
        // 如果 n 已经为 1，可以求得结果了
        if (n == 1) {
            log.debug("join() {}", n);
            return n;
        }

        // 将任务进行拆分(fork)
        ForkJoinAddTask t1 = new ForkJoinAddTask(n - 1);
        t1.fork();
        log.debug("fork() {} + {}", n, t1);

        // 合并(join)结果
        int result = n + t1.join();
        log.debug("join() {} + {} = {}", n, t1, result);
        return result;
    }
}


