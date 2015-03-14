/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.util.number;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author Назарій
 */
public interface Calculatable<T extends Number> extends Comparable<Number> {

    public T value();
    public T abs();
    public T negate();
    public BigInteger toBigInteger();
    public BigDecimal toBigDecimal();
    
    
    public T pow(int n);
    public T add(Number other);
    public T divide(Number other);
    public T subtract(Number other);
    public T multiply(Number other);
    public T remainder(Number other);

    public default T min(T other) {
	return compareTo(other) > 0 ? other : value();
    }

    public default T max(T other) {
	return compareTo(other) < 0 ? other : value();
    }

    public default boolean less(Number other) {
	return compareTo(other) < 0;
    }

    public default boolean greater(Number other) {
	return compareTo(other) > 0;
    }

    public default boolean lessOrEquals(Number other) {
	return compareTo(other) <= 0;
    }

    public default boolean greaterOrEquals(Number other) {
	return compareTo(other) >= 0;
    }

    public default boolean equals(Number other) {
	return compareTo(other) == 0;
    }

    public default boolean notEquals(Number other) {
	return compareTo(other) != 0;
    }

}
