/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.listiterator;

import com.ndemyanovskyi.iterator.ParallelIterator;
import com.ndemyanovskyi.util.Quartet;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;


public class ParallelListIterator<T1, T2, T3, T4> extends ParallelIterator<T1, T2, T3, T4> implements ListIterator<Quartet<T1, T2, T3, T4>> {

    public ParallelListIterator(Iterator<T1> first, Iterator<T2> second, Iterator<T3> third, Iterator<T4> fourth) {
	super(first, second, third, fourth);
    } 

    @Override
    protected ListIterator<T1> first() {
	return (ListIterator<T1>) super.first();
    }

    @Override
    protected ListIterator<T2> second() {
	return (ListIterator<T2>) super.second();
    }

    @Override
    protected ListIterator<T3> third() {
	return (ListIterator<T3>) super.third();
    }

    @Override
    protected ListIterator<T4> fourth() {
	return (ListIterator<T4>) super.fourth();
    }

    @Override
    public boolean hasPrevious() {
	if(!first().hasPrevious()) {
	    return false;
	}
	if(second() != first() && !second().hasPrevious()) {
	    return false;
	}
	if(third() != first() && third() != second() && !third().hasPrevious()) {
	    return false;
	}
	return !(fourth() != first() && fourth() != second() && fourth() != third() && !fourth().hasPrevious());
    }

    @Override
    public Quartet<T1, T2, T3, T4> previous() {
	if(!hasPrevious()) {
	    throw new NoSuchElementException();
	}
	//First
	value().setFirst(first().previous());
	//Second
	value().setSecond((second() == first()) ? (T2) value().getFirst() : second().previous());
	//Third
	value().setThird((third() == first()) ? (T3) value().getFirst() : (third() == second()) ? 
		(T3) value().getSecond() : third().previous());
	//Fourth
	value().setFourth((fourth() == first()) ? 
		(T4) value().getFirst() : (fourth() == second()) ? 
			(T4) value().getSecond() : (fourth() == third()) ? 
				(T4) value().getThird() : fourth().previous());
	return value();
    }

    @Override
    public int nextIndex() {
	return first().nextIndex();
    }

    @Override
    public int previousIndex() {
	return first().previousIndex();
    }

    @Override
    public void set(Quartet<T1, T2, T3, T4> e) {
	//First
	first().set(e.getFirst());
	//Second
	if(second() != first()) {
	    second().set(e.getSecond());
	}
	//Third
	if(third() != first() && third() != second()) {
	    third().set(e.getThird());
	}
	//Fourth
	if(fourth() != first() && fourth() != second() && fourth() != third()) {
	    fourth().set(e.getFourth());
	}
    }

    @Override
    public void add(Quartet<T1, T2, T3, T4> e) {
	//First
	first().add(e.getFirst());
	//Second
	if(second() != first()) {
	    second().add(e.getSecond());
	}
	//Third
	if(third() != first() && third() != second()) {
	    third().add(e.getThird());
	}
	//Fourth
	if(fourth() != first() && fourth() != second() && fourth() != third()) {
	    fourth().add(e.getFourth());
	}
    }

}
