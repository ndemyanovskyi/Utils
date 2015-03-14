/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.collection.set;

import com.ndemyanovskyi.collection.FilteredCollection;
import java.util.Set;
import java.util.function.Predicate;


public class FilteredSet<E> extends FilteredCollection<E> implements Set<E> {

    public FilteredSet(Set<E> base, Predicate<? super E> predicate) {
	super(base, predicate);
    }

    @Override
    protected Set<E> base() {
	return (Set<E>) super.base();
    }

}
