package com.lyflexi.threadpractice.unsafeVariable.localVariable;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @Author: ly
 * @Date: 2024/3/14 23:10
 */


public abstract class UnSafeSubclassTest {

    public void bar() {
        // 是否安全
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        foo(sdf);
    }

    public abstract void foo(SimpleDateFormat sdf);


    public static void main(String[] args) {
        new SubClass().bar();
    }

    static class SubClass extends UnSafeSubclassTest{
        public void foo(SimpleDateFormat sdf) {
            String dateStr = "1999-10-11 00:00:00";
            for (int i = 0; i < 20; i++) {
                new Thread(() -> {
                    try {
                        sdf.parse(dateStr);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }

}


