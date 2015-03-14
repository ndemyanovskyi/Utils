/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection;

import com.sun.javafx.collections.ObservableListWrapper;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import javafx.collections.ObservableList;

/**
 *
 * @author nazar_000
 */
public class Collections {
    
    public static <E> ObservableList<E> observableList(List<E> base) {
	return new ObservableListWrapper<>(base);
    }
    
    public static <T extends Collection<?>> T requireNonEmpty(T c) {
        return requireNonEmpty(c, "");
    }
    
    public static <T extends Collection<?>> T requireNonEmpty(
            T c, String message) {
        if(c.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return c;
    }  
    
    public static <T> void forEach(Iterable<T> values, Consumer<? super T> action) {
        Objects.requireNonNull(values);
        Objects.requireNonNull(action);
        if(values instanceof List && values instanceof RandomAccess) {
            List<T> list = (List<T>) values;
            for(int i = 0; i < list.size(); i++) {
                action.accept(list.get(i));
            }
        } else {
            Iterator<T> it = values.iterator();
            while(it.hasNext()) {
                action.accept(it.next());
            }
        }
    }
    
    public static <T> void forEach(Iterable<T> values, Consumer<? super T> action, Consumer<? super T> lastAction) {
        Objects.requireNonNull(values);
        Objects.requireNonNull(action);
        if(values instanceof List && values instanceof RandomAccess) {
            List<T> list = (List<T>) values;
            for(int i = 0; i < list.size(); i++) {
                action.accept(list.get(i));
            }
        } else {
            Iterator<T> it = values.iterator();
            while(it.hasNext()) {
                action.accept(it.next());
            }
        }
    }
    
    public static <T> void forEachInverted(List<T> list, Consumer<? super T> action) {
        Objects.requireNonNull(list);
        Objects.requireNonNull(action);
        
        ListIterator<T> it = list.listIterator(list.size());
        while(it.hasPrevious()) {
            action.accept(it.previous());
        }
    }
    
    public static <T> void forEach(Iterable<T> values, BiConsumer<? super T, Integer> action) {
        Objects.requireNonNull(values);
        Objects.requireNonNull(action);
        if(values instanceof List && values instanceof RandomAccess) {
            List<T> list = (List<T>) values;
            for(int i = 0; i < list.size(); i++) {
                action.accept(list.get(i), i);
            }
        } else {
            Iterator<T> it = values.iterator();
            int index = 0;
            while(it.hasNext()) {
                action.accept(it.next(), index++);
            }
        }
    }
    
    public static <T, P> P forEach(
            Iterable<T> values, P param, BiFunction<? super T, P, P> action) {
        Objects.requireNonNull(values);
        Objects.requireNonNull(action);
        if(values instanceof List && values instanceof RandomAccess) {
            List<T> list = (List<T>) values;
            for(int i = 0; i < list.size(); i++) {
                param = action.apply(list.get(i), param);
            }
        } else {
            Iterator<T> it = values.iterator();
            int index = 0;
            while(it.hasNext()) {
                param = action.apply(it.next(), param);
            }
        }
        return param;
    }
    
}
