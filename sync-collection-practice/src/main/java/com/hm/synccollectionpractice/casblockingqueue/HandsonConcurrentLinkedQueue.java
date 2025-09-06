package com.hm.synccollectionpractice.casblockingqueue;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author hasee 实现标准Queue接口
 * @description V1.0
 * @create 2025/9/6 18:39
 */
public class HandsonConcurrentLinkedQueue<E> implements Queue<E> {
    /**
     * 构造函数
     */
    public HandsonConcurrentLinkedQueue() {
        head = last = new Node<>(null, null);
    }

    /**
     * 尾指针
     */
    private volatile Node<E> last;
    /**
     * 头指针
     */
    private volatile Node<E> head;

    @Override
    public boolean offer(E e) {
        Node<E> n = new Node<>(e, null);
        while(true) {
            // 获取尾节点
            AtomicReference<Node<E>> next = last.next;
            // S1: 真正尾节点的 next 是 null, cas 从 null 到新节点
            if(next.compareAndSet(null, n)) {
                // 这时的 last 已经是倒数第二, next 不为空了, 其它线程的 cas 肯定失败
                // S2: 更新 last 为倒数第一的节点
                last = n;
                return true;
            }
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Node<E> p = head; p != null; p = p.next.get()) {
            E item = p.item;
            if (item != null) {
                sb.append(item).append("->");
            }
        }
        sb.append("null");
        return sb.toString();
    }
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E element() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }
}
