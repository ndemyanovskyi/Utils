/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.util;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;
import java.util.function.Supplier;

/**
 *
 * @author Назарій
 */
public class Convert {
    
    private static <T> T tryOrNull(Supplier<T> supplier) {
        try {
            return supplier.get();
        } catch(RuntimeException ex) {
            return null;
        }
    }
        
    public static Integer toInteger(String value) {
        return tryOrNull(() -> Integer.valueOf(value));
    }
        
    public static Long toLong(String value) {
        return tryOrNull(() -> Long.valueOf(value));
    }
        
    public static Float toFloat(String value) {
        return tryOrNull(() -> Float.valueOf(value));
    }
        
    public static Double toDouble(String value) {
        return tryOrNull(() -> Double.valueOf(value));
    }
        
    public static Byte toByte(String value) {
        return tryOrNull(() -> Byte.valueOf(value));
    }
        
    public static Short toShort(String value) {
        return tryOrNull(() -> Short.valueOf(value));
    }
        
    public static Date toDate(LocalDate date) {
        return new Date(date.getYear() - 1900, date.getMonthValue() - 1, date.getDayOfMonth());
    }
    
    public static LocalDate toLocalDate(Date date) {
        return LocalDate.of(date.getYear() + 1900, date.getMonth() + 1, date.getDate());
    }
    
    public static String toString(Object o) {
        return Objects.toString(o);
    }
    
}
