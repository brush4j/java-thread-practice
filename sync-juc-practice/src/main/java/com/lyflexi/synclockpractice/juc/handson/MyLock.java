package com.lyflexi.synclockpractice.juc.handson;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
/**
 *自定义锁（简单起见，实现个不可重入锁）
 *
 * 子类主要实现这样一些方法（默认抛出 UnsupportedOperationException）
 *  tryAcquire
 *  tryRelease
 *  tryAcquireShared
 *  tryReleaseShared
 *  isHeldExclusively
 */
class MyLock implements Lock {

    // 独占锁  同步器类
    class MySync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            if(compareAndSetState(0, 1)) {
                // 加上了锁，并设置 owner 为当前线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            //解锁的时候当前线程拥有锁，必定没有其他线程竞争，因此不需要compareAndSetState
            setExclusiveOwnerThread(null);
            //state是volitile,此处会加写屏障：保证上一行代码setExclusiveOwnerThread刷新到主存
            setState(0);
            return true;
        }

        @Override // 是否持有独占锁
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        public Condition newCondition() {
            return new ConditionObject();
        }
    }

    private MySync sync = new MySync();

    @Override // 加锁（不成功会进入等待队列）
    public void lock() {
        sync.acquire(1);
    }

    /**
     * 这一点是aqs比synchronized灵活的地方，可以实现可打断的加锁
     * @throws InterruptedException
     */
    @Override // 加锁，可打断
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override // 尝试加锁（一次）
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override // 尝试加锁，带超时
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override // 解锁
    public void unlock() {
        sync.release(1);
    }

    @Override // 创建条件变量
    public Condition newCondition() {
        return sync.newCondition();
    }
}