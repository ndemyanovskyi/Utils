/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.util;

import com.ndemyanovskyi.collection.list.unmodifiable.UnmodifiableList;
import com.ndemyanovskyi.collection.set.unmodifiable.UnmodifiableNavigableSet;
import com.ndemyanovskyi.collection.set.unmodifiable.UnmodifiableSet;
import com.ndemyanovskyi.collection.set.unmodifiable.UnmodifiableSortedSet;
import com.ndemyanovskyi.collection.unmodifiable.UnmodifiableCollection;
import com.ndemyanovskyi.iterator.Iterators;
import com.ndemyanovskyi.iterator.unmodifiable.UnmodifiableIterator;
import com.ndemyanovskyi.listiterator.ListIterators;
import com.ndemyanovskyi.map.HashPool;
import com.ndemyanovskyi.map.Pool;
import com.ndemyanovskyi.map.unmodifiable.UnmodifiableMap;
import com.ndemyanovskyi.map.unmodifiable.UnmodifiablePool;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Implementing this interface if your class is unmodifiable.
 *
 * @author Назарій
 */
public interface Unmodifiable {

    //<editor-fold defaultstate="collapsed" desc="Set">
    public static <T> Set<T> set(Set<T> set) {
        return new UnmodifiableSet<>(set);
    }

    public static <T> Set<T> set() {
        return Constants.EMPTY_SET;
    }

    public static <T> Set<T> set(T... array) {
        if(array.length == 0) {
            return set();
        }

        Set<T> set = new HashSet<>();
        for(T t : array) {
            set.add(t);
        }
        return new UnmodifiableSet<>(set);
    }

