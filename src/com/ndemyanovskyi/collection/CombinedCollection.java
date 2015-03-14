/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection;

import com.ndemyanovskyi.iterator.Iterators;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 *
 * @author Nazariy
 */
public interface CombinedCollection<E> extends DefaultCollection<E> {

    @Override
    default public int size() {
        return Collections.forEach(collections(), 0,
                (list, size) -> size + list.size());
    }
    
    default public E action(int index, BiFunction<Integer, Collection<E>, E> action) {
        return action(index, offset -> index < offset, action);
    }

    default public <R> R action(int index, Predicate<Integer> condition, BiFunction<Integer, Collection<E>, R> action) {
        Objects.requireNonNull(condition, "\"condition\" can`t be null.");        
        Objects.requireNonNull(action, "\"action\" can`t be null.");
        
        int offset = 0;
        Iterator<? extends Collection<E>> it = collections().iterator();
        while (it.hasNext()) {
            Collection<E> c = it.next();
            int size = c.size();
            offset += size;
            if (condition.test(offset)) {
                return action.apply(index - (offset - size), c);
            }
        }

        throw new IndexOutOfBoundsException(
                "Index = " + index + ", " +
                        "Bounds = [" + 0 + ", " + offset + "].");
    }

    @Override
    default public Iterator<E> iterator() {
        return Iterators.combined(Iterators.get(collections()));
    }
    
    public Collection<? extends Collection<E>> collections();
    
}
