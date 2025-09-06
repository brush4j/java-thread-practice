package com.hm.synccollectionpractice.casblockingqueue;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author hasee
 * @description V1.0
 * @create 2025/9/6 18:40
 */
public class Node<E> {

    volatile E item;
    AtomicReference<Node<E>> next;

    /**
     * node初始化的时候都会初始化其next节点
     * @param item
     * @param next
     */
    public Node(E item, Node<E> next) {
        this.item = item;
        this.next = new AtomicReference<>(next);
    }


}
