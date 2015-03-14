/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.map;

import com.ndemyanovskyi.collection.DefaultCollection;
import com.ndemyanovskyi.iterator.Iterators;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class DefaultValuesCollection<T> implements DefaultCollection<T> {
    
    private final Set<Map.Entry<?, T>> base;
    
    public DefaultValuesCollection(Map<?, T> map) {
	this.base = (Set) map.entrySet();
    }

    @Override
    public int size() {
	return base.size();
    }

    @Override
    public Iterator<T> iterator() {
	return Iterators.converted(base.iterator(), e -> e.getValue());
    }
    
}
