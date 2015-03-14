/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection.list;

import com.ndemyanovskyi.collection.list.unmodifiable.AbstractUnmodifiableList;
import com.ndemyanovskyi.listiterator.ListIterators;
import java.util.ListIterator;

/**
 *
 * @author Назарій
 */
public class SingletonList<E> extends AbstractUnmodifiableList<E> {
    
    private static final SingletonList<?> NULLABLE_INSTANCE = new SingletonList<>(null);
    
    private final E element;

    public SingletonList(E element) {
        this.element = element;
    }
    
    @SuppressWarnings("unchecked")
    public static <E> SingletonList<E> of(E element) {
        return element != null 
                ? new SingletonList<>(element) 
                : (SingletonList<E>) NULLABLE_INSTANCE;
    }

    @Override
    public E get(int index) {
        checkIndex(index);
        return element;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        checkIndex(index);
        return ListIterators.singleton(element);
    }
    
    private void checkIndex(int index) {
        if(index != 0) {
            throw new IndexOutOfBoundsException(
                    "Index = " + index + "; Size = 1.");
        }
    }

    @Override
    public int size() {
        return 1;
    }
    
}
