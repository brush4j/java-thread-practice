使用 Semaphore 限流，在访问高峰期时，让请求线程阻塞，高峰期过去再释放许可
- 当然它只适合限制单机线程数量，
- 仅是限制线程数，而不是限制资源数

用 Semaphore 实现简单连接池，对比『享元模式』下的实现（用wait notify），性能和可读性显然更好，

注意下面的实现中线程数和数据库连接数是相等的，不推荐线程数与资源数不相等的场景下使用Semaphore
```java
    public ConnectPool(int poolSize) {
        this.poolSize = poolSize;
        // 让许可数与资源数一致
        this.semaphore = new Semaphore(poolSize);
        this.connections = new Connection[poolSize];
        this.states = new AtomicIntegerArray(new int[poolSize]);
        for (int i = 0; i < poolSize; i++) {
            connections[i] = new MockConnection("连接" + (i+1));
        }
}
```