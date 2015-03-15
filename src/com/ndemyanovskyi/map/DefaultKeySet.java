/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.map;

import com.ndemyanovskyi.collection.set.DefaultSet;
import com.ndemyanovskyi.iterator.Iterators;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class DefaultKeySet<T> implements DefaultSet<T> {
    
    private final Set<Map.Entry<T, ?>> base;
    
    public DefaultKeySet(Map<T, ?> map) {
	this.base = (Set) map.entrySet();
    }
    
    @Override
    public int size() {
	return base.size();
    }

    @Override
    public Iterator<T> iterator() {
	return Iterators.converted(base.iterator(), e -> e.getKey());
    }

    @Override
    public boolean add(T e) {
        throw new UnsupportedOperationException("add");
    }

}
