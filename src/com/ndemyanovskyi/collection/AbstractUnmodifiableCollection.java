/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.collection;

import com.ndemyanovskyi.util.Unmodifiable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.function.Predicate;


public abstract class AbstractUnmodifiableCollection<E> extends AbstractCollection<E> implements Unmodifiable {

    @Override
    public final boolean add(E e) {
	throw new UnsupportedOperationException("add");
    }

    @Override
    public final boolean remove(Object o) {
	throw new UnsupportedOperationException("remove");
    }

    @Override
    public final void clear() {
	throw new UnsupportedOperationException("clear");
    }

    @Override
    public final boolean removeIf(Predicate<? super E> filter) {
	throw new UnsupportedOperationException("removeIf");
    }

    @Override
    public final boolean addAll(Collection<? extends E> c) {
	throw new UnsupportedOperationException("addAll");
    }

    @Override
    public final boolean retainAll(Collection<?> c) {
	throw new UnsupportedOperationException("retainAll");
    }

    @Override
    public final boolean removeAll(Collection<?> c) {
	throw new UnsupportedOperationException("removeAll");
    }
    
}
