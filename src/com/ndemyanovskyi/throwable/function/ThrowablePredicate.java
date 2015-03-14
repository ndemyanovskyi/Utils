/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.throwable.function;

@FunctionalInterface
public interface ThrowablePredicate<T, E extends Throwable> {

    public boolean test(T t) throws E;
    
}
