/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.collection.set.unmodifiable;

import com.ndemyanovskyi.util.Unmodifiable;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;


public class UnmodifiableSet<E> extends AbstractUnmodifiableSet<E> {

    private final Set<E> base;
    
    public UnmodifiableSet(Set<E> base) {
	this.base = Objects.requireNonNull(base);
    }

    protected Set<E> base() {
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
