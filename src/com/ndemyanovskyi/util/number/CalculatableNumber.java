/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.util.number;

/**
 *
 * @author Назарій
 */
public abstract class CalculatableNumber<T extends Number> extends Number implements Calculatable<T> {

    @Override
    public int intValue() {
        return (int) doubleValue();
    }

    @Override
    public long longValue() {
        return (long) doubleValue();
    }

    @Override
    public float floatValue() {
        return (float) doubleValue();
    }

    @Override
    public boolean equals(Object o) {
        return o == this || (o != null && o instanceof Number && compareTo((Number) o) == 0);
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + intValue();
        return hash;
    }

    @Override
    public abstract String toString();
    
}
