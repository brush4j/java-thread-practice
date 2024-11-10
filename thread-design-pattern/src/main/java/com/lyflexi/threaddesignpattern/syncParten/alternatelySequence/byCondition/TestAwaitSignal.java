package com.lyflexi.threaddesignpattern.syncParten.alternatelySequence.byCondition;

import java.util.concurrent.locks.Condition;

/**
 * @Author: ly
 * @Date: 2024/3/18 21:41
 */
public class TestAwaitSignal {
    public static void main(String[] args) throws InterruptedException {
        AwaitSignal as = new AwaitSignal(5);
        Condition aWaitSet = as.newCondition();
        Condition bWaitSet = as.newCondition();
        Condition cWaitSet = as.newCondition();
        new Thread(() -> {
            as.print("a", aWaitSet, bWaitSet);
        }).start();
        new Thread(() -> {
            as.print("b", bWaitSet, cWaitSet);
        }).start();
        new Thread(() -> {
            as.print("c", cWaitSet, aWaitSet);
        }).start();
        as.start(aWaitSet);

    }
}
