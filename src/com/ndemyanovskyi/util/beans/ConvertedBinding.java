package com.ndemyanovskyi.util.beans;


import com.ndemyanovskyi.util.BiConverter;
import com.ndemyanovskyi.util.Converter;
import java.util.Objects;
import javafx.beans.WeakListener;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Назарій
 */
public abstract class ConvertedBinding<T1, T2> implements ChangeListener, WeakListener {

    public abstract Property<T1> getProperty1();
    public abstract Property<T2> getProperty2();

    @Override
    public boolean wasGarbageCollected() {
        return (getProperty1() == null) || (getProperty2() == null);
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) return true;
        if(o == null) return false;
        if(!(o instanceof ConvertedBinding)) return false;
        
        ConvertedBinding<?, ?> other = (ConvertedBinding<?, ?>) o;
        return Objects.equals(getProperty1(), other.getProperty1()) 
                && Objects.equals(getProperty2(), other.getProperty2());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(getProperty1());
        hash = 41 * hash + Objects.hashCode(getProperty2());
        return hash;
    }
    
    public static <T1, T2> void bind(Property<T1> p1, Property<T2> p2, BiConverter<T1, T2> converter) {
        new BiConvertedBindingImpl<>(p1, p2, converter);
    }
    
    public static <T1, T2> void bind(Property<T1> p1, Property<T2> p2, Converter<T1, T2> to, Converter<T2, T1> from) {
        bind(p1, p2, BiConverter.of(to, from));
    }
    
    public static <T> void bind(Property<String> p1, Property<T> p2, Converter<String, T> to) {
        bind(p1, p2, to, Object::toString);
    }
    
    public static <T1, T2> void unbind(Property<T1> p1, Property<T2> p2) {
        ConvertedBinding b = new UntypedBinding(p1, p2);
        p1.removeListener(b);
        p2.removeListener(b);
    }
    
}
