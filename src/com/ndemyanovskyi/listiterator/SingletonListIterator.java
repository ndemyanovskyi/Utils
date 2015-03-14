/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.listiterator;

import java.util.ListIterator;
import java.util.NoSuchElementException;


class SingletonListIterator<E> implements ListIterator<E> {
    
    private int position = -1;
    private E element;

    public SingletonListIterator(E element) {
	this.element = element;
    }

    @Override
    public boolean hasNext() {
	return position < 0;
    }

    @Override
    public E next() {
	if(!hasNext()) {
	    throw new NoSuchElementException();
	}
	position++;
	return element;
    }

    @Override
    public boolean hasPrevious() {
	return position > -1;
    }

    @Override
    public E previous() {
	if(!hasNext()) {
	    throw new NoSuchElementException();
	}
	position--;
	return element;
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
	throw new UnsupportedOperationException("remove");
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
