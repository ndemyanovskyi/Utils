/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection.set;

import java.util.Set;
import java.util.function.Predicate;

/**
 *
 * @author Назарій
 */
public class Sets {
    
    public static <T> FilteredSet<T> filtered(Set<T> set, Predicate<? super T> predicate) {
        return new FilteredSet<>(set, predicate);
    }
    
}
