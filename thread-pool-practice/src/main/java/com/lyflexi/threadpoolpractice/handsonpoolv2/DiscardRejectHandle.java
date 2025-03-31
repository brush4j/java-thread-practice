package com.lyflexi.threadpoolpractice.handsonpoolv2;

/**
 * @author liuyanoutsee@outlook.com
 **/
public class DiscardRejectHandle implements RejectHandle {
    @Override
    public void reject(Runnable rejectCommand, ThreadPoolV2 threadPool) {
        //丢弃阻塞队列中的一个任务
        threadPool.blockingQueue.poll();
        //执行新到来的任务
        threadPool.execute(rejectCommand);
    }
}
