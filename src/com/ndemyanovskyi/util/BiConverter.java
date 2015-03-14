/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.util;

import java.util.Objects;

/**
 *
 * @author Назарій
 */
public interface BiConverter<F, T> extends Converter<F, T> {
    
    public F from(T value);
    
    public static <T> BiConverter<T, String> of(Converter<String, T> to) {
        return of(Object::toString, to);
    }
    
    public static <F, T> BiConverter<F, T> of(Converter<F, T> to, Converter<T, F> from) {
	Objects.requireNonNull(to, "to");
	Objects.requireNonNull(from, "from");
	
	return new BiConverter<F, T>() {

	    @Override
	    public T to(F value) {
		return to.to(value);
	    }

	    @Override
	    public F from(T value) {
		return from.to(value);
	    }
	    
	};
    }
    
    public static <T> BiConverter<T, T> identity() {
        return new BiConverter<T, T>() {

            @Override
            public T to(T value) {
                return value;
            }

            @Override
            public T from(T value) {
                return value;
            }
            
        };
    }
    
    public static <T> BiConverter<T, T> unsupported() {
        return new BiConverter<T, T>() {

            @Override
            public T to(T value) {
                throw new UnsupportedOperationException("to");
            }

            @Override
            public T from(T value) {
                throw new UnsupportedOperationException("from");
            }
            
        };
    }
    
}
