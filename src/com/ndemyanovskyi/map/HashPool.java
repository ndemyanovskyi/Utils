/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.map;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;


public class HashPool<K, V> extends HashMap<K, V> implements Pool<K, V> {
    
    private final Function<Object, V> uncheckedCreator;
    private final Function<K, V> creator;
    private final Class<K> keyType;

    public HashPool(Function<Object, V> creator) {
	this.uncheckedCreator = Objects.requireNonNull(creator);
        this.creator = key -> uncheckedCreator.apply(key);
        this.keyType = null;
    }

    public HashPool(Class<K> keyType, Function<K, V> creator) {
        this.keyType = Objects.requireNonNull(keyType, "keyType");
        this.creator = Objects.requireNonNull(creator, "creator");
	this.uncheckedCreator = null;
    }

    @Override
    public Function<K, V> creator() {
	return creator;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V get(Object key) {
	V value = super.get(key);
	if(value == null) {
            value = keyType != null && keyType.isInstance(key) 
                    ? creator.apply((K) key)
                    : uncheckedCreator.apply(key);
            if(value != null) put((K) key, value);
	}
	return value;
    }

}
