package com.lyflexi.synclockpractice;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/7/6
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        ExecutorService consumer = Executors.newFixedThreadPool(1);
        ExecutorService producer = Executors.newFixedThreadPool(1);
        BlockingQueue<Integer> queue = new SynchronousQueue<>();
        log.debug("开始");
        producer.submit(() -> {
            log.debug("开始");
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.debug("结束");
            try {
                queue.put(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        consumer.submit(() -> {
            try {
                Integer result = queue.take();
                log.debug("结果为:{}", result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }
}
