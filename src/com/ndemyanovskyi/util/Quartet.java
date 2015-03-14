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
public class Quartet<T1, T2, T3, T4> extends Trio<T1, T2, T3> {
    
    private T4 fourth;
    
    public Quartet() {}
    
    public Quartet(T1 first, T2 second, T3 third, T4 fourth) {
        set(first, second, third, fourth);
    }

    @Override
    public Quartet<T1, T2, T3, T4> set(T1 first, T2 second) {
        return (Quartet) super.set(first, second); 
    }
    
    @Override
    public Quartet<T1, T2, T3, T4> set(T1 first, T2 second, T3 third) {
        return (Quartet) super.set(first, second, third); 
    }
    
    public Quartet<T1, T2, T3, T4> set(
            T1 first, T2 second, T3 third, T4 fourth) {
        set(first, second, third);
        setFourth(fourth);
        return this;
    }

    @Override
    public String toString() {
        return "[" + getFirst() + ", " + getSecond() + ", " + getThird() + ", " + getFourth() + "]";
    }

    public void setFourth(T4 fourth) {
	this.fourth = fourth;
    }

    public T4 getFourth() {
	return fourth;
    }
    
}
