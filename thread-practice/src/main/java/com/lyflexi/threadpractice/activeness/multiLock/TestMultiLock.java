package com.lyflexi.threadpractice.activeness.multiLock;

/*"小南"线程和小女线程此时几乎可以同时运行，提升了并发度*/
public class TestMultiLock {
    public static void main(String[] args) {
        Room room = new Room();
        new Thread(() -> {
            try {
                room.study();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"小南").start();
        new Thread(() -> {
            try {
                room.sleep();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"小女").start();
    }
}


