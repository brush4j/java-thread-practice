package com.lyflexi.caspractice.aba;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/6/14
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Slf4j
public class ResolverAtomicStampedReference {
    /**
     * 给初始的邮戳为0
     */
    static AtomicStampedReference<TargetObject> ref = new AtomicStampedReference<>(TargetObject.of("引用对象", 18),0);
    static CountDownLatch latch = new CountDownLatch(2);

    /**
     * 使用AtomicStampedReference解决ABA问题
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        log.debug("main start...");
        // 获取初始值, api从get()调整为了getReference()
        TargetObject prev = ref.getReference();
        int prevStamp = ref.getStamp();
        log.debug("主线程获取初始值: {}, 邮戳为：{}", prev,prevStamp);

        // 启动线程1，将18改为19
        Thread t1 = new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(100);  // 稍微延时，确保执行顺序
                int stamp = ref.getStamp();
                boolean success = ref.compareAndSet(ref.getReference(), TargetObject.of("引用对象", 19),stamp ,stamp +1);
                log.debug("线程1 CAS修改 18->19: {}, 当前值: {}, 邮戳为：{}", success, ref.getReference(),ref.getStamp());
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread1");

        // 启动线程2，将19改回18
        Thread t2 = new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(200);  // 确保在线程1之后执行
                int stamp = ref.getStamp();
                boolean success = ref.compareAndSet(ref.getReference(), TargetObject.of("引用对象", 18),stamp,stamp+1);
                log.debug("线程2 CAS修改 19->18: {}, 当前值: {}, 邮戳为：{}", success, ref.getReference(),ref.getStamp());
                latch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "thread2");

        // 启动两个线程
        t1.start();
        t2.start();

        // 等待两个修改线程执行完成
        latch.await();
        TimeUnit.MILLISECONDS.sleep(100);  // 确保能看到中间过程的日志

        // 主线程尝试CAS操作
        boolean casResult = ref.compareAndSet(ref.getReference(), TargetObject.of("引用对象", 20),prevStamp,prevStamp+1);
        log.debug("主线程 CAS修改 18->20: {}, 当前值: {}", casResult, ref.getReference());

        // 为了清晰地展示问题，再次确认最终结果
        log.debug("最终结果: {}", ref.getReference());
    }
}
