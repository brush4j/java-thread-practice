package com.lyflexi.synclockpractice.juc.semaphore.connectpool;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/7/6
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        ConnectPool pool = new ConnectPool(2);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                Connection conn = pool.borrow();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pool.free(conn);
            }).start();
        }
    }
}
