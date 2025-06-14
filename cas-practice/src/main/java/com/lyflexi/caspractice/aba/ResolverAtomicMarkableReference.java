package com.lyflexi.caspractice.aba;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/6/14
 * @description：但是有时候，并不关心引用变量更改了几次，只是单纯的关心是否更改过，所以就有了AtomicMarkableReference
 * @modifiedBy：
 * @version: 1.0
 */
@Slf4j
public class ResolverAtomicMarkableReference {
    /**
     * 给初始的标记为false
     */
    static AtomicMarkableReference<TargetObject> ref = new AtomicMarkableReference<>(TargetObject.of("引用对象", 18), false);


    /**
     * 使用AtomicMarkableReference解决ABA问题
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        log.debug("main start...");
        // 获取初始值, api从get()调整为了getReference()
        TargetObject prev = ref.getReference();
        boolean marked = ref.isMarked();
        log.debug("主线程获取初始值: {}, marked：{}", prev, marked);

        // 启动线程1，将18改为19
        Thread t1 = new Thread(() -> {

            boolean success = ref.compareAndSet(ref.getReference(), TargetObject.of("引用对象", 19), false, true);
            log.debug("线程1 CAS修改 18->19: {}, 当前值: {}, marked：{}", success, ref.getReference(), ref.isMarked());
        }, "thread1");

        // 启动两个线程
        t1.start();

        TimeUnit.MILLISECONDS.sleep(100);

        // 主线程尝试CAS操作
        boolean casResult = ref.compareAndSet(ref.getReference(), TargetObject.of("引用对象", 20), false, true);
        log.debug("主线程 CAS修改 18->20: {}, 当前值: {}, marked:{}", casResult, ref.getReference(), ref.isMarked());

        // 为了清晰地展示问题，再次确认最终结果
        log.debug("最终结果: {}", ref.getReference());
    }
}
