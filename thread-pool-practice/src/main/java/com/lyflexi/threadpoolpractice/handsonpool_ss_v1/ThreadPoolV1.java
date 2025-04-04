package com.lyflexi.threadpoolpractice.handsonpool_ss_v1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 基于有界阻塞队列的线程池经典实现，任务立即执行。但由于任务没有封装到线程池中的线程，导致由主线程执行了任务。进一步导致拒绝策略失效
 * <p>
 * 线程池中的线程要使用死循环while任务去接收用户传入的任务，这样可以做到复用。因为线程一旦停止就无法复活了
 */
public class ThreadPoolV1 {
    private final int corePoolSize;
    private final int maxSize;
    private int timeout = 0;
    private TimeUnit timeUnit = TimeUnit.SECONDS;
    /**
     * 阻塞队列的经典应用场景，因为阻塞队列在队列为空的时候，获取元素方法会阻塞, 这样会避免线程池中的线程在空闲的时候空转
     * eg. take()
     * eg. poll(timeout, timeUnit)
     */
    public BlockingQueue<Runnable> blockingQueue = null;
    private final RejectHandle rejectHandle;

    public ThreadPoolV1(int corePoolSize, int maxSize, int timeout, TimeUnit timeUnit,
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
     *
     * @param command
     */
    void execute(Runnable command) {
        if (coreList.size() < corePoolSize) {
            Thread thread = new Thread(coreTask);
            coreList.add(thread);
            //任务立即执行。但由于任务没有封装到线程池中的线程，导致由主线程执行了任务。进一步导致拒绝策略失效
            command.run();
            thread.start();
            //线程立即执行， 所以下面直接返回
            return;
        }
        //offer返回true证明阻塞队列不满，同时任务正常进入队列，无需创建辅助线程
        if (blockingQueue.offer(command)) {
            // 回应jdk中的线程池策略，只有当达到核心线程数，并且阻塞队列也满时，才会创建救急线程。
            // 因此这里直接返回
            return;
        }
        //offer返回true证明阻塞队列已满，无法入队。 此时创建额外辅助线程，以加速任务的处理
        if (coreList.size() + supportList.size() < maxSize) {
            Thread thread = new Thread(supportTask);
            supportList.add(thread);
            //任务立即执行。但由于任务没有封装到线程池中的线程，导致由主线程执行了任务。进一步导致拒绝策略失效
            command.run();
            thread.start();
            //线程立即执行， 所以下面直接返回
            return;
        }

        //走到这里，说明coreList.size() + supportList.size() > maxSize，说明没有空闲的线程了。
        //此时只能尝试入队列了，如果阻塞队列也返回false, 说明阻塞队列也满了。只有拒绝策略来兜底了
        if (!blockingQueue.offer(command)) {
            rejectHandle.reject(command, this);
        }
    }

    /**
     * 线程池中的线程再封装一层runnable，用于从阻塞队列中获取用户的runnable
     * <p>
     * 核心线程不能停止，所以在run方法中是个死循环，且没有终止条件
     * <p>
     * 核心线程死循环从阻塞队列中不停的取任务来执行
     */
    Runnable coreTask = () -> {
        while (true) {
            try {
                //阻塞队列为空则该方法阻塞
                Runnable command = blockingQueue.take();
                command.run();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    };

    /**
     * 线程池中的线程再封装一层runnable，用于从阻塞队列中获取用户的runnable
     * <p>
     * 辅助线程需要在任务空闲的时候停止，降低资源消耗，因此在死循环中如果获取不到任务，则break停止
     * <p>
     * 对于线程而言，一旦执行完毕，就再也无法复活
     */
    Runnable supportTask = () -> {
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
    };


}
