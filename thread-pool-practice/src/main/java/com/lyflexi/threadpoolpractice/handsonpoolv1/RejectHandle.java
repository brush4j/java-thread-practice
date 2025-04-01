package com.lyflexi.threadpoolpractice.handsonpoolv1;

/**
 * @author liuyanoutsee@outlook.com
 **/
public interface RejectHandle {

    void reject(Runnable rejectCommand, ThreadPoolV1 threadPool);
}
