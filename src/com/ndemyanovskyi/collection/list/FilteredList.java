/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.collection.list;

import com.ndemyanovskyi.collection.FilteredCollection;
import com.ndemyanovskyi.listiterator.FilteredListIterator;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.function.Predicate;


public class FilteredList<E> extends FilteredCollection<E> implements DefaultList<E> {

    public FilteredList(List<E> base, Predicate<? super E> predicate) {
	super(base, predicate);
    }

    @Override
    protected List<E> base() {
	return (List<E>) super.base();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
	boolean modified = false;
	for(E e : c) {
	    if(super.add(e)) modified = true;
	}
	return modified;
    }

    @Override
    public E get(int index) {
	int position = 0;
	if(base() instanceof RandomAccess) {
	    for(int i = 0; i < base().size(); i++) {
		E e = base().get(i);
		if(predicate().test(e) 
			&& ++position == index) {
		    return e;
		}
	    }
	} else {
	    for(Iterator<E> it = base().iterator(); it.hasNext();) {
		E e = it.next();
		if(predicate().test(e) 
			&& ++position == index) {
		    return e;
		}
	    }
	}
	
	throw new IndexOutOfBoundsException(
		"Index = " + index + "; Bounds = [0; " + position + "]");
    }

    @Override
    public E set(int index, E element) {
	throw new UnsupportedOperationException("set");
    }

    @Override
    public void add(int index, E element) {
	throw new UnsupportedOperationException("add");
    }

    @Override
    public E remove(int index) {
	int position = 0;
	if(base() instanceof RandomAccess) {
	    for(int i = 0; i < base().size(); i++) {
		E e = base().get(i);
		if(predicate().test(e)) position++;
		if(position == index) return base().remove(i);
	    }
	} else {
	    for(Iterator<E> it = base().iterator(); it.hasNext();) {
		E e = it.next();
		if(predicate().test(e)) position++;
		if(position == index) {
		    it.remove();
		    return e;
		}
	    }
	}
	
	throw new IndexOutOfBoundsException(
		"Index = " + index + "; Bounds = [0; " + position + "]");
    }

    @Override
    public ListIterator<E> listIterator() {
	return new FilteredListIterator<>(base().listIterator(), predicate());
    }

    @Override
    public ListIterator<E> listIterator(int index) {
	ListIterator<E> it = new FilteredListIterator<>(base().listIterator(), predicate());
	while(it.hasNext()) {
	    it.next();
	    if(it.previousIndex() == index) {
		return it;
	    }
	}
	
	throw new IndexOutOfBoundsException(
		"Index = " + index + "; Bounds = [0; " + it.nextIndex() + "]");
    }

    @Override
    public FilteredList<E> subList(int fromIndex, int toIndex) {
	throw new UnsupportedOperationException("subList");
    }

}
