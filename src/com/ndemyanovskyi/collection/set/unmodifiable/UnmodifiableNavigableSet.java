/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.collection.set.unmodifiable;

import com.ndemyanovskyi.util.Unmodifiable;
import java.util.Iterator;
import java.util.NavigableSet;


public class UnmodifiableNavigableSet<E> extends UnmodifiableSortedSet<E> implements NavigableSet<E> {

    public UnmodifiableNavigableSet(NavigableSet<E> base) {
	super(base);
    }

    @Override
    protected NavigableSet<E> base() {
	return (NavigableSet<E>) super.base();
    }

    @Override
    public E lower(E e) {
	return base().lower(e);
    }

    @Override
    public E floor(E e) {
	return base().floor(e);
    }

    @Override
    public E ceiling(E e) {
	return base().ceiling(e);
    }

    @Override
    public E higher(E e) {
	return base().higher(e);
    }

    @Override
    public E pollFirst() {
	return base().pollFirst();
    }

    @Override
    public E pollLast() {
	return base().pollLast();
    }

    @Override
    public NavigableSet<E> descendingSet() {
	return Unmodifiable.navigableSet(base().descendingSet());
    }

    @Override
    public Iterator<E> descendingIterator() {
	return Unmodifiable.iterator(base().descendingIterator());
    }

    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
	return Unmodifiable.navigableSet(base().subSet(fromElement, fromInclusive, toElement, toInclusive));
    }

    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
	return Unmodifiable.navigableSet(base().headSet(toElement, inclusive));
    }

    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
	return Unmodifiable.navigableSet(base().tailSet(fromElement, inclusive));
    }

}
