package com.lyflexi.synclockpractice.juc.rwlock.rwcache;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/7/5
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class Main {
    public static void main(String[] args) {
//        genericDaoWithoutCache();
//        genericDaoCachedV1();
//        genericDaoCachedV2();
//        genericDaoCachedV3WithLock();
//        genericDaoCachedV4WithLock();
        genericDaoCachedV5WithLock();
    }

    /**
     * 不带缓存
     */
    private static void genericDaoWithoutCache() {
        GenericDao dao = new GenericDao();
        System.out.println("============> 查询");
        String sql = "select * from emp where empno = ?";
        int empno = 7369;
        Emp emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);

        System.out.println("============> 更新");
        dao.update("update emp set sal = ? where empno = ?", 800, empno);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
    }

    /**
     * 缓存更新策略：（不推荐, 因为更新数据库速度要远远小于查询数据库速度，有可能其他线程会将更新前的数据放回缓存）
     * 先清空缓存
     * 后更新数据库
     */
    private static void genericDaoCachedV1() {
        GenericDao dao = new GenericDaoCachedV1();
        System.out.println("============> 查询");
        String sql = "select * from emp where empno = ?";
        int empno = 7369;
        Emp emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);

        System.out.println("============> 更新");
        dao.update("update emp set sal = ? where empno = ?", 800, empno);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
    }

    /**
     * 缓存更新策略是：（推荐）
     * 先更新数据库
     * 后情况缓存
     *
     */
    private static void genericDaoCachedV2() {
        GenericDao dao = new GenericDaoCachedV2();
        System.out.println("============> 查询");
        String sql = "select * from emp where empno = ?";
        int empno = 7369;
        Emp emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);

        System.out.println("============> 更新");
        dao.update("update emp set sal = ? where empno = ?", 800, empno);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
    }

    /**
     * 加锁进一步保证更新操作的，更新数据库+清空缓存的原子性
     */
    private static void genericDaoCachedV3WithLock() {
        GenericDao dao = new GenericDaoCachedV3WithLock();
        System.out.println("============> 查询");
        String sql = "select * from emp where empno = ?";
        int empno = 7369;
        Emp emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);

        System.out.println("============> 更新");
        dao.update("update emp set sal = ? where empno = ?", 800, empno);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
    }

    /**
     * 对读操作的写缓存加锁的原因是，防止系统冷启动的时候大量线程全部涌入DB
     */
    private static void genericDaoCachedV4WithLock() {
        GenericDao dao = new GenericDaoCachedV4WithLock();
        System.out.println("============> 查询");
        String sql = "select * from emp where empno = ?";
        int empno = 7369;
        Emp emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);

        System.out.println("============> 更新");
        dao.update("update emp set sal = ? where empno = ?", 800, empno);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
    }

    /**
     * 更加严谨的做法是读写锁，保证读写互斥,读的时候禁止更新
     */
    private static void genericDaoCachedV5WithLock() {
        GenericDao dao = new GenericDaoCachedV5WithLock();
        System.out.println("============> 查询");
        String sql = "select * from emp where empno = ?";
        int empno = 7369;
        Emp emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);

        System.out.println("============> 更新");
        dao.update("update emp set sal = ? where empno = ?", 800, empno);
        emp = dao.queryOne(Emp.class, sql, empno);
        System.out.println(emp);
    }
}
