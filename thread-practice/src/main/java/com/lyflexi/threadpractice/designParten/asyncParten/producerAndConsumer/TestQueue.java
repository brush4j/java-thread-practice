package com.lyflexi.threadpractice.designParten.asyncParten.producerAndConsumer;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: ly
 * @Date: 2024/3/16 12:48
 */

@Slf4j(topic = "c.Test21")
public class TestQueue {

    public static void main(String[] args) throws InterruptedException{
        MessageQueue queue = new MessageQueue(2);

        //生产三条消息
        for (int i = 0; i < 3; i++) {
            int id = i;
            new Thread(() -> {
                queue.put(new Message(id , "值"+id));
            }, "生产者" + i).start();
        }

        //一个消费者源源不断的消费消息
        new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Message message = queue.take();
            }
        }, "消费者").start();
    }

}