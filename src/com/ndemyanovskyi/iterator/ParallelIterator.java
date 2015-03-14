/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.iterator;

import com.ndemyanovskyi.util.Quartet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;


public class ParallelIterator<T1, T2, T3, T4> implements Iterator<Quartet<T1, T2, T3, T4>> {
    
    private final List<Iterator<?>> iterators = new ArrayList<>();
    private final Quartet<T1, T2, T3, T4> value = new Quartet<>();

    public ParallelIterator(Iterator<T1> first, Iterator<T2> second, Iterator<T3> third, Iterator<T4> fourth) {
	iterators.add(Objects.requireNonNull(first, "first"));
	iterators.add(Objects.requireNonNull(second, "second"));
	iterators.add(Objects.requireNonNull(third, "third"));
	iterators.add(Objects.requireNonNull(fourth, "fourth"));
    } 

    protected Quartet<T1, T2, T3, T4> value() {
	return value;
    }

    protected List<Iterator<?>> iterators() {
	return iterators;
    }

    protected Iterator<T1> first() {
	return (Iterator<T1>) iterators.get(0);
    }

    protected Iterator<T2> second() {
	return (Iterator<T2>) iterators.get(1);
    }

    protected Iterator<T3> third() {
	return (Iterator<T3>) iterators.get(2);
    }

    protected Iterator<T4> fourth() {
	return (Iterator<T4>) iterators.get(3);
    }

    @Override
    public boolean hasNext() {
	if(!first().hasNext()) {
	    return false;
	}
	if(second() != first() && !second().hasNext()) {
	    return false;
	}
	if(third() != first() && third() != second() && !third().hasNext()) {
	    return false;
	}
	return !(fourth() != first() && fourth() != second() 
                && fourth() != third() && !fourth().hasNext());
    }

    @Override
    public Quartet<T1, T2, T3, T4> next() {
	if(!hasNext()) {
	    throw new NoSuchElementException();
	}
	
	//First
	value().setFirst(first().next());
        
	//Second
	value().setSecond(
                (second() == first()) ? (T2) value().getFirst() 
                        : second().next());
	//Third
	value().setThird(
                (third() == first()) ? (T3) value().getFirst() 
                        : (third() == second()) ? (T3) value().getSecond() 
                                : third().next());
	//Fourth
	value().setFourth(
                (fourth() == first()) ? (T4) value().getFirst() 
                        : (fourth() == second()) ? (T4) value().getSecond()
                                : (fourth() == third()) ? (T4) value().getThird() 
                                        : fourth().next());
	return value();
    }

    @Override
    public void remove() {
	first().remove();
	if(second() != first()) {
	    second().remove();
	}
	if(third() != first() && third() != second()) {
	    third().remove();
	}
	if(fourth() != first() && fourth() != second() && fourth() != third()) {
	    fourth().remove();
	}
    }

}
