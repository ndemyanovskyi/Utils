/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.iterator;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;


public class CombinedIterator<T> implements Iterator<T> {
    
    private Iterator<? extends Iterator<T>> iterators;
    private Iterator<? extends T> current;

    public CombinedIterator(Collection<? extends Iterator<T>> iterators) {
	this.iterators = Objects.requireNonNull(iterators, "iterators").iterator();
    }

    @Override
    public boolean hasNext() {
	while(true) {
	    if(getCurrent() != null && getCurrent().hasNext()) {
		return true;
	    }
	    if(iterators().hasNext()) {
		setCurrent(iterators.next());
	    } else {
		break;
	    }
	}
	return false;
    }

    protected Iterator<? extends Iterator<T>> iterators() {
	return iterators;
    }

    protected Iterator<? extends T> getCurrent() {
	return current;
    }

    protected void setCurrent(Iterator<? extends T> current) {
	this.current = current;
    }

    @Override
    public T next() {
	if(!hasNext()) {
	    throw new NoSuchElementException();
	}
	return getCurrent().next();
    }

    @Override
    public void remove() {
	getCurrent().remove();
    }

}
