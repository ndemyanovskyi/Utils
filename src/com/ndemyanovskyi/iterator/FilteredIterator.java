/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Predicate;

public class FilteredIterator<E> implements Iterator<E> {

    protected static final Object NULL_OBJECT = new Object();

    private final Iterator<E> base;
    private final Predicate<? super E> predicate;
    private Object next = NULL_OBJECT;

    public FilteredIterator(Iterator<E> base, Predicate<? super E> predicate) {
	this.base = Objects.requireNonNull(base, "base");
	this.predicate = Objects.requireNonNull(predicate, "predicate");
    }
    
    protected Iterator<E> base() {
	return base;
    }

    public Predicate<? super E> predicate() {
	return predicate;
    }

    @Override
    public boolean hasNext() {
	if(next != NULL_OBJECT) {
	    return true;
	}

	while(base.hasNext()) {
	    E element = base.next();
	    if(predicate.test(element)) {
		next = element;
		return true;
	    }
	}
	return false;
    }

    @Override
    public E next() {
	if(!hasNext()) {
	    throw new NoSuchElementException();
	}

	E result = (E) next;
	next = NULL_OBJECT;
	return result;
    }

    @Override
    public void remove() {
	base.remove();
    }

}
