/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.collection.set;

import com.ndemyanovskyi.util.BiConverter;
import com.ndemyanovskyi.util.Converter;
import com.ndemyanovskyi.collection.ConvertedCollection;
import java.util.Collection;
import java.util.Set;


public class ConvertedSet<F, T> extends ConvertedCollection<F, T> implements Set<T> {
    
    public ConvertedSet(Collection<F> base, Class<T> valueClass, Converter<T, F> to, Converter<F, T> from) {
	super(base, casterFrom(valueClass), BiConverter.of(to, from));
    }
    
    public ConvertedSet(Collection<F> base, Converter<Object, T> caster, Converter<T, F> to, Converter<F, T> from) {
	super(base, caster, BiConverter.of(to, from));
    }
    
    public ConvertedSet(Collection<F> base, Converter<Object, T> caster, BiConverter<T, F> converter) {
        super(base, caster, converter);
    }
    
    public ConvertedSet(Collection<F> base, Class<T> valueClass, BiConverter<T, F> converter) {
	super(base, casterFrom(valueClass), converter);
    }

    @Override
    protected Set<F> base() {
	return (Set<F>) super.base();
    }

}
