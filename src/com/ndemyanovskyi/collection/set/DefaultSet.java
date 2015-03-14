/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection.set;

import com.ndemyanovskyi.collection.DefaultCollection;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public interface DefaultSet<E> extends DefaultCollection<E>, Set<E> {

    @Override
    public default boolean retainAll(Collection<?> c) {
	return DefaultCollection.super.retainAll(c);
    }

    @Override
    public default boolean contains(Object o) {
	return DefaultCollection.super.contains(o);
    }

    @Override
    public default boolean containsAll(Collection<?> c) {
	return DefaultCollection.super.containsAll(c);
    }

    @Override
    default public boolean add(E e) {
	return DefaultCollection.super.add(e);
    }

    @Override
    public default boolean isEmpty() {
	return DefaultCollection.super.isEmpty();
    }

    @Override
    public default boolean removeAll(Collection<?> c) {
	return DefaultCollection.super.removeAll(c);
    }

    @Override
    public default boolean remove(Object o) {
	return DefaultCollection.super.remove(o);
    }

    @Override
    public default Object[] toArray() {
	return DefaultCollection.super.toArray();
    }

    @Override
    @SuppressWarnings("unchecked")
    public default <T> T[] toArray(T[] a) {
	return DefaultCollection.super.toArray(a);
    }

    @Override
    public default boolean addAll(Collection<? extends E> c) {
	return DefaultCollection.super.addAll(c);
    }

    @Override
    public default void clear() {
	DefaultCollection.super.clear();
    }
    
    public static String toString(Set<?> set) {
	return DefaultCollection.toString(set);
    }
    
    public static int hashCode(Set<?> set) {
	return DefaultCollection.hashCode(set);
    }
    
    public static boolean equals(Set<?> set, Object object) {
        Objects.requireNonNull(set, "set");
	return object instanceof Set 
                && DefaultCollection.equals(set, object);
    }
	

}
