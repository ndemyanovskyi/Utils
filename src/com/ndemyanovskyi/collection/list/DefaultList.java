/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection.list;

import com.ndemyanovskyi.collection.DefaultCollection;
import com.ndemyanovskyi.listiterator.ListIterators;
import com.ndemyanovskyi.util.number.Numbers.Integers;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 *
 * @author Назарій
 */
public interface DefaultList<E> extends DefaultCollection<E>, List<E> {

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
        add(size(), e);
        return true;
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
    public default E set(int index, E element) {
        throw new UnsupportedOperationException("set");
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
        return addAll(size(), c);
    }

    @Override
    default public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public E remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    default public int indexOf(Object o) {
        return indexOf(o, 0);
    }

    default public int indexOf(Object o, int fromInclusive) {
        return indexOf(o, fromInclusive, size());
    }

    default public int indexOf(Object o, int fromInclusive, int toExclusive) {
        Integers.requireInRange(fromInclusive, this, "fromInclusive");
        Integers.requireInRangeExclusive(toExclusive, this, "toExclusive");
        
        if(isEmpty()) return -1;
        
        ListIterator<E> it = listIterator(fromInclusive);
        while(it.hasNext()) {
            E next = it.next();
            int index = it.previousIndex();
            if(index >= toExclusive) break;
            if(Objects.equals(next, o)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    default public int lastIndexOf(Object o) {
        return lastIndexOf(o, size());
    }

    default public int lastIndexOf(Object o, int toExclusive) {
        return lastIndexOf(o, 0, toExclusive);
    }

    default public int lastIndexOf(Object o, int fromInclusive, int toExclusive) {
        Integers.requireInRange(fromInclusive, this, "fromInclusive");
        Integers.requireInRangeExclusive(toExclusive, this, "toExclusive");
        
        if(isEmpty()) return -1;
        
        ListIterator<E> it = listIterator(toExclusive);
        while(it.hasPrevious()) {
            E previous = it.previous();
            int index = it.previousIndex();
            if(index < fromInclusive) break;
            if(Objects.equals(previous, o)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    default public void clear() {
	DefaultCollection.super.clear();
    }

    @Override
    default public boolean addAll(int index, Collection<? extends E> c) {
	return DefaultCollection.super.addAll(c);
    }

    @Override
    default public Iterator<E> iterator() {
        return listIterator();
    }

    @Override
    default public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public default ListIterator<E> listIterator(int index) {
	return ListIterators.of(index, this);
    }

    @Override
    public default List<E> subList(int fromIndex, int toIndex) {
	return new DefaultSubList<>(this, fromIndex, toIndex);
    }
    
    public static int hashCode(List<?> a) {
        int hash = 3;
        for(Object e : a) {
            hash = hash * 53 + Objects.hashCode(e);
        }
        return hash;
    }
    
    public static String toString(List<?> list) {
        switch(list.size()) {
            case 0: return "[]";
            case 1: return "[" + list.get(0) + "]";
            default: 
                StringBuilder b = new StringBuilder();
                b.append("[");
                for(Iterator<? extends Object> it = list.iterator(); it.hasNext();) {
                    b.append(it.next());
                    if(it.hasNext()) b.append(", ");
                }
                b.append("]");
                return b.toString();
        }
    }
    
    public static boolean equals(List<?> a, Object o) {
        if(a == o) return true;
        if(o == null || !(o instanceof List)) return false;
        
        List<?> b = (List<?>) o;
        if(a.size() != b.size()) return false;
        
        Iterator<?> aIt = a.iterator();
        Iterator<?> bIt = b.iterator();
        while(aIt.hasNext() && bIt.hasNext()) {
            if(!aIt.next().equals(bIt.next())) {
                return false;
            }
        }
        return !aIt.hasNext() && !bIt.hasNext();
    }

}
