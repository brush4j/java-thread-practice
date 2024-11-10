package com.lyflexi.threaddesignpattern.defensivecopyParten;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * @Description:
 * @Author: lyflexi
 * @project: java-thread-practice
 * @Date: 2024/11/10 11:32
 */
@Slf4j
public class SimpleDateFormatSamlple {
    /**
     * 下面的代码在运行时，由于 SimpleDateFormat 不是线程安全的
     * 有很大几率出现 java.lang.NumberFormatException 或者出现不正确的日期解析结果，例如：
     * @param args
     */
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    log.debug("{}", sdf.parse("1951-04-21"));
                }
                catch (Exception e) {
                    log.error("{}", e);
                }
            }).start();
        }
    }
}


