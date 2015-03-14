/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection.unmodifiable;

import com.ndemyanovskyi.util.Converter;
import com.ndemyanovskyi.iterator.Iterators;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

/**
 *
 * @author Назарій
 */
public class UnmodifiableConvertedCollection<F, T> extends AbstractUnmodifiableCollection<T> {

    private final Converter<F, T> converter;
    private final Collection<F> base;

    public UnmodifiableConvertedCollection(Collection<F> collection, Converter<F, T> converter) {
        this.base = Objects.requireNonNull(collection, "collection");
        this.converter = Objects.requireNonNull(converter, "converter");
    }

    protected Collection<F> base() {
        return base;
    }
    
    @Override
    public Iterator<T> iterator() {
        return Iterators.unmodifiableConverted(base.iterator(), converter);
    }

    public Converter<F, T> converter() {
        return converter;
    }

    @Override
    public int size() {
        return base.size();
    }
    
}
