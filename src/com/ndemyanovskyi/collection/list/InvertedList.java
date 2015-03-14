/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection.list;

import com.ndemyanovskyi.listiterator.ListIterators;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.UnaryOperator;

/**
 *
 * @author Назарій
 */
public class InvertedList<E> extends AbstractList<E> {
    
    private final List<E> base;

    public InvertedList(List<E> base) {
        this.base = Objects.requireNonNull(base, "base");
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
    public boolean contains(Object o) {
        return base.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return ListIterators.of(this);
    }

    @Override
    public boolean add(E e) {
        base.add(0, e);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return base.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return base.containsAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return base.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return base.retainAll(c);
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        base.replaceAll(operator);
    }

    @Override
    public void clear() {
        base.clear();
    }

    @Override
    public boolean equals(Object o) {
        return DefaultList.equals(this, o);
    }

    @Override
    public int hashCode() {
        return DefaultList.hashCode(this);
    }
    
    private int invert(int index) {
        int size = size();
        if(index >= size || index < 0) {
            throw new IndexOutOfBoundsException(
                    "index = " + index + "; size = " + size);
        }
        return size - index - 1;
    }

    @Override
    public E get(int index) {
        return base.get(invert(index));
    }

    @Override
    public E set(int index, E element) {
        return base.set(invert(index), element);
    }

    @Override
    public void add(int index, E element) {
        base.add(invert(index), element);
    }

    @Override
    public E remove(int index) {
        return base.remove(invert(index));
    }

    @Override
    public int indexOf(Object o) {
        int index = base.indexOf(o);
        return index != -1 ? invert(index) : -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        int index = base.lastIndexOf(o);
        return index != -1 ? invert(index) : -1;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return ListIterators.of(index, this);
    }

    @Override
    public String toString() {
        return DefaultList.toString(this);
    }

    @Override
    public List<E> subList(int fromInclusive, int toExclusive) {
        int size = size();
        if(fromInclusive < 0 || fromInclusive >= size) {
            throw new IndexOutOfBoundsException(
                    "fromInclusive = " + fromInclusive + "; size = " + size);
        }
        if(toExclusive < 0 || toExclusive > size) {
            throw new IndexOutOfBoundsException(
                    "toExclusive = " + toExclusive + "; size = " + size);
        }
        if(fromInclusive > toExclusive) {
            throw new IndexOutOfBoundsException("fromInclusive > toExclusive");
        }
        
        return new InvertedList<>(base.subList(
                invert(toExclusive - 1), invert(fromInclusive) + 1));
    }
    
    public static void main(String[] args) {
        System.out.println(new InvertedList<>(Arrays.asList(1,2,7,4)).set(0, Integer.SIZE));
    }

    @Override
    public Spliterator<E> spliterator() {
        return base.spliterator();
    }
    
}
