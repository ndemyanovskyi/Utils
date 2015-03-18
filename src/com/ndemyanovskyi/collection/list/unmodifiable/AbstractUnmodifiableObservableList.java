/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection.list.unmodifiable;

import java.util.Collection;
import javafx.collections.ObservableList;

/**
 *
 * @author Назарій
 */
public abstract class AbstractUnmodifiableObservableList<E> extends AbstractUnmodifiableList<E> implements ObservableList<E> {

    @Override
    public final boolean addAll(E... elements) {
        throw new UnsupportedOperationException("addAll");
    }

    @Override
    public final boolean setAll(Collection<? extends E> col) {
        throw new UnsupportedOperationException("setAll");
    }

    @Override
    public final boolean setAll(E... elements) {
        throw new UnsupportedOperationException("setAll");
    }

    @Override
    public final void remove(int from, int to) {
        throw new UnsupportedOperationException("remove");
    }

    @Override
    public final boolean removeAll(E... elements) {
        throw new UnsupportedOperationException("removeAll");
    }

    @Override
    public final boolean retainAll(E... elements) {
        throw new UnsupportedOperationException("retainAll");
    }
     
}
