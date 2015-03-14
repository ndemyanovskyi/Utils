/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection.list;

import com.ndemyanovskyi.iterator.Iterators;
import com.ndemyanovskyi.listiterator.ListIterators;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 *
 * @author Назарій
 */
public class UniqueArrayList<E> extends ArrayList<E> implements UniqueList<E> {
    
    private UniqueArrayList<E> unmodifiable;
    
    public UniqueArrayList() {}
    
    public UniqueArrayList(int capacity) {
        super(capacity);
    }
    
    public UniqueArrayList(Collection<? extends E> c) {
        super(c);
    }

    @Override
    public UniqueArrayList<E> clone() {
        return (UniqueArrayList<E>) super.clone();
    }

    @Override
    public boolean add(E e) {
        return !contains(e) && super.add(e);
    }

    public UniqueArrayList<E> unmodifiable() {
        if(unmodifiable == null) {
            unmodifiable = new UnmodifiableUniqueArrayList<>(this);
        }
        return unmodifiable;
    }
    
    private static class UnmodifiableUniqueArrayList<E> extends UniqueArrayList<E> {

        private UniqueArrayList<E> base;
        
        public UnmodifiableUniqueArrayList(UniqueArrayList<E> base) {
            this.base = Objects.requireNonNull(base);
        }
        
        @Override
        public boolean add(E e) {
            throw new UnsupportedOperationException("add");
        }  

        @Override
        public void trimToSize() {
            base.trimToSize();
        }

        @Override
        public void ensureCapacity(int minCapacity) {
            base.ensureCapacity(minCapacity);
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
        public int indexOf(Object o) {
            return base.indexOf(o);
        }

        @Override
        public int lastIndexOf(Object o) {
            return base.lastIndexOf(o);
        }

        @Override
        public UniqueArrayList<E> clone() {
            return base.clone();
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
        public E get(int index) {
            return base.get(index);
        }

        @Override
        public E set(int index, E element) {
            throw new UnsupportedOperationException("set");
        }

        @Override
        public void add(int index, E element) {
            throw new UnsupportedOperationException("add");
        }

        @Override
        public E remove(int index) {
            throw new UnsupportedOperationException("remove");
        }

        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException("remove");
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException("clear");
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            throw new UnsupportedOperationException("addAll");
        }

        @Override
        public boolean addAll(int index, Collection<? extends E> c) {
            throw new UnsupportedOperationException("addAll");
        }

        @Override
        protected void removeRange(int fromIndex, int toIndex) {
            throw new UnsupportedOperationException("removeRange");
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException("removeAll");
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException("retainAll");
        }

        @Override
        public ListIterator<E> listIterator(int index) {
            return ListIterators.unmodifiable(base.listIterator(index));
        }

        @Override
        public ListIterator<E> listIterator() {
            return listIterator(0);
        }

        @Override
        public Iterator<E> iterator() {
            return Iterators.unmodifiable(base.iterator());
        }

        @Override
        public void forEach(Consumer<? super E> action) {
            base.forEach(action);
        }

        @Override
        public Spliterator<E> spliterator() {
            return base.spliterator();
        }

        @Override
        public boolean removeIf(Predicate<? super E> filter) {
            throw new UnsupportedOperationException("removeIf");
        }

        @Override
        public void replaceAll(UnaryOperator<E> operator) {
            throw new UnsupportedOperationException("repalceAll");
        }

        @Override
        public void sort(Comparator<? super E> c) {
            throw new UnsupportedOperationException("sort");
        }
        
    }
    
}
