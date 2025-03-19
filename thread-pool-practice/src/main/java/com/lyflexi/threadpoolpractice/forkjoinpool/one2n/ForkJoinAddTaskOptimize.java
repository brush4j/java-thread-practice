package com.lyflexi.threadpoolpractice.forkjoinpool.one2n;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RecursiveTask;

/**
 * @Author: hmly
 * @Date: 2025/3/19 21:12
 * @Project: java-thread-practice
 * @Version: 1.0.0
 * @Description:
 */
@Slf4j
public class ForkJoinAddTaskOptimize extends RecursiveTask<Integer> {

    int begin;
    int end;

    public ForkJoinAddTaskOptimize(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public String toString() {
        return "{" + begin + "," + end + '}';
    }

    @Override
    protected Integer compute() {
        if (begin == end) {
            log.debug("join() {}", begin);
            return begin;
        }
        if (end - begin == 1) {
            log.debug("join() {} + {} = {}", begin, end, end + begin);
            return end + begin;
        }
        int mid = (end + begin) / 2;

        ForkJoinAddTaskOptimize t1 = new ForkJoinAddTaskOptimize(begin, mid);
        t1.fork();
        ForkJoinAddTaskOptimize t2 = new ForkJoinAddTaskOptimize(mid + 1, end);
        t2.fork();
        log.debug("fork() {} + {} = ?", t1, t2);

        int result = t1.join() + t2.join();
        log.debug("join() {} + {} = {}", t1, t2, result);
        return result;
    }
}
