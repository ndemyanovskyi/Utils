/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.map.unmodifiable;

import com.ndemyanovskyi.map.Pool;
import java.util.function.Function;


public class UnmodifiablePool<K, V> extends UnmodifiableMap<K, V> implements Pool<K, V> {

    public UnmodifiablePool(Pool<K, V> pool) {
	super(pool);
    }

    @Override
    protected Pool<K, V> base() {
	return (Pool<K, V>) super.base();
    }

    @Override
    public Function<K, V> creator() {
	return base().creator();
    }

}
