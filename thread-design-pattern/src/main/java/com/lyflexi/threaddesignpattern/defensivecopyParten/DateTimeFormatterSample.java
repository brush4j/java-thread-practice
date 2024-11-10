package com.lyflexi.threaddesignpattern.defensivecopyParten;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DecimalStyle;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalField;
import java.util.Locale;
import java.util.Set;

/**
 * @Description:
 * @Author: lyflexi
 * @project: java-thread-practice
 * @Date: 2024/11/10 11:36
 */
@Slf4j
public class DateTimeFormatterSample {



    /**
     * This class DateTimeFormatter is immutable and thread-safe.发现该类、类中所有属性都是 final 的
     *      属性用 final 修饰保证了该属性是只读的，不能修改
     *      类用 final 修饰保证了该类中的方法不能被覆盖，防止子类无意间破坏不可变性
     *     public final class DateTimeFormatter {
     *         private final DateTimeFormatterBuilder.CompositePrinterParser printerParser;
     *         private final Locale locale;
     *         private final DecimalStyle decimalStyle;
     *         private final ResolverStyle resolverStyle;
     *         private final Set<TemporalField> resolverFields;
     *         private final Chronology chrono;
     *         private final ZoneId zone;
     *         ...
     *     }
     *
     * @param args
     */
    public static void main(String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                LocalDate date = dtf.parse("2018-10-01", LocalDate::from);
                log.debug("{}", date);
            }).start();
        }
    }
}
