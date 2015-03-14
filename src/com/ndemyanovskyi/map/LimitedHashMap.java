/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.map;

import java.util.LinkedHashMap;
import java.util.Map.Entry;


public class LimitedHashMap<K, V> extends LinkedHashMap<K, V> {
    
    private int limit;
    
    public LimitedHashMap(int limit) {
	super(limit);
    }

    @Override
    protected boolean removeEldestEntry(Entry<K, V> eldest) {
        return size() > limit;
    }

    public int limit() {
	return limit;
    }

}
