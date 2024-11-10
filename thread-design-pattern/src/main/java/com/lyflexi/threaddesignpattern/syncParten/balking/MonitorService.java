package com.lyflexi.threaddesignpattern.syncParten.balking;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:
 * @Author: lyflexi
 * @project: java-thread-practice
 * @Date: 2024/11/10 11:04
 */
@Slf4j
/**
 * Balking （犹豫）模式用在一个线程发现另一个线程或本线程已经做了某一件相同的事，那么本线程就无需再做了，直接结束返回
 */
public class MonitorService {
    // 用来表示是否已经有线程已经在执行启动了
    private volatile boolean starting;

    //下述代码volatile无法保证同步性
//    public void start() {
//        log.info("尝试启动监控线程...");
//        if (starting) {
//            return;
//        }
//        starting = true;
//        
//    }

    /**
     * 必须使用synchronized保护同步性
     */
    public void start() {
        log.info("尝试启动监控线程...");
        synchronized (this) {
            if (starting) {
                return;
            }
            starting = true;
        }
    }
}
