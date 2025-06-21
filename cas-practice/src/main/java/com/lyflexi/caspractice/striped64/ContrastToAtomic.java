package com.lyflexi.caspractice.striped64;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/6/21
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class ContrastToAtomic {
    public static void main(String[] args) {
        demo(
                () -> new AtomicLong(0),
                (adder) -> adder.getAndIncrement()
        );


        demo(
                () -> new LongAdder(),
                adder -> adder.increment()
        );
    }

    /**
     * @description:
     * @author: hmly
     * @date: 2025/6/21 15:45
     * @param: [adderSupplier, action]
     * adderSupplier.    () -> 结果    提供累加器对象
     * action            (参数) ->     执行累加操作
     *
     * @return: void
     **/
    private static <T> void demo(Supplier<T> adderSupplier, Consumer<T> action) {
        T adder = adderSupplier.get();
        List<Thread> ts = new ArrayList<>();
        // 4 个线程，每人累加 100 万
        for (int i = 0; i < 4; i++) {
            ts.add(new Thread(() -> {
                for (int j = 0; j < 1000000; j++) {
                    action.accept(adder);
                }
            }));
        }
        long start = System.nanoTime();
        ts.forEach(t -> t.start());
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long end = System.nanoTime();
        System.out.println(adder + " cost:" + (end - start) / 1000_000);
    }
}
