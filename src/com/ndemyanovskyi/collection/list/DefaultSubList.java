/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.collection.list;

import com.ndemyanovskyi.util.number.Numbers.Integers;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


public class DefaultSubList<E> implements DefaultList<E> {
    
    private final int offset;
    private int size;
    private final List<E> base;
    
    public DefaultSubList(List<E> base, int fromInclusive, int toExclusive) {
	this.base = Objects.requireNonNull(base);
	this.offset = Integers.requireInBounds(fromInclusive, base);
	Integers.requireInBounds(toExclusive, base);
	
	size = toExclusive - fromInclusive;
    }

    @Override
    public int size() {
	return size;
    }

    @Override
    public boolean add(E e) {
	int index = size + offset;
	base.add(index, e);
	if(Objects.equals(base.get(index), e)) {
	    size++;
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public boolean remove(Object o) {
	int index = indexOf(o);
	if(index != -1) {
	    remove(index);
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
	return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
	return base.addAll(index + offset, c);
    }

    @Override
    public E get(int index) {
	return base.get(index + offset);
    }

    @Override
    public E set(int index, E element) {
	return base.set(index + offset, element);
    }

    @Override
    public void add(int index, E element) {
	index += offset;
	base.add(index, element);
	if(Objects.equals(base.get(index), element)) {
	    size++;
	}
    }

    @Override
    public E remove(int index) {
	E removed = base.remove(index + offset);
	size--;
	return removed;
    }

}
