package com.hm.synccollectionpractice.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liuyanoutsee@outlook.com
 * @Date: 2025/4/12 23:36
 * @Project: java-thread-practice
 * @Version: 1.0.0
 * @Description:
 */
public class SyncCollectionV2WholeSync {
    public static void main(String[] args) {

        List<String> list = new ArrayList<String>();


        //但真的你就可以无所忌惮的认为所有场景都是安全使用的吗？下面举个例子
        for (int i = 0; i < 5000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    wholeSyncAdd(list);
                }
            }, "thread-" + i).start();
        }
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(list);
    }

    /**
     * 复杂业务场景下，工具类依然容易出现线程安全问题Collections.synchronizedCollection(new ArrayList<String>());
     *
     * 所以直接采取整个方法加synchronized锁，这样更安全
     * @param list
     */
    private static synchronized void wholeSyncAdd(List<String> list) {
        if (list.isEmpty()) {
            list.add("init");
        }
    }

}
