package com.lyflexi.threadpoolpractice.handsonpool_ss_v2;

/**
 * @author liuyanoutsee@outlook.com
 **/
public interface RejectHandle {

    void reject(Runnable rejectCommand, ThreadPoolV2 threadPool);
}
