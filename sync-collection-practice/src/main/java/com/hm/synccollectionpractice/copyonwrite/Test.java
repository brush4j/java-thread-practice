package com.hm.synccollectionpractice.copyonwrite;

import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author hasee
 * @description V1.0
 * @create 2025/9/6 19:32
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        CopyOnWriteArrayList<String> cwlist = new CopyOnWriteArrayList<>();
        cwlist.add("a");
        cwlist.add("b");
        cwlist.add("c");
        //注意, 迭代器持有的是原始快照, 即使后面有人更新了cwlist,  迭代器依然无法感知更新后的内容, 这就会造成迭代器遍历的线程安全问题
        Iterator<String> iterator = cwlist.iterator();

        new Thread(new Runnable() {
            public void run() {
                cwlist.remove(0);
                log.info("new thread list:{}",cwlist);
            }
        }).start();

        try {
            Thread.sleep(1000);
            log.info("保证数组已经回设更新");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        while(iterator.hasNext()) {
            log.info("iterator {}",iterator.next());
        }

        /**
         * 可以通过普通的for循环, 保证读取最新的cwlist数组
         */
        for (String s : cwlist) {
            log.info("simple for {}",s);
        }
    }
}
