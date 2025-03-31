package com.lyflexi.threadpoolpractice.handsonpoolv2;

/**
 * @author liuyanoutsee@outlook.com
 **/
public interface RejectHandle {

    void reject(Runnable rejectCommand, ThreadPoolV2 threadPool);
}
