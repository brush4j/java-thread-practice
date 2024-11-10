package com.lyflexi.threadpoolpractice.handsonpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author: lyflexi
 * @project: java-thread-practice
 * @Date: 2024/11/10 15:44
 */
@Slf4j(topic = "c.TestPool")
public class TestPool {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(
                1,
                1000, 
                TimeUnit.MILLISECONDS, 
                1, 
                //下面的函数式接口作为用户的自定义逻辑传入底层
                //反过来，函数式接口接收两个由底层回传的参数queue, task
                // 1.这里拿到的queue和ThreadPool构造创建出的queue是同一个
                // 2.这里拿到的task和ThreadPool#execute中用户传入的task是同一个
                (queue, task)->{
            // 1. 死等
//            queue.put(task);
            // 2) 带超时等待
//            queue.offer(task, 1500, TimeUnit.MILLISECONDS);
            // 3) 让调用者放弃任务执行
//            log.debug("放弃{}", task);
            // 4) 让调用者抛出异常
//            throw new RuntimeException("任务执行失败 " + task);
            // 5) 让调用者自己执行任务，task已经给你了，直接调用run即可而不再走execute的逻辑
            task.run();
        });
        for (int i = 0; i < 4; i++) {
            int j = i;
            threadPool.execute(() -> {
                try {
                    //验证任务队列（阻塞队列）满了之后，应该使用什么样的拒绝策略
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("{}", j);
            });
        }
    }
}