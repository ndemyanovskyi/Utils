/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection.list;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Spliterator;

/**
 *
 * @author Назарій
 */
public interface UniqueList<E> extends List<E>, Set<E> {

    @Override
    public default Iterator<E> iterator() {
        return listIterator();
    }

    @Override
    public default ListIterator<E> listIterator() {
        return listIterator(0);
    }
    
    @Override
    default public Spliterator<E> spliterator() {
        return List.super.spliterator();
    }
    
}
