/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.iterator;

import com.ndemyanovskyi.util.Converter;
import com.ndemyanovskyi.util.Pair;
import com.ndemyanovskyi.util.Quartet;
import com.ndemyanovskyi.util.Trio;
import com.ndemyanovskyi.util.Unmodifiable;
import com.ndemyanovskyi.iterator.unmodifiable.UnmodifiableConvertedIterator;
import com.ndemyanovskyi.iterator.unmodifiable.UnmodifiableIterator;
import com.ndemyanovskyi.listiterator.DefaultListIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 *
 * @author nazar_000
 */
public final class Iterators {

    private Iterators() {
	throw new AssertionError();
    }

    private static final Iterator EMPTY = new Iterator() {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new IllegalStateException();
        }
        
    };

    public static <T> Iterator<T> empty() {
	return EMPTY;
    }

    public static <T> Iterator<T> unmodifiable(Iterator<T> it) {
	return new UnmodifiableIterator<>(it);
    }
    
    public static <F, T> Iterator<T> converted(Iterator<F> base, Converter<F, T> converter) {
	return new ConvertedIterator<>(base, converter);
    }
    
    public static <F, T> Iterator<T> unmodifiableConverted(Iterator<F> base, Converter<F, T> converter) {
	return new UnmodifiableConvertedIterator<>(base, converter);
    }

    public static <T> Iterator<T> move(int offset, Iterator<T> iterator) {
	Objects.requireNonNull(iterator, "\"iterator\" can`t be null.");
	if (offset < 0) {
	    throw new IllegalArgumentException(
		    "\"offset\" can`t be less then 0.");
	}
	while (offset > 0) {
	    iterator.next();
	    offset--;
	}
	return iterator;
    }

    public static <T> ListIterator<T> move(int offset, ListIterator<T> iterator) {
	Objects.requireNonNull(iterator, "\"iterator\" can`t be null.");
	while (offset > 0) {
	    iterator.next();
	    offset--;
	}
	while (offset < 0) {
	    iterator.previous();
	    offset++;
	}
	return iterator;
    }

    public static <T> ListIterator<T> moveToFirst(ListIterator<T> it) {
	while (it.hasPrevious()) {
	    it.previous();
	}
	return it;
    }

    public static <T extends Iterator<?>> T moveToLast(T it) {
	while (it.hasNext()) {
	    it.next();
	}
	return it;
    }

    public static <T> ListIterator<T> forEach(ListIterator<T> it, Consumer<? super T> c) {
	int offset = it.nextIndex();
	move(-offset, it).forEachRemaining(c);
	return move(offset, it);
    }

    public static <T1, T2> Iterator<Pair<T1, T2>> parallel(Iterator<T1> first, Iterator<T2> second) {
	Iterator it = parallel(first, second, first, first);
	return (Iterator<Pair<T1, T2>>) it;
    }

    public static <T1, T2, T3> Iterator<Trio<T1, T2, T3>> parallel(Iterator<T1> first,
	    Iterator<T2> second, Iterator<T3> third) {
	Iterator it = parallel(first, second, third, first);
	return (Iterator<Trio<T1, T2, T3>>) it;
    }

    public static <T1, T2, T3, T4> Iterator<Quartet<T1, T2, T3, T4>> parallel(Iterator<T1> first,
	    Iterator<T2> second, Iterator<T3> third, Iterator<T4> fourth) {
	return new ParallelIterator<>(first, second, third, fourth);
    }

    public static <T> List<Iterator<T>> get(Iterable<T>... iterables) {
	Objects.requireNonNull(iterables, "iterables");
        
        if(iterables.length == 0) return Unmodifiable.list();
        
        List<Iterator<T>> iterators = new ArrayList<>();
        for(Iterable<T> list : iterables) {
            iterators.add(list.iterator());
        }
	return iterators;
    }

    public static <T> List<Iterator<T>> get(Collection<? extends Iterable<T>> iterables) {
	Objects.requireNonNull(iterables, "iterables");
        
        if(iterables.isEmpty()) return Unmodifiable.list();
        
        List<Iterator<T>> iterators = new ArrayList<>();
        for(Iterable<T> list : iterables) {
            iterators.add(list.iterator());
        }
	return iterators;
    }

    public static <T> Iterator<T> singleton(T element) {
	return new SingletonIterator(element);
    }

    public static <T> Iterator<T> combined(Iterator<T>... iterators) {
	if (iterators.length == 0) {
	    return empty();
	}

	return combined(Arrays.asList(iterators));
    }

    public static <T> Iterator<T> combined(Collection<? extends Iterator<T>> c) {
	if (c.isEmpty()) {
	    return empty();
	}
        
	return new CombinedIterator<>(c);
    }

    public static <T> Iterator<T> of(List<T> list) {
	return of(0, list);
    }

    public static <T> Iterator<T> of(int start, List<T> list) {
	return new DefaultListIterator<>(start, list);
    }

    public static <T> Iterator<T> of(int fromInclusive, int toExclusive, List<T> list) {
	return of(fromInclusive, toExclusive, 0, list);
    }

    public static <T> Iterator<T> of(int fromInclusive, int toExclusive, int start, List<T> list) {
	return new DefaultListIterator<>(fromInclusive, toExclusive, start, list);
    }

    public static <T> Iterator<T> filtered(Iterator<T> base, Predicate<? super T> condition) {
	return new FilteredIterator<>(base, condition);
    }
    
}
