/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.listiterator;

import com.ndemyanovskyi.iterator.CombinedIterator;
import com.ndemyanovskyi.iterator.Iterators;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class CombinedListIterator<T> extends CombinedIterator<T> implements ListIterator<T> {
    
    private int index = -1;

    public CombinedListIterator(int position, List<? extends ListIterator<T>> iterators) {
	super(iterators);
	Iterators.move(position, this);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ListIterator<? extends ListIterator<T>> iterators() {
	return (ListIterator<? extends ListIterator<T>>) super.iterators();
    }

    @Override
    protected ListIterator<? extends T> getCurrent() {
	return (ListIterator<? extends T>) super.getCurrent();
    }

    @Override
    protected void setCurrent(Iterator<? extends T> current) {
	if(!(current instanceof ListIterator)) {
	    throw new ClassCastException("Iterator to ListIterator");
	}
	super.setCurrent(current);
    }

    @Override
    public boolean hasPrevious() {
	while(true) {
	    if(getCurrent() != null && getCurrent().hasPrevious()) {
		return true;
	    }
	    if(iterators().hasPrevious()) {
		setCurrent(iterators().previous());
	    } else {
		break;
	    }
	}
	return false;
    }

    @Override
    public T next() {
	T next = super.next();
	index++;
	return next;
    }

    @Override
    public T previous() {
	if(!hasPrevious()) {
	    throw new NoSuchElementException();
	}
	index--;
	return getCurrent().previous();
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
    public void set(T e) {
	((ListIterator<T>) getCurrent()).set(e);
    }

    @Override
    public void add(T e) {
	((ListIterator<T>) getCurrent()).add(e);
    }

}
