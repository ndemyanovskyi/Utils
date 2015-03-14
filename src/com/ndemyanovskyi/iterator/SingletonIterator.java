/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author Назарій
 */
public class SingletonIterator<T> implements Iterator<T> {
    
    private final T element;
    private boolean hasNext = true;

    public SingletonIterator(T element) {
        this.element = element;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public T next() {
        if(!hasNext) {
            throw new NoSuchElementException();
        }
        hasNext = false;
        return element;
    }
    
}
