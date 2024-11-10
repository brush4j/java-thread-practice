package com.lyflexi.threaddesignpattern.asyncParten.producerAndConsumer;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * @Author: ly
 * @Date: 2024/3/16 12:51
 */

// 弱化版的消息队列类 ， 仅用于java线程之间通信，无法用于进程之间通信
@Slf4j(topic = "c.MessageQueue")
class MessageQueue {
    // 消息的队列集合,使用双向队列LinkedList可以方便的取头加尾
    private LinkedList<Message> list = new LinkedList<>();
    // 队列容量，限制是有界队列
    private int capcity;

    public MessageQueue(int capcity) {
        this.capcity = capcity;
    }

    // 获取消息
    public Message take() {
        // 检查队列是否为空，为空则消费者阻塞
        synchronized (list) {
            while(list.isEmpty()) {
                try {
                    log.debug("队列为空, 消费者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 从队列头部获取消息并返回
            Message message = list.removeFirst();
            log.debug("已消费消息 {}", message);
            list.notifyAll();
            return message;
        }
    }

    // 存入消息
    public void put(Message message) {
        synchronized (list) {
            // 检查对象是否已满，为满则生产者阻塞
            while(list.size() == capcity) {
                try {
                    log.debug("队列已满, 生产者线程等待");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 将消息加入队列尾部
            list.addLast(message);
            log.debug("已生产消息 {}", message);
            list.notifyAll();
        }
    }
}
