/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.util;

import com.ndemyanovskyi.map.Pool;
import com.ndemyanovskyi.map.WeakHashPool;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Objects;

/**
 *
 * @author Назарій
 */
public class DateTimeFormatters {
    
    private static final Pool<String, DateTimeFormatter> CACHE = 
            new WeakHashPool<>(o -> DateTimeFormatter.ofPattern(Objects.toString(o)));
    
    private DateTimeFormatters() {}
    
    public static String format(String pattern, TemporalAccessor accessor) {
        return of(pattern).format(accessor);
    }
    
    public static DateTimeFormatter of(String pattern) {
        return CACHE.get(pattern);
    }
    
}
