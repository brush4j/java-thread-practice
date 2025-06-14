package com.lyflexi.caspractice.contrast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/6/14
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public interface IAccountHandler {
    // 获取余额
    Integer getBalance();

    // 取款
    void process(Integer amount);

    /**
     * 方法内会启动 1000 个线程，每个线程做 -10 元 的操作
     * 如果初始余额为 10000 那么正确的结果应当是 0
     */
    default void process(IAccountHandler handler) {
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                handler.process(10);
            }));
        }
        long start = System.nanoTime();
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(handler.getBalance()
                + " cost: " + (end-start)/1000_000 + " ms");
    }
}
