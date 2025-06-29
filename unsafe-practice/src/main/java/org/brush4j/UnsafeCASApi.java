package org.brush4j;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/6/29
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
@Slf4j
public class UnsafeCASApi {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        //通过反射获取Unsafe类中的theUnsafe域
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        //由于域theUnsafe是静态的，属于类级别，因此反射get方法不用传递对象实例进去，传null即可
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);

        log.info("unsafe: {}", unsafe);

        // 1. 获取域的偏移地址，属性比如int或者string都有其固定的偏移地址，所以大胆的获取
        long idOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("id"));
        long nameOffset = unsafe.objectFieldOffset(Teacher.class.getDeclaredField("name"));

        Teacher t = new Teacher();
        // 2. 执行 cas 操作
        unsafe.compareAndSwapInt(t, idOffset, 0, 1);
        unsafe.compareAndSwapObject(t, nameOffset, null, "张三");

        // 3. 验证
        log.info("t");
    }
    @Data
    static class Teacher {
        volatile int id;
        volatile String name;
    }
}
