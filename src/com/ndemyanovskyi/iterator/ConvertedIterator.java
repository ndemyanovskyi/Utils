/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.iterator;

import com.ndemyanovskyi.util.Converter;
import java.util.Iterator;
import java.util.Objects;


public class ConvertedIterator<F, T> implements Iterator<T> {
    
    private final Iterator<F> base;
    private final Converter<F, T> converter;

    public ConvertedIterator(Iterator<F> base, Converter<F, T> converter) {
	this.converter = Objects.requireNonNull(converter);
	this.base = Objects.requireNonNull(base);
        
    }

    public Converter<F, T> converter() {
        return converter;
    }

    @Override
    public boolean hasNext() {
	return base.hasNext();
    }

    @Override
    public T next() {
	return converter.to(base.next());
    }

    @Override
    public void remove() {
	base.remove();
    }

}
