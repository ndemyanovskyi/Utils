/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.listiterator;

import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;


public class DefaultListIterator<T> implements ListIterator<T> {
    
    private final List<T> list;
    private final int offset;
    
    private int position = -1;
    private int size = 0;
    private boolean removed = false;

    public DefaultListIterator(int fromInclusive, int toExclusive, int start, List<T> list) {
        this.list = Objects.requireNonNull(list, "list");
        this.size = list.size();
        
	if(fromInclusive < 0) {
	    throw new IndexOutOfBoundsException("fromInclusive < 0");
	}
	if(toExclusive > list.size()) {
	    throw new IndexOutOfBoundsException("toExclusive > list.size()");
	}
	if(start < fromInclusive) {
	    throw new IndexOutOfBoundsException("start < fromInclusive");
	}
	if(start > toExclusive) {
	    throw new IndexOutOfBoundsException("start > toExclusive");
	}
        
	this.position = start - 1;
	this.offset = fromInclusive;
	this.size = toExclusive - fromInclusive;
    }

    public DefaultListIterator(int start, List<T> list) {
        this.list = Objects.requireNonNull(list, "list");
        this.size = list.size();
        this.offset = 0;
        
        if(start > size) {
            throw new IndexOutOfBoundsException(
                    "start = " + start + "; bounds = [0," + size + "]");
        }
        
        this.position = start - 1;
    }

    @Override
    public boolean hasNext() {
	return position < size + offset - 1;
    }

    @Override
    public T next() {
	if(!hasNext()) {
	    throw new NoSuchElementException();
	}
	removed = false;
	return get(++position);
    }

    @Override
    public boolean hasPrevious() {
	return position >= offset;
    }

    @Override
    public T previous() {
	if(!hasPrevious()) {
	    throw new NoSuchElementException();
	}
	removed = false;
	return get(position--);
    }

    @Override
    public int nextIndex() {
	return position - offset + 1;
    }

    @Override
    public int previousIndex() {
	return position - offset;
    }

    @Override
    public void remove() {
	if(removed == true) {
	    throw new IllegalStateException("Element is alredy removed.");
	}
	if(position < 0) {
	    throw new IllegalStateException("Iterator in the begin position.");
	}
	list.remove(position--);
	removed = true;
	size--;
    }

    @Override
    public void set(T e) {
	if(previousIndex() == -1) {
	    throw new IllegalStateException("Iterator in the begin position.");
	}
	list.set(position, e);
    }

    @Override
    public void add(T e) {
	list.add(++position, e);
	size++;
    }

    protected T get(int index) {
	return list.get(index);
    }

}
