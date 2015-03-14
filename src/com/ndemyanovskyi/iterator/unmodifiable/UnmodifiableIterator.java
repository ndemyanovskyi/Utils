/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.iterator.unmodifiable;

import java.util.Iterator;
import java.util.Objects;


public class UnmodifiableIterator<T> extends AbstractUnmodifiableIterator<T> {
    
    private final Iterator<T> base;

    public UnmodifiableIterator(Iterator<T> base) {
	this.base = Objects.requireNonNull(base);
    }

    protected Iterator<T> base() {
	return base;
    }

    @Override
    public boolean hasNext() {
	return base().hasNext();
    }

    @Override
    public T next() {
	return base().next();
    }

}
