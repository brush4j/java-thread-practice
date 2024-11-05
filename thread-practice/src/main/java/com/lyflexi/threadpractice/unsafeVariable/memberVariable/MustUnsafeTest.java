package com.lyflexi.threadpractice.unsafeVariable.memberVariable;

import java.util.ArrayList;

/**
 * @Author: ly
 * @Date: 2024/3/14 21:26
 */
public class MustUnsafeTest {
    static final int THREAD_NUMBER = 2;
    static final int LOOP_NUMBER = 200;

    //成员变量是不安全的
    ArrayList<String> list = new ArrayList<>();
    public static void main(String[] args) {
        MustUnsafeTest test = new MustUnsafeTest();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> {
                test.method1(LOOP_NUMBER);
            }, "Thread" + (i+1)).start();
        }
    }
    public void method1(int loopNumber) {
        for (int i = 0; i < loopNumber; i++) {
            method2();
            method3();
        }
    }

    private void method2() {
        list.add("1");
    }

    private void method3() {
        list.remove(0);
    }
}
