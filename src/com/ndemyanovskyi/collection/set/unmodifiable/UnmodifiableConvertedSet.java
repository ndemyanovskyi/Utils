/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection.set.unmodifiable;

import com.ndemyanovskyi.collection.unmodifiable.UnmodifiableConvertedCollection;
import com.ndemyanovskyi.util.Converter;
import java.util.Set;

/**
 *
 * @author Назарій
 */
public class UnmodifiableConvertedSet<F, T> extends UnmodifiableConvertedCollection<F, T> {

    public UnmodifiableConvertedSet(Set<F> set, Converter<F, T> converter) {
        super(set, converter);
    }
    
}
