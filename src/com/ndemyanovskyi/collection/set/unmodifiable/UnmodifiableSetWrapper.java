/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.collection.set.unmodifiable;

import com.ndemyanovskyi.util.Unmodifiable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;


public class UnmodifiableSetWrapper<E> implements Set<E>, Unmodifiable.Wrapper<Set<E>> {
    
    private final Set<E> modifiable;
    private Set<E> unmodifiable;

    public UnmodifiableSetWrapper(Set<E> modifiable) {
	this.modifiable = Objects.requireNonNull(modifiable, "modifiable");
    }

    @Override
    public Set<E> modifiable() {
	return modifiable;
    }

    @Override
    public Set<E> unmodifiable() {
	return unmodifiable != null ? unmodifiable 
		: (unmodifiable = Unmodifiable.set(modifiable));
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
    public boolean retainAll(Collection<?> c) {
	return modifiable.retainAll(c);
    }
    
    @Override
    public boolean removeAll(Collection<?> c) {
	return modifiable.removeAll(c);
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
