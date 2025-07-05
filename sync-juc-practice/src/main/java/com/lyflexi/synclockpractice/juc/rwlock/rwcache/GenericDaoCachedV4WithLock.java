package com.lyflexi.synclockpractice.juc.rwlock.rwcache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/7/5
 * @description： 带缓存功能的DAO
 * @modifiedBy：
 * @version: 1.0
 */
public class GenericDaoCachedV4WithLock extends GenericDao {
    private GenericDao dao = new GenericDao();
    private Map<SqlPair, Object> map = new HashMap<>();
    private Lock lock = new ReentrantLock();

    @Override
    public <T> List<T> queryList(Class<T> beanClass, String sql, Object... args) {
        return dao.queryList(beanClass, sql, args);
    }

    @Override
    public <T> T queryOne(Class<T> beanClass, String sql, Object... args) {
        // 先从缓存中找，找到直接返回
        SqlPair key = new SqlPair(sql, args);;

        T value = (T) map.get(key);
        if(value != null) {
            return value;
        }

        //对写缓存加锁的原因是，防止系统冷启动的时候大量线程全部涌入DB
        lock.lock();
        try {
            value = (T) map.get(key);
            if (value == null) {//DCL
                // 缓存中没有，查询数据库
                value = dao.queryOne(beanClass, sql, args);
                map.put(key, value);
            }
            return value;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int update(String sql, Object... args) {
        lock.lock();
        try {
            //更新数据库
            int update = dao.update(sql, args);
            // 清空缓存
            map.clear();
            return update;
        } finally {
            lock.unlock();
        }

    }
}
