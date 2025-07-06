package com.lyflexi.synclockpractice.juc.cyclicBarrier.nextGeneration;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/7/6
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class Main {
    //threadpool
    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    //球员
    static String[] str = {"李明", "王强", "刘凯", "赵杰"};
    //当拦截线程数达到4时，触发barrierAction
    private static final CyclicBarrier cb = new CyclicBarrier(4, () -> {
        String members = "";
        for (String s : str) {
            members += s + " ";
        }
        System.out.println(members + ": 四人到达球场开始打球");
    });
    /**
     * @description:
     * @author: hmly
     * @date: 2025/7/6 15:33
     * @param: [args]
     * @return: void
     **/
    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            threadPool.execute(new MemberThread(str[i],cb));
        }
        try {
            Thread.sleep(4000);
            System.out.println();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //计数复用
        System.out.println("现在对CyclicBarrier进行复用.....");
        System.out.println("又来了一拨人，看看愿不愿意一起打：");
        str = new String[]{"王二", "洪光", "雷兵", "赵三"};
        for (int i = 0; i < 4; i++) {
            threadPool.execute(new MemberThread(str[i],cb));
        }
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
