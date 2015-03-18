/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

/**
 *
 * @author Назарій
 */
public class Compare {
    
    public static <T extends Comparable<? super T>> int compare(T a, T b) {
        return a.compareTo(b);
    }
    
    public static <T extends Comparable<? super T>> int compareNullable(T a, T b) {
        if(a == null && b == null) return 0;
        if(a == null) return -1;
        if(b == null) return 1;
        return compare(a, b);
    }
    
    public static <T extends Comparable<? super T>> T max(T a, T b) {
        return compareNullable(a, b) > 0 ? a : b;
    }
    
    public static <T extends Comparable<? super T>> boolean less(T a, T b) {
        return compareNullable(a, b) < 0;
    }
    
    public static <T extends Comparable<? super T>> boolean greater(T a, T b) {
        return compareNullable(a, b) > 0;
    }
    
    public static <T extends Comparable<? super T>> boolean lessOrEquals(T a, T b) {
        return compareNullable(a, b) <= 0;
    }
    
    public static <T extends Comparable<? super T>> boolean notGreater(T a, T b) {
        return lessOrEquals(a, b);
    }
    
    public static <T extends Comparable<? super T>> boolean notLess(T a, T b) {
        return greaterOrEquals(a, b);
    }
    
    public static <T extends Comparable<? super T>> boolean greaterOrEquals(T a, T b) {
        return compareNullable(a, b) >= 0;
    }
    
    public static <T extends Comparable<? super T>> boolean equals(T a, T b) {
        return compareNullable(a, b) == 0;
    }
    
    public static <T extends Comparable<? super T>> boolean notEquals(T a, T b) {
        return compareNullable(a, b) != 0;
    }
    
    public static boolean equals(Object a, Object b) {
        return Objects.equals(a, b);
    }
    
    public static boolean notEquals(Object a, Object b) {
        return !equals(a, b);
    }
    
    public static <T extends Comparable<? super T>> boolean equals(T a, T b, T... others) {
        if(notEquals(a, b)) return false;
        T previous = b;
        for(T t : others) {
            if(notEquals(previous, t)) return false;
            previous = t;
        }
        return true;
    }
    
    public static <T extends Comparable<? super T>> boolean equals(T[] array) {
        if(array.length == 0) {
            throw new IllegalArgumentException("array.length = 0");
        }
        for(int i = 1; i < array.length; i++) {
            if(notEquals(array[i - 1], array[i])) return false;
        }
        return true;
    }
    
    public static <T extends Comparable<? super T>> boolean notEquals(T a, T b, T... others) {
        if(equals(a, b)) return false;
        for(T other : others) {
            if(equals(a, other)) return false;
        }
        for(T other : others) {
            if(equals(b, other)) return false;
        }
        return notEquals(others);
    }
    
    public static boolean equals(Object a, Object b, Object... others) {
        if(notEquals(a, b)) return false;
        Object previous = b;
        for(Object t : others) {
            if(notEquals(previous, t)) return false;
            previous = t;
        }
        return true;
    }
    
    public static boolean equals(Object[] array) {
        if(array.length == 0) {
            throw new IllegalArgumentException("array.length = 0");
        }
        for(int i = 1; i < array.length; i++) {
            if(notEquals(array[i - 1], array[i])) return false;
        }
        return true;
    }
    
    public static boolean equals(Collection<?> collection) {
        if(collection.isEmpty()) {
            throw new IllegalArgumentException("collection.size() = 0");
        }
        Iterator<?> it = collection.iterator();
        Object previous = it.next();
        while(it.hasNext()) {
            Object current = it.next();
            if(notEquals(previous, current)) return false;
            previous = current;
        }
        return true;
    }
    
    public static boolean notEquals(Object a, Object b, Object... others) {
        if(equals(a, b)) return false;
        for(Object other : others) {
            if(equals(a, other)) return false;
        }
        for(Object other : others) {
            if(equals(b, other)) return false;
        }
        return notEquals(others);
    }
    
    public static <T extends Comparable<? super T>> boolean notEquals(T[] array) {
        if(array.length == 0) {
            throw new IllegalArgumentException("array.length = 0");
        }
        for(int i = 0; i < array.length - 1; i++) {
            for(int j = i + 1; j < array.length; j++) {
                if(equals(array[i], array[j])) return false;
            }
        }
        return true;
    }
    
    public static boolean notEquals(Object[] array) {
        if(array.length == 0) {
            throw new IllegalArgumentException("array.length = 0");
        }
        for(int i = 0; i < array.length - 1; i++) {
            for(int j = i + 1; j < array.length; j++) {
                if(equals(array[i], array[j])) return false;
            }
        }
        return true;
    }
    
    public static boolean notEquals(Collection<?> collection) {
        if(collection.isEmpty()) {
            throw new IllegalArgumentException("collection.size() = 0");
        }
        
        Iterator<?> itI = collection.iterator();
        int i = 0;
        while(itI.hasNext()) {
            Object current = itI.next();
            
            Iterator<?> itJ = collection.iterator();
            int j = 0; 
            while(j++ <= i && itJ.hasNext()) {
                itJ.next();
            }            
            while(itJ.hasNext()) {
                if(equals(current, itJ.next())) return false;
            }
        }
        return true;
    }
    
    public static <T extends Comparable<? super T>> T min(T a, T b) {
        return compareNullable(a, b) < 0 ? a : b;
    }
    
