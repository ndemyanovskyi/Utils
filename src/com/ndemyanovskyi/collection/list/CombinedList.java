/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.collection.list;

import com.ndemyanovskyi.collection.Collections;
import com.ndemyanovskyi.collection.CombinedCollection;
import com.ndemyanovskyi.listiterator.ListIterators;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 *
 * @author nazar_000
 */
public interface CombinedList<E> extends CombinedCollection<E>, DefaultList<E> {

    @Override
    default public int size() {
        return Collections.forEach(
                collections(), 0, (list, size) -> size + list.size());
    }

    @Override
    default public Iterator<E> iterator() {
        return CombinedCollection.super.iterator();
    }

    @Override
    default public boolean add(E o) {
        add(size(), o);
        return true;
    }

    @Override
    default public boolean addAll(int index, Collection<? extends E> c) {
        for(E o : c) add(index++, o);
        return true;
    }

    @Override
    default public E get(int index) {
        return action(index, (pos, list) -> ((List<E>) list).get(pos));
    }
    
    @Override
    default public <R> R action(int index, Predicate<Integer> condition, BiFunction<Integer, Collection<E>, R> action) {
        Objects.requireNonNull(condition, "\"condition\" can`t be null.");        
        Objects.requireNonNull(action, "\"action\" can`t be null.");
        
        int offset = 0;
        if(collections() instanceof List && collections() instanceof RandomAccess) {
            List<List<E>> lists = (List<List<E>>) collections();
            for(int i = 0; i < lists.size(); i++) {
                List<E> list = lists.get(i);
                int size = list.size();
                offset += size;
                if(condition.test(offset)) {
                    return action.apply(index - (offset - size), list); 
                }
            }
        } else {
            Iterator<? extends List<E>> it = collections().iterator();
            while(it.hasNext()) {
                List<E> list = it.next();
                int size = list.size();
                offset += size;
                if(condition.test(offset)) {
                    return action.apply(index - (offset - size), list); 
                }
            }
        }

        throw new IndexOutOfBoundsException(
                "Index = " + index + ", " +
                        "Bounds = [" + 0 + ", " + offset + "].");
    }

    @Override
    default public E set(int index, E element) {
        return action(index, (pos, list) -> ((List<E>) list).set(pos, element));
    }

    default public void addAsLast(int index, E element) {
        action(index, offset -> index <= offset, 
                (pos, list) -> { ((List<E>) list).add(pos, element); return null; });
    }

    default public void addAsFirst(int index, E element) {
        action(index, offset -> index < offset, 
                (pos, list) -> { ((List<E>) list).add(pos, element); return null; });
    }

    @Override
    default public void add(int index, E element) {
        addAsLast(index, element);
    }

    @Override
    default public E remove(int index) {
        return action(index, (pos, list) -> ((List<E>) list).remove((int) pos));
    }
    
    @Override
    default public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    default public ListIterator<E> listIterator(int index) {
        return ListIterators.combined(index, ListIterators.get(collections()));
    }

    @Override
    public Collection<? extends List<E>> collections();

    @Override
    default public void forEach(Consumer<? super E> action) {
        Collections.forEach(collections(), 
                list -> Collections.forEach(list, action));
    }

    @Override
    default public int indexOf(Object o) {
        if (o == null) {
            int index = 0;
            for (E value : this) {
                if (value == null) return index;
                index++;
            }
        } else {
            int index = 0;
            for (E value : this) {
                if(o.equals(value)) return index;
                index++;
            }
        }
        
        return -1;
    }

    @Override
    default public int lastIndexOf(Object o) {
        if (o == null) {
            int index = 0;
            for (E value : this) {
                if (value == null) return index;
                index++;
            }
        } else {
            int index = 0;
            for (E value : this) {
                if(o.equals(value)) return index;
                index++;
            }
        }
        
        return -1;
    }

    @Override
    default public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("subList");
    }
    
}
