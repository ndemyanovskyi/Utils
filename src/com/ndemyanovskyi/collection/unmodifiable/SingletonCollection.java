/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection.unmodifiable;

import com.ndemyanovskyi.iterator.Iterators;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

/**
 *
 * @author Назарій
 */
public class SingletonCollection<E> extends AbstractUnmodifiableCollection<E> {
    
    private final E element;

    public SingletonCollection(E element) {
        this.element = element;
    }

    @Override
    public Iterator<E> iterator() {
        return Iterators.singleton(element);
    }

    @Override
    public boolean contains(Object o) {
        return Objects.equals(element, o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        int size = c.size();
        switch(size) {
            case 1: return Objects.equals(element, c.iterator().next());
            case 0: return true;
            default: return false;
        }
    }

    @Override
    public String toString() {
        return "[" + element + "]";
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void forEach(Consumer<? super E> action) {
        action.accept(element);
    }

    @Override
    public int size() {
        return 1;
    }
    
}
