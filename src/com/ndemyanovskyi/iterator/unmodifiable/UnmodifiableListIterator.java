/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.iterator.unmodifiable;

import java.util.ListIterator;


public class UnmodifiableListIterator<T> extends UnmodifiableIterator<T> implements ListIterator<T> {

    public UnmodifiableListIterator(ListIterator<T> base) {
	super(base);
    }

    @Override
    protected ListIterator<T> base() {
	return (ListIterator<T>) super.base();
    }

    @Override
    public boolean hasPrevious() {
	return base().hasPrevious();
    }

    @Override
    public T previous() {
	return base().previous();
    }

    @Override
    public int nextIndex() {
	return base().nextIndex();
    }

    @Override
    public int previousIndex() {
	return base().previousIndex();
    }

    @Override
    public final void set(T e) {
	throw new UnsupportedOperationException("set");
    }

    @Override
    public final void add(T e) {
	throw new UnsupportedOperationException("add");
    }

}