    public static <T extends Comparable<? super T>> T min(T a, T b, T... values) {
        Objects.requireNonNull(values, "values");
        T min = min(a, b);
        for(T value : values) {
            min = min(min, value);
        }
        return min;
    }
    
    public static <T extends Comparable<? super T>> T max(T a, T b, T... values) {
        Objects.requireNonNull(values, "values");
        T max = max(a, b);
        for(T value : values) {
            max = max(max, value);
        }
        return max;
    }
    
    public static <T extends Comparable<? super T>> T max(T[] values) {
        Objects.requireNonNull(values, "values");
        if(values.length == 0) {
            throw new IllegalArgumentException("values.length == 0");
        }
        
        T max = values[0];
        for(T value : values) {
            max = max(max, value);
        }
        return max;
    }
    
    public static <T extends Comparable<? super T>> T min(T[] values) {
        Objects.requireNonNull(values, "values");
        if(values.length == 0) {
            throw new IllegalArgumentException("values.length == 0");
        }
        
        T min = values[0];
        for(T value : values) {
            min = min(min, value);
        }
        return min;
    }
    
    public static <F, T extends Comparable<? super T>> T min(Function<F, T> extractor, Collection<? extends F> values) {
        Objects.requireNonNull(extractor, "extractor");
        Objects.requireNonNull(values, "values");
        
        Iterator<? extends F> it = values.iterator();
        
        if(!it.hasNext()) {
            throw new IllegalArgumentException("values.size() == 0");
        }
        
        T min = extractor.apply(it.next());
        while(it.hasNext()) {
            min = min(min, extractor.apply(it.next()));
        }
        return min;
    }
    
    public static <F, T extends Comparable<? super T>> T max(Function<F, T> extractor, Collection<? extends F> values) {
        Objects.requireNonNull(extractor, "extractor");
        Objects.requireNonNull(values, "values");
        
        Iterator<? extends F> it = values.iterator();
        
        if(!it.hasNext()) {
            throw new IllegalArgumentException("values.size() == 0");
        }
        
        T max = extractor.apply(it.next());
        while(it.hasNext()) {
            max = max(max, extractor.apply(it.next()));
        }
        return max;
    }
    
    public static <T extends Comparable<? super T>> T max(Collection<T> values) {
        Objects.requireNonNull(values, "values");
        
        Iterator<T> it = values.iterator();
        
        if(!it.hasNext()) {
            throw new IllegalArgumentException("values.size() == 0");
        }
        
        T max = it.next();
        while(it.hasNext()) {
            max = max(max, it.next());
        }
        return max;
    }
    
    public static <T extends Comparable<? super T>> T min(Collection<T> values) {
        Objects.requireNonNull(values, "values");
        
        Iterator<T> it = values.iterator();
        
        if(!it.hasNext()) {
            throw new IllegalArgumentException("values.size() == 0");
        }
        
        T min = it.next();
        while(it.hasNext()) {
            min = min(min, it.next());
        }
        return min;
    }
    
    public static <F, T extends Comparable<? super T>> T min(Function<F, T> extractor, F a, F b, F... others) {
        Objects.requireNonNull(extractor, "extractor");
        Objects.requireNonNull(others, "others");
        
        T min = min(extractor.apply(a), extractor.apply(b));
        for(F value : others) {
            min = min(min, extractor.apply(value));
        }
        return min;
    }
    
    public static <F, T extends Comparable<? super T>> T max(Function<F, T> extractor, F a, F b, F... others) {
        Objects.requireNonNull(extractor, "extractor");
        Objects.requireNonNull(others, "others");
        
        T max = max(extractor.apply(a), extractor.apply(b));
        for(F value : others) {
            max = max(max, extractor.apply(value));
        }
        return max;
    }
    
    public static <F, T extends Comparable<? super T>> T max(Function<F, T> extractor, F a, F b) {
        Objects.requireNonNull(extractor, "extractor");
        return max(extractor.apply(a), extractor.apply(b));
    }
    
    public static <F, T extends Comparable<? super T>> T min(Function<F, T> extractor, F a, F b) {
        Objects.requireNonNull(extractor, "extractor");
        return min(extractor.apply(a), extractor.apply(b));
    }
    
    public static <F, T extends Comparable<? super T>> T min(Function<F, T> extractor, F[] values) {
        Objects.requireNonNull(extractor, "extractor");
        Objects.requireNonNull(values, "values");
        if(values.length == 0) {
            throw new IllegalArgumentException("values.length == 0");
        }
        
        T min = extractor.apply(values[0]);
        for(F value : values) {
            min = min(min, extractor.apply(value));
        }
        return min;
    }
    
    public static <F, T extends Comparable<? super T>> T max(Function<F, T> extractor, F[] values) {
        Objects.requireNonNull(extractor, "extractor");
        Objects.requireNonNull(values, "values");
        if(values.length == 0) {
            throw new IllegalArgumentException("values.length == 0");
        }
        
        T max = extractor.apply(values[0]);
        for(F value : values) {
            max = max(max, extractor.apply(value));
        }
        return max;
    }
    
    public static int max(int a, int b) {
        return a > b ? a : b;
    }
    
    public static long max(long a, long b) {
        return a > b ? a : b;
    }
    
    public static float max(float a, float b) {
        return a > b ? a : b;
    }
    
    public static double max(double a, double b) {
        return a > b ? a : b;
    }
    
    public static byte max(byte a, byte b) {
        return a > b ? a : b;
    }
    
    public static short max(short a, short b) {
        return a > b ? a : b;
    }
    
}
