/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection.list.unmodifiable;

import java.util.Collection;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author Назарій
 */
public class UnmodifiableObservableList<E> extends AbstractUnmodifiableObservableList<E> {
    
    private final ObservableList<E> base;

    public UnmodifiableObservableList(ObservableList<E> list) {
        this.base = list;
    }

    protected ObservableList<E> base() {
        return base;
    }

    @Override
    public E get(int index) {
        return base.get(index);
    }

    @Override
    public void addListener(ListChangeListener<? super E> listener) {
        base.addListener(listener);
    }

    @Override
    public void removeListener(ListChangeListener<? super E> listener) {
        base.removeListener(listener);
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
    public Object[] toArray() {
        return base.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return base.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return base.containsAll(c);
    }

    @Override
    public void addListener(InvalidationListener listener) {
        base.addListener(listener);
    }

    @Override
    public void removeListener(InvalidationListener listener) {
        base.removeListener(listener);
    }
    
}
