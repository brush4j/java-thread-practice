package com.lyflexi.threadpoolpractice.exception;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Description:
 * @Author: lyflexi
 * @project: java-thread-practice
 * @Date: 2024/11/10 21:23
 */
@Slf4j
public class ThreadPoolTaskExceptionSample5 {
    static ExecutorService pool = new ExecutorServiceWithUncatchException(
            2,
            3,
            0,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue(10));

    /**
     * execute和submit都会打印异常信息了
     * @param args
     */
    public static void main(String[] args) {
        testExecute(pool);
//        testSubmit(pool);
    }

    /**
     * @description:
     * @author: hmly
     * @date: 2025/7/13 14:38
     * @param: [pool]
     * @return: void
     **/
    private static void testExecute(ExecutorService pool) {
        pool.execute(() -> {
            log.debug("task");
            int i = 1 / 0;

        });
    }

    /**
     * @description:
     * @author: hmly
     * @date: 2025/7/13 14:38
     * @param: [pool]
     * @return: void
     **/
    private static void testSubmit(ExecutorService pool) {
        pool.submit(() -> {
            log.debug("task");
            int i = 1 / 0;

        });
    }

    static class ExecutorServiceWithUncatchException extends ThreadPoolExecutor {
        /**
         * @description: 父类ThreadPoolExecutor没有无参构造，因此你要继承ThreadPoolExecutor就也不能有无参构造，
         *
         * 方式是提供有参构造覆盖无参构造
         * @author: hmly
         * @date: 2025/7/13 15:18
         * @param: [corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue]
         * @return:
         **/
        public ExecutorServiceWithUncatchException(int corePoolSize,
                                  int maximumPoolSize,
                                  long keepAliveTime,
                                  TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue,
                                  ThreadFactory threadFactory,
                                  RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,threadFactory,handler);
        }
        public ExecutorServiceWithUncatchException(
                int corePoolSize,
                int maximumPoolSize,
                long keepAliveTime,
                TimeUnit unit,
                BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }


        //重写afterExecute方法
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            //这个是execute提交的时候
            if (t != null) {
                System.out.println("afterExecute里面获取到execute提交的异常信息，处理异常" + t.getMessage());
            }
            //如果r的实际类型是FutureTask 那么是submit提交的，所以可以在里面get到异常
            if (r instanceof FutureTask) {
                try {
                    Future<?> future = (Future<?>) r;
                    //get获取异常
                    future.get();

                } catch (Exception e) {
                    System.out.println("afterExecute里面获取到submit提交的异常信息，处理异常" + e);
                }
            }
        }
    }
}
