package org.brush4j.handson;

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
public class UnsafeAccessor {
    private static final Unsafe unsafe;

    static {
        try {
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error(e);
        }
    }

    public static Unsafe getUnsafe() {
        return unsafe;
    }
}