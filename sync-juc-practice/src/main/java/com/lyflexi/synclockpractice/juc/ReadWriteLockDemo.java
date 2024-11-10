package com.lyflexi.synclockpractice.juc;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;




/**
 * @auther zzyy
 * @create 2022-04-08 18:18
 */
public class ReadWriteLockDemo
{
    public static void main(String[] args)
    {
        MyResource myResource = new MyResource();

        for (int i = 1; i <=10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.write(finalI +"", finalI +"");
            },String.valueOf(i)).start();
        }

        for (int i = 1; i <=10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.read(finalI +"");
            },String.valueOf(i)).start();
        }

    }

    /**
     * 资源类
     */
    static class MyResource
    {
        //模拟一个简单的缓存
        Map<String,String> map = new HashMap<>();
        //=====ReentrantReadWriteLock 一体两面，读写互斥，读读共享
        ReadWriteLock rwLock = new ReentrantReadWriteLock();

        public void write(String key ,String value)
        {
            rwLock.writeLock().lock();
            try
            {
                System.out.println(Thread.currentThread().getName()+"\t"+"正在写入");
                map.put(key,value);
                //暂停毫秒
                try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName()+"\t"+"完成写入");
            }finally {
                rwLock.writeLock().unlock();
            }
        }

        public void read(String key)
        {
            rwLock.readLock().lock();
            try
            {
                System.out.println(Thread.currentThread().getName()+"\t"+"正在读取");
                String result = map.get(key);
                //暂停200毫秒
                try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println(Thread.currentThread().getName()+"\t"+"完成读取"+"\t"+result);
            }finally {
                rwLock.readLock().unlock();
            }
        }


    }
}
