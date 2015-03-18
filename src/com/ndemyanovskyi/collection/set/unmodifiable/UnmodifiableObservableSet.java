/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection.set.unmodifiable;

import com.ndemyanovskyi.util.Unmodifiable;
import java.util.Collection;
import java.util.Iterator;
import javafx.beans.InvalidationListener;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;

/**
 *
 * @author Назарій
 */
public class UnmodifiableObservableSet<E> extends AbstractUnmodifiableObservableSet<E> {
    
    private final ObservableSet<E> base;

    public UnmodifiableObservableSet(ObservableSet<E> set) {
        this.base = set;
    }

    protected ObservableSet<E> base() {
        return base;
    }

    @Override
    public Iterator<E> iterator() {
        return Unmodifiable.iterator(base.iterator());
    }

    @Override
    public void addListener(SetChangeListener<? super E> listener) {
        base.addListener(listener);
    }

    @Override
    public void removeListener(SetChangeListener<? super E> listener) {
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
