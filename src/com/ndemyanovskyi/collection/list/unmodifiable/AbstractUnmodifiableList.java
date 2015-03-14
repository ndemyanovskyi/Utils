/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.collection.list.unmodifiable;

import com.ndemyanovskyi.util.Unmodifiable;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;


public abstract class AbstractUnmodifiableList<E> extends AbstractList<E> implements Unmodifiable {

    @Override
    public final boolean add(E e) {
	throw new UnsupportedOperationException("add");
    }

    @Override
    public final void add(int index, E element) {
	throw new UnsupportedOperationException("add");
    }

    @Override
    public final boolean addAll(Collection<? extends E> c) {
	throw new UnsupportedOperationException("addAll");
    }

    @Override
    public final boolean addAll(int index, Collection<? extends E> c) {
	throw new UnsupportedOperationException("addAll");
    }

    @Override
    public final boolean remove(Object o) {
	throw new UnsupportedOperationException("remove");
    }

    @Override
    public final E remove(int index) {
	throw new UnsupportedOperationException("remove");
    }

    @Override
    public final boolean removeAll(Collection<?> c) {
	throw new UnsupportedOperationException("removeAll");
    }

    @Override
    public final boolean removeIf(Predicate<? super E> filter) {
	throw new UnsupportedOperationException("removeIf");
    }

    @Override
    protected final void removeRange(int fromIndex, int toIndex) {
	throw new UnsupportedOperationException("removeRange");
    }

    @Override
    public final void replaceAll(UnaryOperator<E> operator) {
	throw new UnsupportedOperationException("replaceAll");
    }

    @Override
    public final boolean retainAll(Collection<?> c) {
	throw new UnsupportedOperationException("retainAll");
    }

    @Override
    public final void clear() {
	throw new UnsupportedOperationException("clear");
    }

    @Override
    public final E set(int index, E element) {
	throw new UnsupportedOperationException("set");
    }

    @Override
    public final void sort(Comparator<? super E> c) {
	throw new UnsupportedOperationException("sort");
    }

    @Override
    public final Iterator<E> iterator() {
	return listIterator();
    }

    @Override
    public final ListIterator<E> listIterator() {
	return listIterator(0);
    }

}
