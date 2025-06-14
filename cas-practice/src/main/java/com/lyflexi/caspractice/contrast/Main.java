package com.lyflexi.caspractice.contrast;

/**
 * Created with IntelliJ IDEA.
 *
 * @author： hmly
 * @date： 2025/6/14
 * @description：
 * @modifiedBy：
 * @version: 1.0
 */
public class Main {
    public static void main(String[] args) {
        IAccountHandler accountUnsafeHandler = new AccountUnsafeHandler(10000);
        accountUnsafeHandler.process(accountUnsafeHandler);

        IAccountHandler accountSyncHandler = new AccountSyncHandler(10000);
        accountUnsafeHandler.process(accountSyncHandler);

        IAccountHandler accountCasHandler = new AccountCasHandler(10000);
        accountUnsafeHandler.process(accountCasHandler);
    }
}
