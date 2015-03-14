/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.collection.list.unmodifiable;

import com.ndemyanovskyi.util.Unmodifiable;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;


public class UnmodifiableList<E> extends AbstractUnmodifiableList<E> {
    
    private final List<E> base;

    public UnmodifiableList(List<E> base) {
	this.base = Objects.requireNonNull(base);
    }

    protected List<E> base() {
	return base;
    }

    @Override
    public E get(int index) {
	return base.get(index);
    }

    @Override
    public int size() {
	return base.size();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
	return Unmodifiable.listIterator(base.listIterator(index));
    }

}
