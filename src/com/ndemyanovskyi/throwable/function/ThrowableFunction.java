/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.throwable.function;

/**
 *
 * @author Назарій
 */
@FunctionalInterface
public interface ThrowableFunction<T, R, E extends Throwable> {
    
    public R apply(T t) throws E;    
    
}
