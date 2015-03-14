/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.listiterator.unmodifiable;

import com.ndemyanovskyi.iterator.unmodifiable.AbstractUnmodifiableIterator;
import java.util.ListIterator;

/**
 *
 * @author Назарій
 */
public abstract class AbstractUnmodifiableListIterator<T> extends AbstractUnmodifiableIterator<T> implements ListIterator<T> {

    @Override
    public final void set(T e) {
        throw new UnsupportedOperationException("set");
    }

    @Override
    public final void add(T e) {
        throw new UnsupportedOperationException("add");
    }
    
}
