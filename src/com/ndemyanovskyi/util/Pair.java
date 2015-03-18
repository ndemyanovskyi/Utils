/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.util;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

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
    
    public Pair<T1, T2> set(T1 first, T2 second) {
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
    
    public void ifPresent(BiConsumer<? super T1, ? super T2> action) {
        T1 f = first;
        T2 s = second;
        if(f != null && s != null) action.accept(f, s);
    }
    
    public void ifFirstPresent(Consumer<? super T1> action) {
        T1 f = first;
        if(f != null) action.accept(f);
    }
    
    public void ifSecondPresent(Consumer<? super T2> action) {
        T2 s = second;
        if(s != null) action.accept(s);
    }
    
}
