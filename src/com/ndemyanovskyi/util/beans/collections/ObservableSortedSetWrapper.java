/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.util.beans.collections;

import com.ndemyanovskyi.collection.set.DefaultSortedSet;
import com.sun.javafx.collections.ObservableSetWrapper;
import com.sun.javafx.collections.SetListenerHelper;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;
import java.util.SortedSet;
import javafx.collections.SetChangeListener;

/**
 *
 * @author Назарій
 */
public class ObservableSortedSetWrapper<E> extends ObservableSetWrapper<E> implements DefaultSortedSet<E> {

    private final SortedSet<E> backingSet;
    private static Field listenerHelperField; 
    
    private static void setUpListenerHelperField() {
        if(listenerHelperField == null) {
            try {
                listenerHelperField = ObservableSetWrapper.class.
                        getDeclaredField("listenerHelper");
                listenerHelperField.setAccessible(true);
            } catch(NoSuchFieldException | SecurityException ex) {
                throw new IllegalStateException(
                        "'listenerHelper' field not found in ObservableSetWrapper.");
            }
        }
    }
    
    public ObservableSortedSetWrapper(SortedSet<E> set) {
        super(set);
        this.backingSet = set;
        setUpListenerHelperField();
    }
    
    protected SetListenerHelper<E> getListenerHelper() {
        try {
            return (SetListenerHelper<E>) listenerHelperField.get(this);
        } catch(IllegalArgumentException | IllegalAccessException ex) {
            throw new IllegalStateException(
                    "'listenerHelper' field can`t be get value.");
        }
    }
    
    protected void setListenerHelper(SetListenerHelper<E> helper) {
        try {
            listenerHelperField.set(this, helper);
        } catch(IllegalArgumentException | IllegalAccessException ex) {
            throw new IllegalStateException(
                    "'listenerHelper' field can`t be set value.");
        }
    }

    @Override
    public Comparator<? super E> comparator() {
        return backingSet.comparator();
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        return new SubObservableSet(backingSet.subSet(fromElement, toElement));
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        return new SubObservableSet(backingSet.headSet(toElement));
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        return new SubObservableSet(backingSet.tailSet(fromElement));
    }

    @Override
    public E first() {
        return backingSet.first();
    }

    @Override
    public E last() {
        return backingSet.last();
    }

    private void callObservers(SetChangeListener.Change<E> change) {
        SetListenerHelper.fireValueChangedEvent(getListenerHelper(), change);
    }
    
    private class SubObservableSet implements DefaultSortedSet<E> {
        
        private final SortedSet<E> backingSubSet;

        public SubObservableSet(SortedSet<E> set) {
            this.backingSubSet = Objects.requireNonNull(set, "set");
        }

        @Override
        public Comparator<? super E> comparator() {
            return backingSubSet.comparator();
        }

        @Override
        public SortedSet<E> subSet(E fromElement, E toElement) {
            return new SubObservableSet(backingSubSet.subSet(fromElement, toElement));
        }

        @Override
        public SortedSet<E> headSet(E toElement) {
            return new SubObservableSet(backingSubSet.headSet(toElement));
        }

        @Override
        public SortedSet<E> tailSet(E fromElement) {
            return new SubObservableSet(backingSubSet.tailSet(fromElement));
        }

        @Override
        public E first() {
            return backingSubSet.first();
        }

        @Override
        public E last() {
            return backingSubSet.last();
        }

        @Override
        public int size() {
            return backingSubSet.size();
        }

        @Override
        public boolean add(E e) {
            boolean added = backingSubSet.add(e);
            if(added) {
                callObservers(new SimpleAddChange(e));
            }
            return added;
        }

        @Override
        public boolean remove(Object o) {
            boolean removed = backingSubSet.remove(o);
            if(removed) {
                callObservers(new SimpleRemoveChange((E) o));
            }
            return removed;
        }

        @Override
        public void clear() {
            for(Iterator<E> i = backingSet.iterator(); i.hasNext();) {
                E element = i.next();
                i.remove();
                callObservers(new SimpleRemoveChange(element));
            }
        }

        @Override
        public boolean isEmpty() {
            return backingSubSet.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            return backingSubSet.contains(o);
        }

        @Override
        public Iterator<E> iterator() {
            return backingSubSet.iterator();
        }

        @Override
        public Object[] toArray() {
            return backingSubSet.toArray();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            return backingSubSet.toArray(a);
        }
        
    }
    
    private class SimpleAddChange extends SetChangeListener.Change<E> {

        private final E added;

        public SimpleAddChange(E added) {
            super(ObservableSortedSetWrapper.this);
            this.added = added;
        }

        @Override
        public boolean wasAdded() {
            return true;
        }

        @Override
        public boolean wasRemoved() {
            return false;
        }

        @Override
        public E getElementAdded() {
            return added;
        }

        @Override
        public E getElementRemoved() {
            return null;
        }

        @Override
        public String toString() {
            return "added " + added;
        }
        
    }
    
    private class SimpleRemoveChange extends SetChangeListener.Change<E> {

        private final E removed;

        public SimpleRemoveChange(E removed) {
            super(ObservableSortedSetWrapper.this);
            this.removed = removed;
        }

        @Override
        public boolean wasAdded() {
            return false;
        }

        @Override
        public boolean wasRemoved() {
            return true;
        }

        @Override
        public E getElementAdded() {
            return null;
        }

        @Override
        public E getElementRemoved() {
            return removed;
        }

        @Override
        public String toString() {
            return "removed " + removed;
        }
        
    }
    
}
