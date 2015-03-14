/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.time;

import java.time.LocalDate;
import java.util.NavigableSet;

/**
 *
 * @author Назарій
 */
public abstract class AbstractInterval implements Interval {
    
    private NavigableSet<LocalDate> descendingSet;

    @Override
    public NavigableSet<LocalDate> descendingSet() {
        return descendingSet != null ? descendingSet : 
                (descendingSet = new IntervalDescendingSet(this));
    }
        
    protected void checkInBounds(int index) {
        checkInBounds(index, "index");
    }
    
    protected void checkInBounds(int index, String name) {
        if(!inBounds(index)) {
            throw new IndexOutOfBoundsException(
                    name + " = " + index + "; bounds = [0; " + (size() - 1) + "]");
        }
    }
    
    protected void checkInBoundsExclusive(int index, String name) {
        if(!inBounds(index) && index > size()) {
            throw new IndexOutOfBoundsException(
                    name + " = " + index + "; bounds = [0; " + size() + "]");
        }
    }
    
    protected boolean inBounds(long index) {
        return index >= 0 && index < size();
    }
    
}
