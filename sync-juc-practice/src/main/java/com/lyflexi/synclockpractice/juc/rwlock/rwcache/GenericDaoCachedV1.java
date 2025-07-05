package com.lyflexi.synclockpractice.juc.rwlock.rwcache;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/7/5
 * @description： 带缓存功能的DAO
 * @modifiedBy：
 * @version: 1.0
 */
public class GenericDaoCachedV1 extends GenericDao {
    private GenericDao dao = new GenericDao();
    private Map<SqlPair, Object> map = new HashMap<>();

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

        // 缓存中没有，查询数据库
        value = dao.queryOne(beanClass, sql, args);
        map.put(key, value);
        return value;
    }

    @Override
    public int update(String sql, Object... args) {
        // 清空缓存
        map.clear();
        //更新数据库
        return dao.update(sql, args);
    }
}
