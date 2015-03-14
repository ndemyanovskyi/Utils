/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.map.unmodifiable;

import com.ndemyanovskyi.util.Unmodifiable;
import com.ndemyanovskyi.map.Pool;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 *
 * @author Назарій
 */
public class UnmodifiablePoolWrapper<K, V> implements Pool<K, V>, Unmodifiable.Wrapper<Pool<K, V>> {
    
    private final Pool<K, V> modifiable;
    private Pool<K, V> unmodifiable;

    public UnmodifiablePoolWrapper(Pool<K, V> modifiable) {
	this.modifiable = Objects.requireNonNull(modifiable, "modifiable");
    }

    @Override
    public Pool<K, V> modifiable() {
	return modifiable;
    }

    @Override
    public Pool<K, V> unmodifiable() {
	return unmodifiable != null ? unmodifiable 
		: (unmodifiable = Unmodifiable.pool(modifiable));
    }

    //<editor-fold defaultstate="collapsed" desc="Delegats">
    @Override
    public Function<K, V> creator() {
        return modifiable.creator();
    }
    
    @Override
    public int size() {
        return modifiable.size();
    }
    
    @Override
    public boolean isEmpty() {
        return modifiable.isEmpty();
    }
    
    @Override
    public boolean containsKey(Object key) {
        return modifiable.containsKey(key);
    }
    
    @Override
    public boolean containsValue(Object value) {
        return modifiable.containsValue(value);
    }
    
    @Override
    public V get(Object key) {
        return modifiable.get(key);
    }
    
    @Override
    public V put(K key, V value) {
        return modifiable.put(key, value);
    }
    
    @Override
    public V remove(Object key) {
        return modifiable.remove(key);
    }
    
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        modifiable.putAll(m);
    }
    
    @Override
    public void clear() {
        modifiable.clear();
    }
    
    @Override
    public Set<K> keySet() {
        return modifiable.keySet();
    }
    
    @Override
    public Collection<V> values() {
        return modifiable.values();
    }
    
    @Override
    public Set<Entry<K, V>> entrySet() {
        return modifiable.entrySet();
    }
    
    @Override
    public V getOrDefault(Object key, V defaultValue) {
        return modifiable.getOrDefault(key, defaultValue);
    }
    
    @Override
    public void forEach(BiConsumer<? super K, ? super V> action) {
        modifiable.forEach(action);
    }
    
    @Override
    public void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        modifiable.replaceAll(function);
    }
    
    @Override
    public V putIfAbsent(K key, V value) {
        return modifiable.putIfAbsent(key, value);
    }
    
    @Override
    public boolean remove(Object key, Object value) {
        return modifiable.remove(key, value);
    }
    
    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return modifiable.replace(key, oldValue, newValue);
    }
    
    @Override
    public V replace(K key, V value) {
        return modifiable.replace(key, value);
    }
    
    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return modifiable.computeIfAbsent(key, mappingFunction);
    }
    
    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return modifiable.computeIfPresent(key, remappingFunction);
    }
    
    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return modifiable.compute(key, remappingFunction);
    }
    
    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        return modifiable.merge(key, value, remappingFunction);
    }
    
    @Override
    public int hashCode() {
        return modifiable.hashCode();
    }
    
    @Override
    public boolean equals(Object obj) {
        return modifiable.equals(obj);
    }
    
    @Override
    public String toString() {
        return modifiable.toString();
    }
    //</editor-fold>
    
}
