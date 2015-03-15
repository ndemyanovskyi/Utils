/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.util.beans;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;

/**
 *
 * @author Назарій
 */
public class FinalProperty<T> extends ReadOnlyObjectProperty<T> {
    
    private final T value;
    private final String name;
    private final Object bean;

    public FinalProperty(T value) {
        this(null, "", value);
    }

    public FinalProperty(Object bean, String name, T value) {
        this.name = name != null ? name : "";
        this.bean = bean;
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }

    @Override
    public Object getBean() {
        return bean;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addListener(ChangeListener<? super T> listener) {}

    @Override
    public void removeListener(ChangeListener<? super T> listener) {}

    @Override
    public void addListener(InvalidationListener listener) {}

    @Override
    public void removeListener(InvalidationListener listener) {}
    
}
