package com.hm.synccollectionpractice.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * @Author: liuyanoutsee@outlook.com
 * @Date: 2025/4/12 23:36
 * @Project: java-thread-practice
 * @Version: 1.0.0
 * @Description:
 */
public class SyncCollectionV1UtilUnsafe {
    public static void main(String[] args) {
        //这是一个使用装饰器模式（组合）实现的线程安全集合，可以看源码
        Collection<String> syncCollection = Collections.synchronizedCollection(new ArrayList<String>());
        /*
        * static class SynchronizedCollection<E> implements Collection<E>, Serializable {
        @java.io.Serial
        private static final long serialVersionUID = 3053995032091335093L;

        @SuppressWarnings("serial") // Conditionally serializable
        final Collection<E> c;  // Backing Collection
        @SuppressWarnings("serial") // Conditionally serializable
        final Object mutex;     // Object on which to synchronize

        SynchronizedCollection(Collection<E> c) {
            this.c = Objects.requireNonNull(c);
            mutex = this;
        }

        SynchronizedCollection(Collection<E> c, Object mutex) {
            this.c = Objects.requireNonNull(c);
            this.mutex = Objects.requireNonNull(mutex);
        }

        public int size() {
            synchronized (mutex) {return c.size();}
        }
        public boolean isEmpty() {
            synchronized (mutex) {return c.isEmpty();}
        }
        public boolean contains(Object o) {
            synchronized (mutex) {return c.contains(o);}
        }
        public Object[] toArray() {
            synchronized (mutex) {return c.toArray();}
        }
        public <T> T[] toArray(T[] a) {
            synchronized (mutex) {return c.toArray(a);}
        }
        public <T> T[] toArray(IntFunction<T[]> f) {
            synchronized (mutex) {return c.toArray(f);}
        }

        public Iterator<E> iterator() {
            return c.iterator(); // Must be manually synched by user!
        }

        public boolean add(E e) {
            synchronized (mutex) {return c.add(e);}
        }
        public boolean remove(Object o) {
            synchronized (mutex) {return c.remove(o);}
        }

        public boolean containsAll(Collection<?> coll) {
            synchronized (mutex) {return c.containsAll(coll);}
        }
        public boolean addAll(Collection<? extends E> coll) {
            synchronized (mutex) {return c.addAll(coll);}
        }
        public boolean removeAll(Collection<?> coll) {
            synchronized (mutex) {return c.removeAll(coll);}
        }
        public boolean retainAll(Collection<?> coll) {
            synchronized (mutex) {return c.retainAll(coll);}
        }
        public void clear() {
            synchronized (mutex) {c.clear();}
        }
        public String toString() {
            synchronized (mutex) {return c.toString();}
        }
        // Override default methods in Collection
        @Override
        public void forEach(Consumer<? super E> consumer) {
            synchronized (mutex) {c.forEach(consumer);}
        }
        @Override
        public boolean removeIf(Predicate<? super E> filter) {
            synchronized (mutex) {return c.removeIf(filter);}
        }
        @Override
        public Spliterator<E> spliterator() {
            return c.spliterator(); // Must be manually synched by user!
        }
        @Override
        public Stream<E> stream() {
            return c.stream(); // Must be manually synched by user!
        }
        @Override
        public Stream<E> parallelStream() {
            return c.parallelStream(); // Must be manually synched by user!
        }
        @java.io.Serial
        private void writeObject(ObjectOutputStream s) throws IOException {
            synchronized (mutex) {s.defaultWriteObject();}
        }
    }
        *
        * */

        //但真的你就可以无所忌惮的认为所有场景都是安全使用的吗？下面举个例子
        for (int i = 0; i < 5000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    unAutomicAdd(syncCollection);
                }
            }, "thread-" + i).start();
        }
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(syncCollection);
    }

    /**
     * 非原子操作会发生线程不安全，即使被操作对象是syncCollection
     * @param syncCollection
     *
     * 存在概率连续添加两次init
     */
    private static void unAutomicAdd(Collection<String> syncCollection) {
        if (syncCollection.isEmpty()) {
            syncCollection.add("init");
        }
    }

}
