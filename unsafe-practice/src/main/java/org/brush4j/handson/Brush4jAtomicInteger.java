package org.brush4j.handson;

import sun.misc.Unsafe;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/6/29
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class Brush4jAtomicInteger {
    private volatile int value;
    private static final long valueOffset;
    private static final Unsafe UNSAFE;
    static {
        UNSAFE = UnsafeAccessor.getUnsafe();
        try {
            valueOffset = UNSAFE.objectFieldOffset(Brush4jAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public int getValue() {
        return value;
    }

    /**
     * cas实现
     * @param amount
     */
    public void decrement(int amount) {
        while(true) {
            int prev = this.value;
            int next = prev - amount;
            if (UNSAFE.compareAndSwapInt(this, valueOffset, prev, next)) {
                break;
            }
        }
    }

    public Brush4jAtomicInteger(int value) {
        this.value = value;
    }
}
