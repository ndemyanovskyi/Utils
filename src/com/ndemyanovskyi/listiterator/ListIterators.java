/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.listiterator;

import com.ndemyanovskyi.iterator.unmodifiable.UnmodifiableListIterator;
import com.ndemyanovskyi.util.Pair;
import com.ndemyanovskyi.util.Quartet;
import com.ndemyanovskyi.util.Trio;
import com.ndemyanovskyi.util.Unmodifiable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.function.Predicate;

/**
 *
 * @author Назарій
 */
public class ListIterators {
    
    private static final ListIterator EMPTY = of(Unmodifiable.list());
    
    public static <T> ListIterator<T> empty() {
        return EMPTY;
    }
    
    public static <T> ListIterator<T> unmodifiable(ListIterator<T> base) {
        return new UnmodifiableListIterator<>(base);
    }
    
    public static <T> ListIterator<T> filtered(ListIterator<T> base, Predicate<? super T> predicate) {
        return new FilteredListIterator<>(base, predicate);
    } 
    
    public static <T> ListIterator<T> combined(ListIterator<T> first, ListIterator<T> second, ListIterator<T>... others) {
        return combined(0, first, second, others);
    } 
    
    public static <T> ListIterator<T> combined(ListIterator<T>[] iterators) {
        return combined(0, iterators);
    } 
    
    public static <T> ListIterator<T> combined(int position, ListIterator<T>[] iterators) {
        if(iterators.length == 0) return empty();
        return combined(position, new ArrayList<>(Arrays.asList(iterators)));
    } 
    
    public static <T> ListIterator<T> combined(int position, ListIterator<T> first, ListIterator<T> second, ListIterator<T>... others) {
        Objects.requireNonNull(first, "first");
        Objects.requireNonNull(second, "second");
        
        List<ListIterator<T>> iterators = new ArrayList<>();
        iterators.add(first);
        iterators.add(second);
        iterators.addAll(Arrays.asList(others));
        return combined(position, iterators);
    } 
    
    public static <T> ListIterator<T> combined(List<ListIterator<T>> iterators) {
        return combined(0, iterators);
    } 
    
    public static <T> ListIterator<T> combined(int position, List<ListIterator<T>> iterators) {
        if(iterators.isEmpty()) return empty();
        return new CombinedListIterator<>(position, iterators);
    } 
    
    public static <T> ListIterator<T> singleton(T value) {
        return new SingletonListIterator<>(value);
    } 

    public static <T1, T2> ListIterator<Pair<T1, T2>> parallel(
            ListIterator<T1> first, ListIterator<T2> second) {
	return (ListIterator) parallel(first, second, first, first);
    }

    public static <T1, T2, T3> ListIterator<Trio<T1, T2, T3>> parallel(
	    ListIterator<T1> first, ListIterator<T2> second, ListIterator<T3> third) {
	return (ListIterator) parallel(first, second, third, first);
    }

    public static <T1, T2, T3, T4> ListIterator<Quartet<T1, T2, T3, T4>> parallel(
	    ListIterator<T1> first, ListIterator<T2> second, ListIterator<T3> third, ListIterator<T4> fourth) {
	return new ParallelListIterator<>(first, second, third, fourth);
    }
    
    public static <T> ListIterator<T> of(List<T> list) {
        return of(0, list);
    }
    
    public static <T> ListIterator<T> of(int index, List<T> list) {
        return new DefaultListIterator<>(index, list);
    }
    
    public static <T> ListIterator<T> of(int fromInclusive, int toExclusive, List<T> list) {
        return of(fromInclusive, toExclusive, 0, list);
    }
    
    public static <T> ListIterator<T> of(int fromInclusive, int toExclusive, int index, List<T> list) {
        return new DefaultListIterator<>(fromInclusive, toExclusive, index, list);
    }

    public static <T> List<ListIterator<T>> get(Collection<? extends List<T>> lists) {
	Objects.requireNonNull(lists, "lists");
        
        if(lists.isEmpty()) return Unmodifiable.list();
        
        List<ListIterator<T>> iterators = new ArrayList<>();
        for(List<T> list : lists) {
            iterators.add(list.listIterator());
        }
	return iterators;
    }

    public static <T> List<ListIterator<T>> get(List<T>... lists) {
        Objects.requireNonNull(lists, "lists");
        
        if(lists.length == 0) return Unmodifiable.list();
        
        List<ListIterator<T>> iterators = new ArrayList<>();
        for(List<T> list : lists) {
            iterators.add(list.listIterator());
        }
	return iterators;
    }
    
}
