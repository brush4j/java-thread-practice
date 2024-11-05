package com.lyflexi.threadpractice.designParten.twoPhaseTermination;

import lombok.extern.slf4j.Slf4j;

/**
 * @Author: ly
 * @Date: 2024/3/14 11:35
 */
@Slf4j(topic = "c.TwoPhaseTermination")
public class TwoPhaseTermination {
    // 监控线程
    private Thread monitorThread;

    // 启动监控线程
    public void start() {

        monitorThread = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                // 是否被打断
                if (current.isInterrupted()) {
                    log.debug("料理后事");
                    break;
                }
                try {
                    Thread.sleep(1000);//可能的打断点1
                    log.debug("执行监控记录");//可能的打断点2
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    current.interrupt();//一定要加上这句话
                }
            }
        }, "monitor");
        monitorThread.start();
    }

    // 停止监控线程
    public void stop() {
        monitorThread.interrupt();
    }
}
