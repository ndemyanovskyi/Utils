/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.util;


@FunctionalInterface
public interface Converter<F, T> {
    
    public T to(F value);
    
    public static <T> Converter<T, T> identity() {
        return v -> v;
    }
    
    public static <F, T> Converter<F, T> unsupported() {
        return v -> {
            throw new UnsupportedOperationException();
        };
    }
    
}
