/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.map;

import java.util.Map;


public class DefaultEntry<K, V> implements Map.Entry<K, V> {
    
    private V value; 
    private final K key;
    
    public DefaultEntry(K key, V value) {
	this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
	return key;
    }

    @Override
    public V getValue() {
	return value;
    }

    @Override
    public V setValue(V value) {
	V old = this.value;
	this.value = value;
	return old;
    }

}
