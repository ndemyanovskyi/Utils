/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.util.beans;

import java.util.function.Predicate;

/**
 *
 * @author Назарій
 */
public final class FinalNonNullProperty<T> extends FinalPredicateProperty<T> {
    
    public static final Predicate<Object> PREDICATE = o -> o != null;

    public FinalNonNullProperty(T value) {
        super(value, PREDICATE);
    }

    public FinalNonNullProperty(Object bean, String name, T value) {
        super(bean, name, value, PREDICATE);
    }

    @Override
    public void testOrThrow(T value) {
        if(!test(value)) {
            throw new NullPointerException(getName());
        }
    }
    
}
