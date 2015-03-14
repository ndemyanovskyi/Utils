/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.map;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;


public abstract class SetWrapperMap<K, V> implements DefaultMap<K, V> {
    
    private Set<K> keySet;
    private Collection<V> values;
    private final Set<Entry<K, V>> entrySet;
    
    public SetWrapperMap(Set<Entry<K, V>> entrySet) {
	this.entrySet = Objects.requireNonNull(entrySet);
    }    

    @Override
    public V put(K key, V value) {
	for(Entry<K, V> e : entrySet()) {
	    if(e.getKey().equals(key)) {
		return e.setValue(value);
	    }
	}
	
	entrySet().add(getEntry(key, value));
	return null;
    }
    
    protected abstract Entry<K, V> getEntry(K key, V value);

    @Override
    public Set<K> keySet() {
	return keySet != null ? keySet : 
		(keySet = new DefaultKeySet<>(this));
    }

    @Override
    public Collection<V> values() {
	return values != null ? values : 
		(values = new DefaultValuesCollection<>(this));
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
	return entrySet;
    }

}
