/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.util;

/**
 *
 * @author nazar_000
 */
public class Pair<T1, T2> {
    
    private T1 first;
    private T2 second;
    
    public Pair() {}
    
    public Pair(T1 first, T2 second) {
        set(first, second);
    }
    
    public Pair set(T1 first, T2 second) {
        setFirst(first);
	setSecond(second);
        return this;
    }

    @Override
    public String toString() {
        return "[" + getFirst() + ", " + getSecond() + "]";
    }

    public void setFirst(T1 first) {
	this.first = first;
    }

    public T1 getFirst() {
	return first;
    }

    public void setSecond(T2 second) {
	this.second = second;
    }

    public T2 getSecond() {
	return second;
    }
    
}
