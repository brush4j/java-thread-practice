package com.lyflexi.threaddesignpattern.flyweight;

import java.sql.Connection;
import java.util.Random;

/**
 * @Description:
 * @Author: lyflexi
 * @project: java-thread-practice
 * @Date: 2024/11/10 12:36
 */
public class FlyWeigntTest {
    public static void main(String[] args) {
        ConnectionPool pool = new ConnectionPool(2);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                Connection conn = pool.borrow();
                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pool.free(conn);
            }).start();
        }
    }
}
