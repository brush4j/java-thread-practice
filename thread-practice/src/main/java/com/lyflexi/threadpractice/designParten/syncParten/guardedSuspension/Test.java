package com.lyflexi.threadpractice.designParten.syncParten.guardedSuspension;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * @Author: ly
 * @Date: 2024/3/15 20:10
 */
@Slf4j(topic = "c.GuardedObjectTest")
public class Test {
    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject();

        new Thread(()->{
            log.debug("等待结果,,,");
            List<String> list = (List<String>)guardedObject.get();
            log.debug(list.toString());

        },"t1").start();

        new Thread(()-> {
            log.debug("执行下载,,,");

            List<String> download;
            try {
                download = Downloader.download();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            guardedObject.complete(download);

        },"t2").start();


    }
}
