/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.collection.list;

import com.ndemyanovskyi.util.BiConverter;
import com.ndemyanovskyi.util.Converter;
import com.ndemyanovskyi.collection.ConvertedCollection;
import java.util.List;


public class ConvertedList<F, T> extends ConvertedCollection<F, T> implements DefaultList<T> {
    
    public ConvertedList(List<F> base, Class<T> valueClass, Converter<T, F> to, Converter<F, T> from) {
	super(base, casterFrom(valueClass), BiConverter.of(to, from));
    }
    
    public ConvertedList(List<F> base, Converter<Object, T> caster, Converter<T, F> to, Converter<F, T> from) {
	super(base, caster, BiConverter.of(to, from));
    }
    
    public ConvertedList(List<F> base, Converter<Object, T> caster, BiConverter<T, F> converter) {
	super(base, caster, converter);
    }
    
    public ConvertedList(List<F> base, Class<T> valueClass, BiConverter<T, F> converter) {
	super(base, casterFrom(valueClass), converter);
    }

    @Override
    protected List<F> base() {
	return (List<F>) super.base();
    }
    
    @Override
    public T get(int index) {
	return converter().from(base().get(index));
    }

}
