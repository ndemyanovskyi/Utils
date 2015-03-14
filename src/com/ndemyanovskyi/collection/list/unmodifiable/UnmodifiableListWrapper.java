/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection.list.unmodifiable;

import com.ndemyanovskyi.util.Unmodifiable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/**
 *
 * @author Назарій
 */
public class UnmodifiableListWrapper<E> implements List<E>, Unmodifiable.Wrapper<List<E>> {
    
    private final List<E> modifiable;
    private List<E> unmodifiable;

    public UnmodifiableListWrapper() {
        this(new ArrayList<>());
    }

    public UnmodifiableListWrapper(List<E> modifiable) {
	this.modifiable = Objects.requireNonNull(modifiable, "modifiable");
    }

    @Override
    public List<E> modifiable() {
	return modifiable;
    }

    @Override
    public List<E> unmodifiable() {
	return unmodifiable != null ? unmodifiable 
		: (unmodifiable = Unmodifiable.list(modifiable));
    }

    //<editor-fold defaultstate="collapsed" desc="Delegats">
    @Override
    public int size() {
        return modifiable.size();
    }
    
    @Override
    public boolean isEmpty() {
        return modifiable.isEmpty();
    }
    
    @Override
    public boolean contains(Object o) {
        return modifiable.contains(o);
    }
    
    @Override
    public Iterator<E> iterator() {
        return modifiable.iterator();
    }
    
    @Override
    public Object[] toArray() {
        return modifiable.toArray();
    }
    
    @Override
    public <T> T[] toArray(T[] a) {
        return modifiable.toArray(a);
    }
    
    @Override
    public boolean add(E e) {
        return modifiable.add(e);
    }
    
    @Override
    public boolean remove(Object o) {
        return modifiable.remove(o);
    }
    
    @Override
    public boolean containsAll(Collection<?> c) {
        return modifiable.containsAll(c);
    }
    
    @Override
    public boolean addAll(Collection<? extends E> c) {
        return modifiable.addAll(c);
    }
    
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return modifiable.addAll(index, c);
    }
    
    @Override
    public boolean removeAll(Collection<?> c) {
        return modifiable.removeAll(c);
    }
    
    @Override
    public boolean retainAll(Collection<?> c) {
        return modifiable.retainAll(c);
    }
    
    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        modifiable.replaceAll(operator);
    }
    
    @Override
    public void sort(Comparator<? super E> c) {
        modifiable.sort(c);
    }
    
    @Override
    public void clear() {
        modifiable.clear();
    }
    
    @Override
    public boolean equals(Object o) {
        return modifiable.equals(o);
    }
    
    @Override
    public int hashCode() {
        return modifiable.hashCode();
    }
    
    @Override
    public E get(int index) {
        return modifiable.get(index);
    }
    
    @Override
    public E set(int index, E element) {
        return modifiable.set(index, element);
    }
    
    @Override
    public void add(int index, E element) {
        modifiable.add(index, element);
    }
    
    @Override
    public E remove(int index) {
        return modifiable.remove(index);
    }
    
    @Override
    public int indexOf(Object o) {
        return modifiable.indexOf(o);
    }
    
    @Override
    public int lastIndexOf(Object o) {
        return modifiable.lastIndexOf(o);
    }
    
    @Override
    public ListIterator<E> listIterator() {
        return modifiable.listIterator();
    }
    
    @Override
    public ListIterator<E> listIterator(int index) {
        return modifiable.listIterator(index);
    }
    
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return modifiable.subList(fromIndex, toIndex);
    }
    
    @Override
    public Spliterator<E> spliterator() {
        return modifiable.spliterator();
    }
    
    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        return modifiable.removeIf(filter);
    }
    
    @Override
    public Stream<E> stream() {
        return modifiable.stream();
    }
    
    @Override
    public Stream<E> parallelStream() {
        return modifiable.parallelStream();
    }
    
    @Override
    public void forEach(Consumer<? super E> action) {
        modifiable.forEach(action);
    }
    
    @Override
    public String toString() {
        return modifiable.toString();
    }
    //</editor-fold>
    
}
