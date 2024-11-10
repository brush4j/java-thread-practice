package com.lyflexi.threadpractice.escapeonstack.localVariable;

import java.util.ArrayList;

/**
 * @Author: ly
 * @Date: 2024/3/14 21:26
 */
public class SafeTest {
    static final int THREAD_NUMBER = 2;
    static final int LOOP_NUMBER = 200;



    public static void main(String[] args) {
        SafeTest test = new SafeTest();
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> {
                test.method1(LOOP_NUMBER);
            }, "Thread" + (i+1)).start();
        }
    }
    public final void method1(int loopNumber) {
        //局部变量是安全的
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < loopNumber; i++) {
            method2(list);
            method3(list);
        }
    }

    //栈上逃逸，但不影响线程安全
    public void method2(ArrayList<String> list) {
        list.add("1");
    }
    //栈上逃逸，但不影响线程安全
    private void method3(ArrayList<String> list) {
        list.remove(0);
    }
}
