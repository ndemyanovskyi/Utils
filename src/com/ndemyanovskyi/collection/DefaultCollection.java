/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public interface DefaultCollection<E> extends Collection<E> {

    @Override
    public default boolean isEmpty() {
	return size() == 0;
    }

    @Override
    public default boolean contains(Object o) {
	Iterator<E> it = iterator();
	if (o == null) {
	    while (it.hasNext()) {
		if (it.next() == null) {
		    return true;
		}
	    }
	} else {
	    while (it.hasNext()) {
		if (o.equals(it.next())) {
		    return true;
		}
	    }
	}
	return false;
    }

    @Override
    public default Object[] toArray() {
	Object[] array = new Object[size()];
	int count = 0;
	for (Iterator<E> it = iterator(); it.hasNext(); ) {
	    array[count++] = it.next();
	}
	return array;
    }

    @Override
    @SuppressWarnings("unchecked")
    public default <T> T[] toArray(T[] a) {
	int size = size();
	T[] array = a.length >= size
		? a : (T[]) Array.newInstance(a.getClass().getComponentType(), size);
	int count = 0;
	for (Iterator<E> it = iterator(); it.hasNext(); ) {
	    array[count++] = (T) it.next();
	}
	return array;
    }

    @Override
    public default boolean remove(Object o) {
	Iterator<E> it = iterator();
	if (o == null) {
	    while (it.hasNext()) {
		if (it.next() == null) {
		    it.remove();
		    return true;
		}
	    }
	} else {
	    while (it.hasNext()) {
		if (o.equals(it.next())) {
		    it.remove();
		    return true;
		}
	    }
	}
	return false;
    }

    @Override
    public default boolean containsAll(Collection<?> c) {
	for (Object e : c) {
	    if (!contains(e)) {
		return false;
	    }
	}
	return true;
    }

    @Override
    public default boolean addAll(Collection<? extends E> c) {
	boolean modified = false;
	for (E e : c) {
	    if (add(e)) {
		modified = true;
	    }
	}
	return modified;
    }

    @Override
    public default boolean removeAll(Collection<?> c) {
	Objects.requireNonNull(c);
	boolean modified = false;
	Iterator<?> it = iterator();
	while (it.hasNext()) {
	    if (c.contains(it.next())) {
		it.remove();
		modified = true;
	    }
	}
	return modified;
    }

    @Override
    public default boolean retainAll(Collection<?> c) {
	Objects.requireNonNull(c);
	boolean modified = false;
	Iterator<E> it = iterator();
	while (it.hasNext()) {
	    if (!c.contains(it.next())) {
		it.remove();
		modified = true;
	    }
	}
	return modified;
    }

    @Override
    public default void clear() {
	Iterator<E> it = iterator();
	while (it.hasNext()) {
	    it.next();
	    it.remove();
	}
    }
    
    public static String toString(Collection<?> collection) {
	StringBuilder b = new StringBuilder("[");
	for(Iterator<? extends Object> it = collection.iterator(); it.hasNext();) {
	    b.append(it.next());
	    if(it.hasNext()) b.append(", ");
	}
	b.append("]");
	return b.toString();
    }
    
    public static int hashCode(Collection<?> collection) {
	int hash = 1;
	for(Object o : collection) {
	    hash += 31 * hash + Objects.hashCode(o);
	}
	return hash;
    }
    
    public static boolean equals(Collection<?> collection, Object object) {
	if(collection == object) return true;
        
        Objects.requireNonNull(collection, "c");
        
	if(object == null) return false;
	if(!(object instanceof Collection)) return false;
	
	Collection<?> b = (Collection<?>) object;
	
	if(collection.size() != b.size()) return false;
	
	Iterator<?> aIt = collection.iterator();
	Iterator<?> bIt = b.iterator();
	
	while(aIt.hasNext() && bIt.hasNext()) {
	    if(!Objects.equals(aIt.next(), bIt.next())) {
		return false;
	    }
	}
	return !aIt.hasNext() && !bIt.hasNext();
    }
    
}
