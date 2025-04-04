package com.lyflexi.threadpoolpractice.handsonpool_ss_v1;

/**
 * @author liuyanoutsee@outlook.com
 **/
public interface RejectHandle {

    void reject(Runnable rejectCommand, ThreadPoolV1 threadPool);
}
