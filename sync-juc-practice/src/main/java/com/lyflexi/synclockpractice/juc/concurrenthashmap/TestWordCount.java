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
         * ConcurrentHashMap正确使用方式如下：computeIfAbsent一步操作，相当于get+put二合一
         *
         * 虽说源码中putVal/computeIfAbsent也是用了synchronized，但是没有整个哈希表，而是只锁了单一链表头
         *
         * 懒加载初始化tab
         * for (Node<K,V>[] tab = table;;) {
         *      Node<K,V> f; int n, i, fh; K fk; V fv;
         *      if (tab == null || (n = tab.length) == 0)
         *          tab = initTable();
         *      else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) {
         *            if (casTabAt(tab, i, null, new Node<K,V>(hash, key, value)))
         *                break;                   // no lock when adding to empty bin
         *      }
         *      ...
         *      else{
         *          V oldVal = null;
         *          synchronized (f) {
         *            if (tabAt(tab, i) == f) {
         *               ...
         *            }
         *         }
         *         if (binCount != 0) {
         *             if (binCount >= TREEIFY_THRESHOLD)
         *                 treeifyBin(tab, i);
         *             if (oldVal != null)
         *                 return oldVal;
         *             break;
         *         }
         *      }
         * }
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
