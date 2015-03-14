/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection.set.unmodifiable;

import com.ndemyanovskyi.collection.unmodifiable.SingletonCollection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedSet;

/**
 *
 * @author Назарій
 */
public class SingletonSet<E> extends SingletonCollection<E> implements NavigableSet<E> {

    public SingletonSet(E element) {
        super(element);
    }

    @Override
    public E lower(E e) {
        throw new UnsupportedOperationException("lower");
    }

    @Override
    public E floor(E e) {
        throw new UnsupportedOperationException("floor");
    }

    @Override
    public E ceiling(E e) {
        throw new UnsupportedOperationException("ceiling");
    }

    @Override
    public E higher(E e) {
        throw new UnsupportedOperationException("higher");
    }

    @Override
    public E pollFirst() {
        throw new UnsupportedOperationException("pollFirst");
    }

    @Override
    public E pollLast() {
        throw new UnsupportedOperationException("pollLast");
    }

    @Override
    public NavigableSet<E> descendingSet() {
        throw new UnsupportedOperationException("descendingSet");
    }

    @Override
    public Iterator<E> descendingIterator() {
        throw new UnsupportedOperationException("descendingIterator");
    }

    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        throw new UnsupportedOperationException("subSet");
    }

    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        throw new UnsupportedOperationException("headSet");
    }

    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        throw new UnsupportedOperationException("tailSet");
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        throw new UnsupportedOperationException("subSet");
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        throw new UnsupportedOperationException("headSet");
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        throw new UnsupportedOperationException("tailSet");
    }

    @Override
    public Comparator<? super E> comparator() {
        throw new UnsupportedOperationException("comparator");
    }

    @Override
    public E first() {
        throw new UnsupportedOperationException("first");
    }

    @Override
    public E last() {
        throw new UnsupportedOperationException("last");
    }
    
}
