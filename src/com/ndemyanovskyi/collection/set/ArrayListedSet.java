/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection.set;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 *
 * @author Назарій
 */
public class ArrayListedSet<T> extends ArrayList<T> implements ListedSet<T> {
    
    private static final long serialVersionUID = 1912874891419287L;

    public ArrayListedSet(int initialCapacity) {
        super(initialCapacity);
    }

    public ArrayListedSet() {
    }

    public ArrayListedSet(Collection<? extends T> c) {
        super(c);
    }

    @Override
    public boolean add(T e) {
        return !contains(e) && super.add(e);
    }

    @Override
    public void add(int index, T element) {
        if(contains(element)) {
            throw new IllegalArgumentException(
                    "Element(" + element + ") already contains in collection.");
        }
        super.add(index, element);
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        Objects.requireNonNull(operator);
        final int expectedModCount = modCount;
        final int size = size();
        for (int i=0; modCount == expectedModCount && i < size; i++) {
            set(i, operator.apply(get(i)));
        }
        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
        modCount++;
    }

    @Override
    public T set(int index, T element) {
        T old = get(index);
        if(!Objects.equals(old, element) && contains(element)) {
            throw new IllegalArgumentException(
                    "Element(" + element + ") already contains in collection.");
        }
        return super.set(index, element);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        boolean modified = false;
        for(T e : c) {
            if(!contains(e)) {
                add(index++, e);
                modified = true;
            }
        }
        return modified;
    }
    
}
