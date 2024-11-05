package com.lyflexi.threadpractice.jucbilibili.base;

/**
 * @auther zzyy
 * @create 2022-01-12 16:03
 */
public class ThreadBaseDemo
{
    public static void main(String[] args) throws InterruptedException
    {
        Thread t1 = new Thread(() -> {

        },"t1");
        t1.start();

    }
}

// java = C++ ---》  (C++)-- = java