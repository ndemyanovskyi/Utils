/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.collection.list;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class IndexList<E> extends AbstractList<E> {
    
    private final List<E> base;
    private final Adapter<E> adapter;
    
    public IndexList(List<E> base, Adapter<E> adapter) {
	this.base = Objects.requireNonNull(base, "base");
	this.adapter = Objects.requireNonNull(adapter, "adapter");
    }
    
    public IndexList(List<E> base, List<Integer> indexes) {
	this.base = Objects.requireNonNull(base, "base");
	this.adapter = () -> indexes;
    }
    
    public IndexList(List<E> base) {
	this(base, new ArrayList<>());
    }

    @Override
    public E get(int index) {
	return base.get(adapter.getIndexes().get(index));
    }

    @Override
    public E set(int index, E element) {
	return base.set(adapter.getIndexes().get(index), element);
    }

    public Adapter<E> getAdapter() {
	return adapter;
    }

    @Override
    public boolean add(E e) {
	return adapter.onAdd(base, size(), e);
    }

    @Override
    public void add(int index, E e) {
	adapter.onAdd(base, index, e);
    }

    @Override
    public int size() {
	return adapter.getIndexes().size();
    }
    
    public interface Adapter<E> {
	
	public default boolean onAdd(List<E> base, int index, E value) {
	    int pos = getIndexes().stream().
		    max(Integer::compare).orElse(base.size() - 1) + 1;
	    
	    getIndexes().add(pos);
	    if(pos < base.size()) {
		base.add(pos, value);
		return base.contains(value);
	    } else {
		return base.add(value);
	    }
	}
	
	public List<Integer> getIndexes();
	
    }

}
