package com.lyflexi.threadpoolpractice.handsonpoolv2;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author liuyanoutsee@outlook.com
 *
 * 基于有界阻塞队列的线程池经典实现
 **/
public class ThreadPoolV2 {

    private final int corePoolSize;
    private final int maxSize;
    private final int timeout;
    private final TimeUnit timeUnit;
    /**
     * 阻塞队列的经典应用场景，因为阻塞队列在队列为空的时候，获取元素方法会阻塞, 这样会避免线程池中的线程在空闲的时候空转
     * eg. take()
     * eg. poll(timeout, timeUnit)
     */
    public final BlockingQueue<Runnable> blockingQueue;
    private final RejectHandle rejectHandle;

    public ThreadPoolV2(int corePoolSize, int maxSize, int timeout, TimeUnit timeUnit,
                        BlockingQueue<Runnable> blockingQueue, RejectHandle rejectHandle) {
        this.corePoolSize = corePoolSize;
        this.maxSize = maxSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.blockingQueue = blockingQueue;
        this.rejectHandle = rejectHandle;
    }


    /**
     * 核心线程集合
     */
    List<Thread> coreList = new ArrayList<>();

    /**
     * 辅助线程集合，当核心线程数量满的时候会额外初始化辅助线程
     */
    List<Thread> supportList = new ArrayList<>();


    /**
     * 线程池的任务执行主流程，线程懒加载
     * @param command
     */
    void execute(Runnable command) {
        if (coreList.size() < corePoolSize) {
            Thread thread = new CoreThread(command);
            coreList.add(thread);
            thread.start();
            return;
        }
        //offer返回true证明阻塞队列不满，同时任务正常进入队列，无需创建辅助线程
        if (blockingQueue.offer(command)) {
            return;
        }
        //offer返回true证明阻塞队列已满，无法入队。 此时创建额外辅助线程，以加速任务的处理，立即执行
        //TODO 注意区分jdk中的线程池策略，jdk中是当达到核心线程数，并且阻塞队列也满时，才会创建救急线程
        if (coreList.size() + supportList.size() < maxSize) {
            Thread thread = new SupportThread(command);
            supportList.add(thread);
            thread.start();
            return;
        }
        //走到这里，说明coreList.size() + supportList.size() > maxSize，说明没有空闲的线程了。
        //此时只能尝试入队列了，如果阻塞队列也返回false, 说明阻塞队列也满了。只有拒绝策略来兜底了
        if (!blockingQueue.offer(command)) {
            rejectHandle.reject(command, this);
        }
    }


    /**
     * 核心线程不能停止，所以在run方法中是个死循环，且没有终止条件
     *
     * 核心线程死循环从阻塞队列中不停的取任务来执行
     */
    class CoreThread extends Thread {

        private final Runnable firstTask;

        public CoreThread(Runnable firstTask) {
            this.firstTask = firstTask;
        }

        @Override
        public void run() {
            firstTask.run();
            while (true) {
                try {
                    Runnable command = blockingQueue.take();
                    command.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 辅助线程需要在任务空闲的时候停止，降低资源消耗，因此在死循环中如果获取不到任务，则break停止
     *
     * 对于线程而言，一旦执行完毕，就再也无法复活
     */
    class SupportThread extends Thread {
        private final Runnable firstTask;

        public SupportThread(Runnable firstTask) {
            this.firstTask = firstTask;
        }

        @Override
        public void run() {
            firstTask.run();
            while (true) {
                try {
                    Runnable command = blockingQueue.poll(timeout, timeUnit);
                    if (command == null) {
                        break;
                    }
                    command.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println(Thread.currentThread().getName() + "线程结束了！");
        }
    }
}
