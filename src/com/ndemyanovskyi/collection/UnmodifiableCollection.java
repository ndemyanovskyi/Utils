/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.collection;

import com.ndemyanovskyi.util.Unmodifiable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;


public class UnmodifiableCollection<E> extends AbstractUnmodifiableCollection<E> {
    
    private final Collection<E> base;

    public UnmodifiableCollection(Collection<E> base) {
	this.base = Objects.requireNonNull(base, "base");
    }
    
    protected Collection<E> base() {
	return base;
    }

    @Override
    public Iterator<E> iterator() {
	return Unmodifiable.iterator(base.iterator());
    }

    @Override
    public int size() {
	return base.size();
    }

}
