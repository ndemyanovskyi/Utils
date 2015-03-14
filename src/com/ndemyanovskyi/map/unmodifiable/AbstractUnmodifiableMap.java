/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.map.unmodifiable;

import com.ndemyanovskyi.util.Unmodifiable;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;


public abstract class AbstractUnmodifiableMap<K, V> extends AbstractMap<K, V> implements Map<K, V>, Unmodifiable {
    
    @Override
    public final V put(K key, V value) {
	throw new UnsupportedOperationException("put");
    }

    @Override
    public final V putIfAbsent(K key, V value) {
	throw new UnsupportedOperationException("putIfAbsent");
    }

    @Override
    public final Set<K> keySet() {
	return super.keySet();
    }

    @Override
    public final Collection<V> values() {
	return super.values();
    }

    @Override
    public final void putAll(Map<? extends K, ? extends V> m) {
	throw new UnsupportedOperationException("putAll");
    }

    @Override
    public final void clear() {
	throw new UnsupportedOperationException("clear");
    }

    @Override
    public final V replace(K key, V value) {
	throw new UnsupportedOperationException("replace");
    }

    @Override
    public final V remove(Object key) {
	throw new UnsupportedOperationException("remove");
    }

    @Override
    public final void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
	throw new UnsupportedOperationException("replaceAll");
    }

    @Override
    public final boolean remove(Object key, Object value) {
	throw new UnsupportedOperationException("remove");
    }

    @Override
    public final boolean replace(K key, V oldValue, V newValue) {
	throw new UnsupportedOperationException("replace");
    }

    @Override
    public final V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
	throw new UnsupportedOperationException("merge");
    }

}
