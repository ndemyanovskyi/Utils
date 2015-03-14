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
public class Trio<T1, T2, T3> extends Pair<T1, T2> {
    
    private T3 third;
    
    public Trio() {}
    
    public Trio(T1 first, T2 second, T3 third) {
        super(first, second);
        this.third = third;
    }

    @Override
    public Trio<T1, T2, T3> set(T1 first, T2 second) {
        return (Trio) super.set(first, second); 
    }
    
    public Trio<T1, T2, T3> set(T1 first, T2 second, T3 third) {
        setFirst(first);
	setSecond(second);
        setThird(third);
        return this;
    }    

    @Override
    public String toString() {
        return "[" + getFirst() + ", " + getSecond() + ", " + getThird() + "]";
    }

    public void setThird(T3 third) {
	this.third = third;
    }

    public T3 getThird() {
	return third;
    }
    
}
