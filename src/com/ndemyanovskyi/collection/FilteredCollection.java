/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.collection;

import com.ndemyanovskyi.iterator.Iterators;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;


public class FilteredCollection<E> extends AbstractCollection<E> {
    
    private final Predicate<? super E> predicate;
    private final Collection<E> base;

    public FilteredCollection(Collection<E> base, Predicate<? super E> predicate) {
	this.base = Objects.requireNonNull(base, "base");
	this.predicate = Objects.requireNonNull(predicate, "predicate");
    }

    @Override
    public boolean add(E e) {
	return predicate().test(e) && base().add(e);
    }
    
    protected Collection<E> base() {
	return base;
    }

    public Predicate<? super E> predicate() {
	return predicate;
    }
    
    @Override
    public Iterator<E> iterator() {
	return Iterators.filtered(base.iterator(), predicate);
    }

    @Override
    public int size() {
	if(base.isEmpty()) return 0;
	
	int size = 0;
	for(E e : this) { 
            size++;
        }
	return size;
    }

}
