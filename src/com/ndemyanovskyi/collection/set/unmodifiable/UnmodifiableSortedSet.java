/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.collection.set.unmodifiable;

import com.ndemyanovskyi.util.Unmodifiable;
import java.util.Comparator;
import java.util.SortedSet;


public class UnmodifiableSortedSet<E> extends UnmodifiableSet<E> implements SortedSet<E> {

    public UnmodifiableSortedSet(SortedSet<E> base) {
	super(base);
    }

    @Override
    protected SortedSet<E> base() {
	return (SortedSet<E>) super.base();
    }

    @Override
    public Comparator<? super E> comparator() {
	return base().comparator();
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
	return Unmodifiable.sortedSet(base().subSet(fromElement, toElement));
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
	return Unmodifiable.sortedSet(base().headSet(toElement));
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
	return Unmodifiable.sortedSet(base().tailSet(fromElement));
    }

    @Override
    public E first() {
	return base().first();
    }

    @Override
    public E last() {
	return base().last();
    }

}
