package com.lyflexi.caspractice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/6/14
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class AtomicReferenceTest {
    public static void main(String[] args) {
        AccountReferenceHandlerCas accountHandlerCas = new AccountReferenceHandlerCas(new BigDecimal("10000"));
        accountHandlerCas.process(accountHandlerCas);
    }

    static class AccountReferenceHandlerCas implements IAccountReferenceHandler {
        private AtomicReference<BigDecimal> balance;

        public AccountReferenceHandlerCas(BigDecimal balance) {
            this.balance = new AtomicReference<>(balance);
        }

        @Override
        public BigDecimal getBalance() {
            return balance.get();
        }

        @Override
        public void process(BigDecimal amount) {
//        while(true) {
//            BigDecimal prev = balance.get();
//            BigDecimal next = prev.subtract(amount);
//            if (balance.compareAndSet(prev, next)) {
//                break;
//            }
//        }
            balance.getAndUpdate(x-> x.subtract(amount));
        }
    }

    interface IAccountReferenceHandler {
        // 获取余额
        BigDecimal getBalance();

        // 取款
        void process(BigDecimal amount);

        /**
         * 方法内会启动 1000 个线程，每个线程做 -10 元 的操作
         * 如果初始余额为 10000 那么正确的结果应当是 0
         */
        default void process(IAccountReferenceHandler handler) {
            List<Thread> ts = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                ts.add(new Thread(() -> {
                    handler.process(BigDecimal.TEN);
                }));
            }
            long start = System.nanoTime();
            ts.forEach(Thread::start);
            ts.forEach(t -> {
                try {
                    t.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            long end = System.nanoTime();
            System.out.println(handler.getBalance()
                    + " cost: " + (end-start)/1000_000 + " ms");
        }
    }

}

