package com.lyflexi.synclockpractice.juc.countDownLatch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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
public class CountDownLatchRpc {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        RestTemplate restTemplate = new RestTemplate();
        log.debug("begin");
        ExecutorService service = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(4);

        // 修改为接收 String 类型的响应
        Future<String> f1 = service.submit(() -> {
            String response = restTemplate.getForObject("https://www.baidu.com/", String.class);
            latch.countDown();
            return response;
        });

        Future<String> f2 = service.submit(() -> {
            String response = restTemplate.getForObject("https://www.baidu.com/", String.class);
            latch.countDown();
            return response;
        });

        Future<String> f3 = service.submit(() -> {
            String response = restTemplate.getForObject("https://www.baidu.com/", String.class);
            latch.countDown();
            return response;
        });

        Future<String> f4 = service.submit(() -> {
            String response = restTemplate.getForObject("https://www.baidu.com/", String.class);
            latch.countDown();
            return response;
        });

        // 等待所有请求完成
        latch.await();

        // 获取结果
        System.out.println("Response length 1: " + (f1.get() != null ? f1.get().length() : 0));
        System.out.println("Response length 2: " + (f2.get() != null ? f2.get().length() : 0));
        System.out.println("Response length 3: " + (f3.get() != null ? f3.get().length() : 0));
        System.out.println("Response length 4: " + (f4.get() != null ? f4.get().length() : 0));

        log.debug("执行完毕");
        service.shutdown();

    }
}
