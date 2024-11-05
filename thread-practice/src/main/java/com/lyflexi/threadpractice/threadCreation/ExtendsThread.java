package com.lyflexi.threadpractice.threadCreation;

/**
 * @Author: ly
 * @Date: 2024/2/6 13:58
 */
public class ExtendsThread extends Thread {

    @Override
    public void run() {
        System.out.println("线程运行中...");
    }


    public static void main(String[] args) {
        ExtendsThread extendsThread = new ExtendsThread();
        extendsThread.start();
    }

}
