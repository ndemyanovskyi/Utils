/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.map;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

public interface DefaultMap<K, V> extends Map<K, V> {

    @Override
    public default int size() {
	return entrySet().size();
    }

    @Override
    public default boolean isEmpty() {
	return size() == 0;
    }

    @Override
    public default boolean containsValue(Object value) {
	Iterator<Entry<K, V>> i = entrySet().iterator();
	if (value == null) {
	    while (i.hasNext()) {
		Entry<K, V> e = i.next();
		if (e.getValue() == null) {
		    return true;
		}
	    }
	} else {
	    while (i.hasNext()) {
		Entry<K, V> e = i.next();
		if (value.equals(e.getValue())) {
		    return true;
		}
	    }
	}
	return false;
    }

    @Override
    public default boolean containsKey(Object key) {
	Iterator<Map.Entry<K, V>> i = entrySet().iterator();
	if (key == null) {
	    while (i.hasNext()) {
		Entry<K, V> e = i.next();
		if (e.getKey() == null) {
		    return true;
		}
	    }
	} else {
	    while (i.hasNext()) {
		Entry<K, V> e = i.next();
		if (key.equals(e.getKey())) {
		    return true;
		}
	    }
	}
	return false;
    }

    @Override
    public default V get(Object key) {
	Iterator<Entry<K, V>> i = entrySet().iterator();
	if (key == null) {
	    while (i.hasNext()) {
		Entry<K, V> e = i.next();
		if (e.getKey() == null) {
		    return e.getValue();
		}
	    }
	} else {
	    while (i.hasNext()) {
		Entry<K, V> e = i.next();
		if (key.equals(e.getKey())) {
		    return e.getValue();
		}
	    }
	}
	return null;
    }

    @Override
    public default V put(K key, V value) {
	throw new UnsupportedOperationException();
    }

    @Override
    public default V remove(Object key) {
	Iterator<Entry<K, V>> i = entrySet().iterator();
	Entry<K, V> correctEntry = null;
	if (key == null) {
	    while (correctEntry == null && i.hasNext()) {
		Entry<K, V> e = i.next();
		if (e.getKey() == null) {
		    correctEntry = e;
		}
	    }
	} else {
	    while (correctEntry == null && i.hasNext()) {
		Entry<K, V> e = i.next();
		if (key.equals(e.getKey())) {
		    correctEntry = e;
		}
	    }
	}

	V oldValue = null;
	if (correctEntry != null) {
	    oldValue = correctEntry.getValue();
	    i.remove();
	}
	return oldValue;
    }

    @Override
    public default void putAll(Map<? extends K, ? extends V> m) {
	for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
	    put(e.getKey(), e.getValue());
	}
    }

    @Override
    public default void clear() {
	entrySet().clear();
    }
    
    public static boolean equals(Map a, Object o) {
	if(a == o) return true;
	if(o == null || a == null) return false;
	if (!(o instanceof Map)) return false;
	
	Map<?, ?> b = (Map<?, ?>) o;
	
	if (a.size() != b.size()) return false;

	try {
	    Iterator<Entry> i = a.entrySet().iterator();
	    while (i.hasNext()) {
		Entry e = i.next();
		Object key = e.getKey();
		Object value = e.getValue();
		if (value == null) {
		    if (!(b.get(key) == null && b.containsKey(key))) {
			return false;
		    }
		} else {
		    if (!value.equals(b.get(key))) {
			return false;
		    }
		}
	    }
	} catch (ClassCastException | NullPointerException ex) {
	    return false;
	}

	return true;
    }

    public static int hashCode(Map a) {
	int hash = 1;
	Iterator it = a.entrySet().iterator();
	while (it.hasNext()) {
	    hash += 31 * hash + Objects.hashCode(it.next());
	}
	return hash;
    }

    public static String toString(Map a) {
	Iterator<Entry> i = a.entrySet().iterator();
	
	if (!i.hasNext()) return "{}";

	StringBuilder sb = new StringBuilder();
	sb.append('{');
	for (;;) {
	    Entry e = i.next();
	    Object key = e.getKey();
	    Object value = e.getValue();
	    sb.append(key == a ? "(this Map)" : key);
	    sb.append('=');
	    sb.append(value == a ? "(this Map)" : value);
	    if (!i.hasNext()) {
		return sb.append('}').toString();
	    }
	    sb.append(',').append(' ');
	}
    }
    
}


