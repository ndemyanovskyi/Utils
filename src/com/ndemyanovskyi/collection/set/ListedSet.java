/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection.set;

import java.util.List;
import java.util.Set;
import java.util.Spliterator;

public interface ListedSet<T> extends Set<T>, List<T> {

    @Override
    public default Spliterator<T> spliterator() {
        return Set.super.spliterator();
    }
    
}
