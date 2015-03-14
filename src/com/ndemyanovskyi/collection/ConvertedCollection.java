/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.collection;

import com.ndemyanovskyi.iterator.Iterators;
import com.ndemyanovskyi.util.BiConverter;
import com.ndemyanovskyi.util.Converter;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;


public class ConvertedCollection<F, T> extends AbstractCollection<T> {
    
    private final BiConverter<T, F> converter;
    private final Converter<Object, T> caster;
    private final Collection<F> base;
    
    public ConvertedCollection(Collection<F> base, Class<T> valueClass, Converter<T, F> to, Converter<F, T> from) {
	this(base, casterFrom(valueClass), BiConverter.of(to, from));
    }
    
    public ConvertedCollection(Collection<F> base, Converter<Object, T> caster, Converter<T, F> to, Converter<F, T> from) {
	this(base, caster, BiConverter.of(to, from));
    }
    
    public ConvertedCollection(Collection<F> base, Converter<Object, T> caster, BiConverter<T, F> converter) {
	this.base = Objects.requireNonNull(base, "base");
	this.caster = Objects.requireNonNull(caster, "caster");
	this.converter = Objects.requireNonNull(converter, "converter");
    }
    
    public ConvertedCollection(Collection<F> base, Class<T> valueClass, BiConverter<T, F> converter) {
	this(base, casterFrom(valueClass), converter);
    }
    
    protected static <T> Converter<Object, T> casterFrom(Class<T> c) {
	return o -> c.isInstance(o) ? (T) o : null;
    }

    public BiConverter<T, F> converter() {
	return converter;
    }

    public Converter<Object, T> caster() {
	return caster;
    }

    @Override
    public int size() {
	return base.size();
    }

    protected Collection<F> base() {
	return base;
    }

    @Override
    public Iterator<T> iterator() {
	return Iterators.converted(
		base.iterator(), converter::from);
    }

    @Override
    public boolean add(T e) {
	return base.add(converter.to(e));
    }

    @Override
    public boolean remove(Object o) {
	return (o != null) 
		? base.remove(converter.to(caster.to(o))) 
		: base.remove(null);
    }

    @Override
    public void clear() {
	base.clear();
    }

}
