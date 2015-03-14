/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.map.unmodifiable;

import com.ndemyanovskyi.util.Unmodifiable;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;


public class UnmodifiableMap<K, V> extends AbstractUnmodifiableMap<K, V> implements Map<K, V>, Unmodifiable {

    private Set<Entry<K, V>> entrySet;
    private final Map<K, V> base;
    
    public UnmodifiableMap(Map<K, V> map) {
	base = Objects.requireNonNull(map);
    }

    protected Map<K, V> base() {
	return base;
    }

    @Override
    public final Set<Entry<K, V>> entrySet() {
	return entrySet != null ? entrySet : 
		(entrySet = Unmodifiable.set(base.entrySet()));
    }

    @Override
    public int size() {
	return base.size();
    }

    @Override
    public boolean isEmpty() {
	return base.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
	return base.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
	return base.containsValue(value);
    }

    @Override
    public V get(Object key) {
	return base.get(key);
    }

    @Override
    public boolean equals(Object o) {
	return base.equals(o);
    }

    @Override
    public int hashCode() {
	return base.hashCode();
    }

    @Override
    public V getOrDefault(Object key, V defaultValue) {
	return base.getOrDefault(key, defaultValue);
    }

    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
	base.forEach(action);
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
	return base.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
	return base.computeIfPresent(key, remappingFunction);
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
	return base.compute(key, remappingFunction);
    }

}
