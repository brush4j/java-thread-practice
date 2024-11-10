package com.lyflexi.threaddesignpattern.syncParten.alternatelySequence.bySync;

/**
 * @Author: ly
 * @Date: 2024/3/18 21:23
 */

/*
输出内容       等待标记     下一个标记
   a           1             2
   b           2             3
   c           3             1
 */
class WaitNotify {
    // 打印               a           1             2
    public void print(String str, int waitFlag, int nextFlag) {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this) {
                while(flag != waitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(str);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }

    // 等待标记
    private int flag; // 2
    // 循环次数
    private int loopNumber;

    public WaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }
}
