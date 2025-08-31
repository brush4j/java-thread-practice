package com.lyflexi.synclockpractice.juc.concurrenthashmap;

import lombok.Data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/7/27
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class TestWordCount {
    public static void main(String[] args) {
        /**
         * 虽然ConcurrentHashMap是线程安全的集合，但不代表get+put合起来是原子的
         */
        process1(
                () -> new ConcurrentHashMap<String, Long>(8,0.75f,8),

                (map, words) -> {
                    for (String word : words) {
                        // 检查 key 有没有
                        Long v = map.get(word);
                        if (Objects.isNull(v)){
                            v = 0L;
                        }
                        v++;
                        map.put(word, v);
                    }
                }
        );

        /**
         * 不推荐使用synchronized锁定整个map，性能太差了
         */
        process1(
                () -> new ConcurrentHashMap<String, Long>(8,0.75f,8),

                (map, words) -> {
                    for (String word : words) {
                        synchronized (map){
                            // 检查 key 有没有
                            Long v = map.get(word);
                            if (Objects.isNull(v)){
                                v = 0L;
                            }
                            v++;
                            map.put(word, v);
                        }
                    }
                }
        );

        /**
         * ConcurrentHashMap正确使用方式如下：computeIfAbsent一步操作，相当于
         * 1. get不存在, 则初始化数组头节点/或者初始化链表尾节点, 返回
         * 2. get存在, 则直接返回旧值
         *
         * 源码实现思路如下,
         * 1. 如果value(头节点f)为空, 创建个预留节点r当作头节点用synchronized锁住, 然后初始化真正的value设置给数组的头节点, 之后分支break返回
         * 同时在锁块内通过casTabAt再次判断value是否为空, 达到了DCL的目的
         *
         *
         *             else if ((f = tabAt(tab, i = (n - 1) & h)) == null) {
         *                 Node<K,V> r = new ReservationNode<K,V>();
         *                 synchronized (r) {
         *                     if (casTabAt(tab, i, null, r)) {
         *                         binCount = 1;
         *                         Node<K,V> node = null;
         *                         try {
         *                             if ((val = mappingFunction.apply(key)) != null)
         *                                 node = new Node<K,V>(h, key, val, null);
         *                         } finally {
         *                             setTabAt(tab, i, node);
         *                         }
         *                     }
         *                 }
         *                 if (binCount != 0)
         *                     break;
         *             }
         * 2. 第二个synchronized块, 依旧是DCL
         *    如果value(头节点f)存在, 则先跟据头节点和输入key确定是否key存在, 存在则直接返回旧值val . 然后根据e = e.next遍历链表, 直到找到相同key的节点break返回旧的节点值val
         *                         直到链表尾部都没找到(e = e.next) == null , 则尾插法追增链表节点,   完美🆒
         *                synchronized (f) {
         *                     if (tabAt(tab, i) == f) {
         *                         if (fh >= 0) {
         *                             binCount = 1;
         *                             for (Node<K,V> e = f;; ++binCount) {
         *                                 K ek; V ev;
         *                                 //为什么先用hash和key进行==比较, 因为==的效率高于equals
         *                                 if (e.hash == h &&
         *                                     ((ek = e.key) == key ||
         *                                      (ek != null && key.equals(ek)))) {
         *                                     val = e.val;
         *                                     break;
         *                                 }
         *                                 Node<K,V> pred = e;
         *                                 if ((e = e.next) == null) {
         *                                     if ((val = mappingFunction.apply(key)) != null) {
         *                                         added = true;
         *                                         pred.next = new Node<K,V>(h, key, val, null);
         *                                     }
         *                                     break;
         *                                 }
         *                             }
         *                         }
         *                         else if (f instanceof TreeBin) {
         *                             binCount = 2;
         *                             TreeBin<K,V> t = (TreeBin<K,V>)f;
         *                             TreeNode<K,V> r, p;
         *                             if ((r = t.root) != null &&
         *                                 (p = r.findTreeNode(h, key, null)) != null)
         *                                 val = p.val;
         *                             else if ((val = mappingFunction.apply(key)) != null) {
         *                                 added = true;
         *                                 t.putTreeVal(h, key, val);
         *                             }
         *                         }
         *                     }
         *                 }
         *
         */
        process1(
                () -> new ConcurrentHashMap<String, LongAdder>(8,0.75f,8),// 创建 map 集合

                (map, words) -> {
                    for (String word : words) {
                        LongAdder value = map.computeIfAbsent(word, (key) -> new LongAdder());
                        value.increment();
                    }
                }
        );

        /**
         * 自定义个线程安全的累加器LongReference也可以，模拟LongAdder，只是性能比不过LongAdder
         */
        process1(
                () -> new ConcurrentHashMap<String, LongReference>(8,0.75f,8),// 创建 map 集合

                (map, words) -> {
                    for (String word : words) {
                        LongReference value = map.computeIfAbsent(word, (key) -> new LongReference());
                        value.increment();
                    }
                }
        );

        process2();
    }

    @Data
    static class LongReference {
        private Long value;
        public LongReference() {
            this.value = 0L;
        }
        public synchronized void increment() {
            value++;
        }
    }


    /**
     * 分治算法2
     */
    private static void process2() {

        Map<String, Integer> collect = IntStream.range(1, 27).parallel()
                .mapToObj(idx -> readFromFile(
                        String.valueOf(idx)))
                .flatMap(list -> list.stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(w -> 1)));
        System.out.println(collect);
    }

    /**
     * 分治算法1
     */
    private static <V> void process1(Supplier<Map<String, V>> supplier, BiConsumer<Map<String, V>, List<String>> consumer) {
        Map<String, V> counterMap = supplier.get();
        // key value
        // a   200
        // b   200
        List<Thread> ts = new ArrayList<>();
        for (int i = 1; i <= 26; i++) {
            int idx = i;
            Thread thread = new Thread(() -> {
                List<String> words = readFromFile(String.valueOf(idx));
                consumer.accept(counterMap, words);
            });
            ts.add(thread);
        }

        ts.forEach(t -> t.start());
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(counterMap);
    }

    public static List<String> readFromFile(String fileName) {
        ArrayList<String> words = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("tmp/" + fileName + ".txt")))) {
            while (true) {
                String word = in.readLine();
                if (word == null) {
                    break;
                }
                words.add(word);
            }
            return words;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
