package com.lyflexi.synclockpractice.sync.feat;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: ly
 * @Date: 2024/2/24 17:00
 *
 * synchronized的宏观有序性的本质就是原子性
 */
@Slf4j(topic = "c.VisibleExample2")
public class MacroOrderliness {

    //    根据JMM内存模型，此处的count为主存数据
    private static int count = 0;

    private static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                //此处如果不加synchronized块，则共享变量count会发生丢失修改，从而导致最终count不为0
                synchronized (lock) {
                    count++;
                }
            }
        }, "t1");

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 5000; i++) {
                //此处如果不加synchronized块，则共享变量count会发生丢失修改，从而导致最终count不为0
                synchronized (lock) {
                    count--;
                }

            }
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("{}", count);
    }


}
