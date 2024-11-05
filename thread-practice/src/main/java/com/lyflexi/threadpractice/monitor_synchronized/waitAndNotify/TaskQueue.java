package com.lyflexi.threadpractice.monitor_synchronized.waitAndNotify;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: ly
 * @Date: 2024/3/6 15:51
 */
class TaskQueue {
    Queue<String> queue = new LinkedList<>();

    public synchronized void addTask(String s) {
        this.queue.add(s);
        this.notifyAll();
    }

    public synchronized String getTask() throws InterruptedException {
        while (queue.isEmpty()) {
            this.wait();
        }
        return queue.remove();
    }
}
