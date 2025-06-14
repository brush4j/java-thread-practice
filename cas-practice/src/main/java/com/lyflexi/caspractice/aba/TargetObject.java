package com.lyflexi.caspractice.aba;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TargetObject {
    private String name;
    private Integer age;
    public static TargetObject of(String name, int age) {
        return new TargetObject(name, age);
    }
}
