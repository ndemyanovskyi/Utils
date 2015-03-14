/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection.list;

import com.ndemyanovskyi.collection.DefaultCollection;
import com.ndemyanovskyi.listiterator.ListIterators;
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
        ListIterator<E> it = listIterator();
        while (it.hasNext()) {
            if (Objects.equals(it.next(), o)) {
                return it.previousIndex();
            }
        }
        return -1;
    }

    @Override
    default public int lastIndexOf(Object o) {
        ListIterator<E> it = listIterator(size());
        while (it.hasPrevious()) {
            if (Objects.equals(it.previous(), o)) {
                return it.nextIndex();
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
