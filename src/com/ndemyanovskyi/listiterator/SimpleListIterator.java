/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.listiterator;

import com.ndemyanovskyi.util.number.Numbers;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public abstract class SimpleListIterator<E> implements ListIterator<E> {
    
    private int position = -1;
    private int size = 0;
    private boolean removed = false;

    protected SimpleListIterator(int size) {
	this.size = Numbers.Integers.require(size, (s) -> s >= 0);
    }

    @Override
    public boolean hasNext() {
	return position < size;
    }

    @Override
    public E next() {
	if(!hasNext()) {
	    throw new NoSuchElementException();
	}
	removed = false;
	return get(++position);
    }

    @Override
    public boolean hasPrevious() {
	return position >= 0;
    }

    @Override
    public E previous() {
	if(!hasPrevious()) {
	    throw new NoSuchElementException();
	}
	removed = false;
	return get(position--);
    }

    @Override
    public int nextIndex() {
	return position + 1;
    }

    @Override
    public int previousIndex() {
	return position;
    }

    @Override
    public void remove() {
	if(removed == true) {
	    throw new IllegalStateException("Element is alredy removed.");
	}
	if(position < 0) {
	    throw new IllegalStateException("Iterator in the begin position.");
	}
	remove(position);
        position--;
	removed = true;
	size--;
    }

    @Override
    public void set(E e) {
	if(previousIndex() == -1) {
	    throw new IllegalStateException("Iterator in the begin position.");
	}
	set(position, e);
    }

    @Override
    public void add(E e) {
	add(position + 1, e);
        position++;
	size++;
    }

    protected abstract E get(int index);

    protected abstract void set(int index, E e);

    protected abstract void remove(int index);

    protected abstract void add(int index, E e);

}
