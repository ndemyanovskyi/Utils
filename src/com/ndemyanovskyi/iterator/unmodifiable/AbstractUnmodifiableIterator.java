/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.iterator.unmodifiable;

import com.ndemyanovskyi.util.Unmodifiable;
import java.util.Iterator;

/**
 *
 * @author Назарій
 */
public abstract class AbstractUnmodifiableIterator<T> implements Iterator<T>, Unmodifiable {

    @Override
    public final void remove() {
        throw new UnsupportedOperationException("remove");
    }
    
}
