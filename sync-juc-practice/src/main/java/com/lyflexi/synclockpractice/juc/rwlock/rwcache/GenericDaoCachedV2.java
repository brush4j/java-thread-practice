package com.lyflexi.synclockpractice.juc.rwlock.rwcache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/7/5
 * @description： 带缓存功能的DAO
 * @modifiedBy：
 * @version: 1.0
 */
public class GenericDaoCachedV2 extends GenericDao {
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

    /**
     * 推荐先更新数据库，后清空缓存
     * @param sql
     * @param args
     * @return
     */
    @Override
    public int update(String sql, Object... args) {
        //更新数据库
        int update = dao.update(sql, args);
        // 清空缓存
        map.clear();
        return update;
    }
}
