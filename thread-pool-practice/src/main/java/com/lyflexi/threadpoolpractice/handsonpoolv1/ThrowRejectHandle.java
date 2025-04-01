package com.lyflexi.threadpoolpractice.handsonpoolv1;

/**
 * @author liuyanoutsee@outlook.com
 **/
public class ThrowRejectHandle implements RejectHandle {
    @Override
    public void reject(Runnable rejectCommand, ThreadPoolV1 threadPool) {
        throw new RuntimeException("阻塞队列满了！");
    }
}
