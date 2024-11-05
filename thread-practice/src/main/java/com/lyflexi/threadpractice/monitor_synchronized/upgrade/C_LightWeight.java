package com.lyflexi.threadpractice.monitor_synchronized.upgrade;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;


public class C_LightWeight
{
    public static void main(String[] args)
    {
        //先睡眠5秒，保证开启偏向锁
        try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
        Object o = new Object();

        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        o.hashCode();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
        synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }

        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}