    public static <T> Set<T> set(Iterable<T> iterable) {
        Iterator<T> it = iterable.iterator();
        
        if(!it.hasNext()) {
            return set();
        }

        Set<T> set = new HashSet<>();
        while(it.hasNext()) {
            set.add(it.next());
        }
        return new UnmodifiableSet<>(set);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SortedSet">

    public static <T> SortedSet<T> sortedSet(SortedSet<T> set) {
        return new UnmodifiableSortedSet<>(set);
    }

    public static <T> SortedSet<T> sortedSet() {
        return Constants.EMPTY_SORTED_SET;
    }

    public static <T> SortedSet<T> sortedSet(T... array) {
        return navigableSet(array);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="NavigableSet">

    public static <T> NavigableSet<T> navigableSet(NavigableSet<T> set) {
        return new UnmodifiableNavigableSet<>(set);
    }

    public static <T> NavigableSet<T> navigableSet() {
        return Constants.EMPTY_NAVIGABLE_SET;
    }

    public static <T> NavigableSet<T> navigableSet(T... array) {
        if(array.length == 0) return navigableSet();
        return new UnmodifiableNavigableSet<>(
                new TreeSet<>(Arrays.asList(array)));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="List">

    public static <T> List<T> list() {
        return Constants.EMPTY_LIST;
    }

    public static <T> List<T> list(List<T> list) {
        return new UnmodifiableList<>(list);
    }

    public static <T> List<T> list(T... array) {
        return array.length > 0 
                ? new UnmodifiableList<>(Arrays.asList(array)) 
                : list();
    }

    public static <T> List<T> list(Iterable<T> iterable) {
        Iterator<T> it = iterable.iterator();
        
        if(!it.hasNext()) {
            return list();
        }

        List<T> list = new ArrayList<>();
        while(it.hasNext()) {
            list.add(it.next());
        }
        return list(list);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Collection">

    public static <T> Collection<T> collection() {
        return Constants.EMPTY_COLLECTION;
    }

    public static <T> Collection<T> collection(Collection<T> list) {
        return new UnmodifiableCollection<>(list);
    }

    public static <T> Collection<T> collection(T... array) {
        switch(array.length) {
            case 0: return collection();
            case 1: return collection(array[0]);
            default: return collection(Arrays.asList(array));
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Iterator">

    public static <T> Iterator<T> iterator() {
        return Constants.EMPTY_ITERATOR;
    }

    public static <T> Iterator<T> iterator(T... array) {
        return array.length != 0
                ? listIterator(array)
                : iterator();
    }

    public static <T> Iterator<T> iterator(Iterator<T> it) {
        return it.hasNext()
                ? Iterators.unmodifiable(it)
                : iterator();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ListIterator">

    public static <T> ListIterator<T> listIterator() {
        return Constants.EMPTY_LIST_ITERATOR;
    }

    public static <T> ListIterator<T> listIterator(ListIterator<T> it) {
        return it.hasNext() || it.hasPrevious()
                ? ListIterators.unmodifiable(it)
                : listIterator();
    }

    public static <T> ListIterator<T> listIterator(T... array) {
        return array.length != 0
                ? ListIterators.unmodifiable(ListIterators.of(Arrays.asList(array)))
                : listIterator();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Map">

    public static <K, V> Map<K, V> map() {
        return Constants.EMPTY_MAP;
    }

    public static <K, V> Map<K, V> map(Map<K, V> map) {
        return new UnmodifiableMap<>(map);
    }

    /**
     * Returns a map of keys and values taken from the array as pairs. 
     * For example array: [1, "one", 2, "two", 3, "three"] 
     * converted to this map: [[1, "one"], [2, "two"], [3, three]].
     * All elements with <b>even index</b> are appended as <b>keys</b>.
     * All elements with <b>odd index</b> are appended as <b>values</b>.
     * @param elements array of key(K) and value(V) objects that are placed there by turns. For example: [K, V, K, V, ... , K, V]
     * @return map contained keys and values from array.
     */
    public static Map<?, ?> map(Object... elements) {
        return map(Object.class, Object.class, elements);
    }

    /**
     * Returns a map of keys and values taken from the array as pairs. 
     * For example array: [1, "one", 2, "two", 3, "three"] 
     * converted to this map: [[1, "one"], [2, "two"], [3, three]].
     * All elements with <b>even index</b> are appended as <b>keys</b>.
     * All elements with <b>odd index</b> are appended as <b>values</b>.
     * @param <K> key type
     * @param keyType class of K
     * @param elements array of key(K) and value(V) objects that are placed there by turns. For example: [K, V, K, V, ... , K, V]
     * @return map contained keys and values from array.
     */
    public static <K> Map<K, ?> map(Class<K> keyType, Object... elements) {
        return map(keyType, Object.class, elements);
    }

    /**
     * Returns a map of keys and values taken from the array as pairs. 
     * For example array: [1, "one", 2, "two", 3, "three"] 
     * converted to this map: [[1, "one"], [2, "two"], [3, three]]. 
     * All elements with <b>even index</b> are appended as <b>keys</b>.
     * All elements with <b>odd index</b> are appended as <b>values</b>.
     * @param <K> key type
     * @param <V> value type
     * @param keyType class of K
     * @param valueType class of V
     * @param elements array of key(K) and value(V) objects that are placed there by turns. For example: [K, V, K, V, ... , K, V]
     * @return map contained keys and values from array.
     */
    public static <K, V> Map<K, V> map(Class<K> keyType, Class<V> valueType, Object... elements) {
        Map<K, V> map = new HashMap<>();
        for(int i = 0; i < elements.length; i+=2) {
            Object key = elements[i];
            if(key != null && !keyType.isInstance(key)) {
                throw new IllegalArgumentException(String.format(
                        "The element with event index(%d) is not an instance of %s.", 
                        i, keyType.getSimpleName()));
            }
            
            Object value = i + 1 < elements.length ? elements[i + 1] : null;
            if(value != null && !valueType.isInstance(value)) {
                throw new IllegalArgumentException(String.format(
                        "The element with odd index(%d) is not an instance of %s.", 
                        i, valueType.getSimpleName()));
            }
            map.put((K) key, (V) value);
        }
        return map(map);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Pool">

    @SuppressWarnings("unchecked")
    public static <K, V> Pool<K, V> pool() {
        return Constants.EMPTY_POOL;
    }

    public static <K, V> Pool<K, V> pool(Pool<K, V> pool) {
        return new UnmodifiablePool<>(pool);
    }
    //</editor-fold>

    public static interface Wrapper<E> {
        
        public E modifiable();
        public E unmodifiable();
        
        public static <T> Wrapper<T> of(T modifiable, T unmodifiable) {
            return new Wrapper<T>() {

                @Override
                public T modifiable() {
                    return modifiable;
                }

                @Override
                public T unmodifiable() {
                    return unmodifiable;
                }
                
            };
        }
        
    }

}

final class Constants {

    public static final Set EMPTY_SET
            = new UnmodifiableSet(Collections.EMPTY_SET);

    public static final SortedSet EMPTY_SORTED_SET
            = new UnmodifiableSortedSet(Collections.emptySortedSet());

    public static final NavigableSet EMPTY_NAVIGABLE_SET
            = new UnmodifiableNavigableSet(Collections.emptyNavigableSet());

    public static final List EMPTY_LIST
            = new UnmodifiableList<>(Collections.EMPTY_LIST);

    public static final Collection EMPTY_COLLECTION
            = new UnmodifiableCollection<>(Collections.EMPTY_LIST);

    public static final Iterator EMPTY_ITERATOR
            = new UnmodifiableIterator(Iterators.empty());

    public static final ListIterator EMPTY_LIST_ITERATOR
            = ListIterators.empty();

    public static final Map EMPTY_MAP
            = new UnmodifiableMap(Collections.EMPTY_MAP);

    public static final Pool EMPTY_POOL
            = new UnmodifiablePool<>(new HashPool<>(v -> null));

    public Object base() {
        throw new UnsupportedOperationException("base");
    }

}
