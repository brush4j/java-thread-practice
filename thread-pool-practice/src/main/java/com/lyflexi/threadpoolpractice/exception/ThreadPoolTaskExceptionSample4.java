package com.lyflexi.threadpoolpractice.exception;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @Description:
 * @Author: lyflexi
 * @project: java-thread-practice
 * @Date: 2024/11/10 21:23
 */
@Slf4j
public class ThreadPoolTaskExceptionSample4 {
    static ExecutorService executorService = new ThreadPoolExecutor(
            1,
            1,
            0,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue(10),
            new ThreadFactoryWithExceptionHandler());
    /**
     *
     * @param args
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        testExecute();
        testSubmit();
    }
    /**
     * @description: execute方法被线程工厂factory中设置的 UncaughtExceptionHandler捕捉到异常
     * @author: hmly
     * @date: 2025/7/13 14:44
     * @param: []
     * @return: void
     **/
    private static void testExecute() throws ExecutionException, InterruptedException {
        executorService.execute(() -> {
            log.debug("task");
            int i = 1 / 0;
        });
    }
    /**
     * @description: submit方法没有被线程工厂factory中设置的 UncaughtExceptionHandler捕捉到异常
     * @author: hmly
     * @date: 2025/7/13 14:44
     * @param: []
     * @return: void
     **/
    private static void testSubmit() throws ExecutionException, InterruptedException {
        Future<Boolean> f = executorService.submit(() -> {

            log.debug("task");
            int i = 1 / 0;
            return true;

        });

        //从结果看出：submit并不是丢失了异常，使用future.get（）还是有异常打印的！！
        // 那为什么线程工厂factory 的UncaughtExceptionHandler没有打印异常呢？猜测是submit方法内部已经捕获了异常， 只是没有打印出来，也因为异常已经被捕获，因此jvm也就不会去调用Thread的UncaughtExceptionHandler去处理异常。
//        f.get();
    }

    /**
     * 实现一个自己的线程池工厂，给线程设置未捕获异常（没有被try-catch）发生的行为
     *
     * UncaughtExceptionHandler 是Thread类一个内部类，也是一个函数式接口 ,接口定义如下
     *
     *     @FunctionalInterface
     *     public interface UncaughtExceptionHandler {
     *         /**
     *          * Method invoked when the given thread terminates due to the
     *          * given uncaught exception.
     *          * <p>Any exception thrown by this method will be ignored by the
     *          * Java Virtual Machine.
     *          * @param t the thread
     *          * @param e the exception
     *         void uncaughtException(Thread t, Throwable e);
     * */
    static class ThreadFactoryWithExceptionHandler implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            //创建一个线程
            Thread t = new Thread(r);
            //给创建的线程设置UncaughtExceptionHandler对象 里面实现异常的默认逻辑
            t.setDefaultUncaughtExceptionHandler((Thread thread1, Throwable e) -> {
                log.info("线程工厂设置的exceptionHandler" + e.getMessage());
            });
            return t;
        }
    }
}
