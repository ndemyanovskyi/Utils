/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.listiterator;

import com.ndemyanovskyi.iterator.FilteredIterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;


public class FilteredListIterator<E> extends FilteredIterator<E> implements ListIterator<E> {
    
    private Object previous = NULL_OBJECT;
    private int index = -1;

    public FilteredListIterator(ListIterator<E> base, Predicate<? super E> condition) {
	super(base, condition);
    }

    @Override
    protected ListIterator<E> base() {
	return (ListIterator<E>) super.base();
    }

    @Override
    public E next() {
	E result = super.next();
	index++;
	return result;
    }

    @Override
    public boolean hasPrevious() {
	if(previous != NULL_OBJECT) {
	    return true;
	}

	while(base().hasPrevious()) {
	    E e = base().previous();
	    if(predicate().test(e)) {
		previous = e;
		return true;
	    }
	}
	return false;
    }

    @Override
    public E previous() {
	if(!hasPrevious()) {
	    throw new NoSuchElementException();
	}

	E result = (E) previous;
	previous = NULL_OBJECT;
	index--;
	return result;
    }

    @Override
    public int nextIndex() {
	return index + 1;
    }

    @Override
    public int previousIndex() {
	return index;
    }

    @Override
    public void set(E e) {
	throw new UnsupportedOperationException("set");
    }

    @Override
    public void add(E e) {
	throw new UnsupportedOperationException("add");
    }

}
