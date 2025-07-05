package com.lyflexi.synclockpractice.juc.rwlock.rwcache;

import java.util.Arrays;
import java.util.Objects;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/7/5
 * @description：缓存唯一键由sql和args构成
 * @modifiedBy：
 * @version: 1.0
 */
public class SqlPair {
    private String sql;
    private Object[] args;

    public SqlPair(String sql, Object[] args) {
        this.sql = sql;
        this.args = args;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SqlPair sqlPair = (SqlPair) o;
        return Objects.equals(sql, sqlPair.sql) &&
                Arrays.equals(args, sqlPair.args);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(sql);
        result = 31 * result + Arrays.hashCode(args);
        return result;
    }
}
