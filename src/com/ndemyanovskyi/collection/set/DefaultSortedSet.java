/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection.set;

import com.ndemyanovskyi.collection.DefaultCollection;
import java.util.Objects;
import java.util.SortedSet;

public interface DefaultSortedSet<E> extends DefaultSet<E>, SortedSet<E> {
    
    public static String toString(SortedSet<?> set) {
	return DefaultCollection.toString(set);
    }
    
    public static int hashCode(SortedSet<?> set) {
	return DefaultCollection.hashCode(set);
    }
    
    public static boolean equals(SortedSet<?> set, Object object) {
        Objects.requireNonNull(set, "set");
	return object instanceof SortedSet 
                && DefaultCollection.equals(set, object);
    }
	

}
