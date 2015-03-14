/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.util.beans;

import java.util.Objects;
import java.util.function.Predicate;

/**
 *
 * @author Назарій
 */
public class FinalPredicateProperty<T> extends FinalProperty<T> {
    
    Predicate<? super T> predicate;

    public FinalPredicateProperty(T value, Predicate<? super T> predicate) {
        this(null, "", value, predicate);
    }

    public FinalPredicateProperty(Object bean, String name, T value, Predicate<? super T> predicate) {
        super(bean, name, value);
        this.predicate = Objects.requireNonNull(predicate, "predicate");
        testOrThrow(value);
    }

    public Predicate<? super T> getPredicate() {
        return predicate;
    }
    
    public void testOrThrow(T value) {
        if(!test(value)) {
            throw new IllegalArgumentException();
        }
    }
    
    public boolean test(T value) {
        return getPredicate().test(value);
    }
    
}
