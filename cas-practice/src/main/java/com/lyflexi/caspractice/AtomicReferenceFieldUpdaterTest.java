package com.lyflexi.caspractice;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/6/14
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class AtomicReferenceFieldUpdaterTest {
    public static void main(String[] args) {
        Student stu = new Student();

        AtomicReferenceFieldUpdater updater =
                AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");

        //修改成功
        System.out.println(updater.compareAndSet(stu, null, "张三"));
        System.out.println(stu);
        //修改失败
        System.out.println(updater.compareAndSet(stu, "李四", "王五"));
        System.out.println(stu);
    }
    /**
     * @description: 利用字段更新器，可以针对对象的某个域（Field）进行原子操作，只能配合 volatile 修饰的字段使用，否则会出现异常
     * @author: hmly 
     * @date: 2025/6/14 19:02
     * @param: 
     * @return: 
     **/
    static class Student {
        volatile String name;

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}

