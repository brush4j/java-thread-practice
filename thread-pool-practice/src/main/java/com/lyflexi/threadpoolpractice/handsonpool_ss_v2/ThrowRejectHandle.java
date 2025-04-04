package com.lyflexi.threadpoolpractice.handsonpool_ss_v2;

/**
 * @author liuyanoutsee@outlook.com
 **/
public class ThrowRejectHandle implements RejectHandle {
    @Override
    public void reject(Runnable rejectCommand, ThreadPoolV2 threadPool) {
        throw new RuntimeException("阻塞队列满了！");
    }
}
