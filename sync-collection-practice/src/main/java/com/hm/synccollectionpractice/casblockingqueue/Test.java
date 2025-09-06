package com.hm.synccollectionpractice.casblockingqueue;

/**
 * @author hasee
 * @description V1.0
 * @create 2025/9/6 19:07
 */
public class Test {
    public static void main(String[] args) {
        HandsonConcurrentLinkedQueue<String> queue = new HandsonConcurrentLinkedQueue<>();
        queue.offer("1");
        queue.offer("2");
        queue.offer("3");
        System.out.println(queue);
    }
}
