/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection.list.unmodifiable;

import com.ndemyanovskyi.collection.list.DefaultList;
import com.ndemyanovskyi.collection.unmodifiable.UnmodifiableConvertedCollection;
import com.ndemyanovskyi.util.Converter;
import java.util.List;

/**
 *
 * @author Назарій
 */
public class UnmodifiableConvertedList<F, T> extends UnmodifiableConvertedCollection<F, T> implements DefaultList<T> {
    
    public UnmodifiableConvertedList(List<F> list, Converter<F, T> converter) {
        super(list, converter);
    }

    @Override
    protected List<F> base() {
        return (List<F>) super.base();
    }
    
    @Override
    public T get(int index) {
        return converter().to(base().get(index));
    }
    
}
