/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.time;

import com.ndemyanovskyi.collection.list.DefaultList;
import static com.ndemyanovskyi.util.Compare.lessOrEquals;
import static com.ndemyanovskyi.util.Compare.max;
import static com.ndemyanovskyi.util.Compare.min;
import com.ndemyanovskyi.util.Unmodifiable;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.Spliterator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

/**
 *
 * @author Назарій
 */
public interface Interval extends NavigableSet<LocalDate>, DefaultList<LocalDate> {
    
    public static final Comparator<LocalDate> COMPARATOR = LocalDate::compareTo;

    @Override
    public default <T> T[] toArray(T[] a) {
        return DefaultList.super.toArray(a);
    }

    @Override
    public default boolean containsAll(Collection<?> c) {
        return DefaultList.super.containsAll(c);
    }

    @Override
    public default boolean contains(Object o) {
        return DefaultList.super.contains(o);
    }

    @Override
    public default boolean isEmpty() {
        return DefaultList.super.isEmpty();
    }

    @Override
    public default LocalDate[] toArray() {
        LocalDate[] array = new LocalDate[size()];
        int index = 0;
        for(LocalDate date : this) {
            array[index++] = date; 
        }
        return array;
    }
    
    public List<? extends Interval> getExcluded();
    public List<? extends Interval> getIncluded();
    public Interval inverted();
    
    public boolean isContinuous();
    
    public default boolean isDiscontinuous() {
        return !isContinuous();
    }
    
    public default LocalDate round(LocalDate date) {
        if(isEmpty()) return null;
        if(last().isBefore(date)) return last();
        if(first().isAfter(date)) return first();
        if(isContinuous()) return date;
        
        LocalDate floor = floor(date);
        if(floor.isEqual(date)) return date;
        LocalDate ceiling = ceiling(date);
        if(ceiling.isEqual(date)) return date;
        long betweenFloor = DAYS.between(floor, date);
        long betweenCeiling = DAYS.between(date, ceiling);
        return betweenFloor < betweenCeiling ? floor : ceiling;
    }
    
    public default boolean crosses(Interval other) {
        Objects.requireNonNull(other, "other");
        
        if(isEmpty() || other.isEmpty()) return false;
        
        LocalDate from = max(first(), other.first());
        LocalDate to = min(last(), other.last());

        if(from.isAfter(to)) return false;
        
        if(isContinuous() || other.isContinuous()) {
            return lessOrEquals(other.ceiling(from), to);
        } else {
            return subSet(from, true, to, true).
                    containsAny(other.subSet(from, true, to, true));
        }
    }
    
    public default Interval plus(Interval other) {
        return plus((Collection<LocalDate>) other);
    }
    
    public default Interval minus(Interval other) {
        return minus((Collection<LocalDate>) other);
    }
    
    public default Interval plus(LocalDate date) {
        return plus(Unmodifiable.collection(date));
    }
    
    public default Interval minus(LocalDate date) {
        return minus(Unmodifiable.collection(date));
    }
    
    public Interval plus(Collection<? extends LocalDate> date);
    public Interval minus(Collection<? extends LocalDate> date);
    
    public Interval cross(Interval other);

    public default boolean contentEquals(Interval other) {
        if(isEmpty()) return other.isEmpty();
        if(size() != other.size()) return false;
        if(isContinuous() != other.isContinuous()) return false;
                
        if(isContinuous()) {
            return is(other.first(), other.last());
        } 
        
        List<? extends Interval> included = getIncluded();
        List<? extends Interval> otherIncluded = other.getIncluded();
        if(included.size() != otherIncluded.size()) return false;
        for(int i = 0; i < included.size(); i++) {
            Interval interval = included.get(i);
            Interval otherInterval = otherIncluded.get(i);
            if(!interval.contentEquals(otherInterval)) {
                return false;
            }
        }
        return true;
    }
    
    public default boolean containsAny(Collection<?> c) {
        if(c instanceof Period) {
            return crosses((Interval) c);
        }
        
        for(Object o : c) {
            if(contains(o)) return true;
        }
        return false;
    }

    @Override
    public default Comparator<LocalDate> comparator() {
	return COMPARATOR;
    }
    
    public default long betweenFirst(ChronoUnit unit, LocalDate date) {
        Objects.requireNonNull(unit, "unit");
        Objects.requireNonNull(date, "date");
        return unit.between(first(), date);
    }
    
    public default long betweenLast(ChronoUnit unit, LocalDate date) {
        Objects.requireNonNull(unit, "unit");
        Objects.requireNonNull(date, "date");
        return unit.between(last(), date);
    }
    
    public default boolean is(LocalDate first, LocalDate last) {
        Objects.requireNonNull(first, "first");
        Objects.requireNonNull(last, "last");
        if(isEmpty()) return false;
        return first().equals(first) && last().equals(last);
    }

    @Override
    public default int lastIndexOf(Object o) {
        return indexOf(o);
    }
    
    public default boolean isAfter(Interval other) {
        Objects.requireNonNull(other, "other");
        return first().isAfter(other.last());
    }
    
    public default boolean isBefore(Interval other) {
        Objects.requireNonNull(other, "other");
        return last().isBefore(other.first());
    }
    
    public default boolean isAfter(ChronoLocalDate date) {
        Objects.requireNonNull(date, "date");
        return first().isAfter(date);
    }
    
    public default boolean isBefore(ChronoLocalDate date) {
        Objects.requireNonNull(date, "date");
        return last().isBefore(date);
    }

    @Override
    public default Interval subSet(LocalDate fromInclusive, LocalDate toExclusive) {
        return subSet(fromInclusive, true, toExclusive, false);
    }

    @Override
    public default Interval tailSet(LocalDate fromInclusive) {
        return tailSet(fromInclusive, true);
    }
    
    @Override
    public default Interval headSet(LocalDate toExclusive) {
        return headSet(toExclusive, false);
    }

    @Override
    public abstract Interval subList(int fromInclusive, int toExclusive);

    @Override
    public abstract Interval tailSet(LocalDate fromElement, boolean inclusive);

    @Override
    public abstract Interval headSet(LocalDate toElement, boolean inclusive);
    
    @Override
    public abstract Interval subSet(LocalDate fromElement, boolean fromInclusive, LocalDate toElement, boolean toInclusive);
    
    @Override
    public default Iterator<LocalDate> iterator() {
        return listIterator();
    }

    @Override
    public default Iterator<LocalDate> descendingIterator() {
        return descendingSet().iterator();
    }
    
    @Override
    public default ListIterator<LocalDate> listIterator() {
        return listIterator(0);
    }

    @Override
    public default Spliterator<LocalDate> spliterator() {
        return DefaultList.super.spliterator();
    }

    //<editor-fold defaultstate="collapsed" desc="Unsupported methods by default">
    @Override
    public default boolean add(LocalDate e) {
        throw new UnsupportedOperationException("add");
    }
    
    @Override
    public default boolean remove(Object o) {
        throw new UnsupportedOperationException("remove");
    }
    
    @Override
    public default boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("retainAll");
    }
    
    @Override
    public default boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("removeAll");
    }
    
    @Override
    public default boolean addAll(Collection<? extends LocalDate> c) {
        throw new UnsupportedOperationException("removeAll");
    }

    @Override
    public default void clear() {
        throw new UnsupportedOperationException("clear");
    }

    @Override
    public default void sort(Comparator<? super LocalDate> c) {
        throw new UnsupportedOperationException("sort");
    }

    @Override
    public default LocalDate set(int index, LocalDate element) {
        throw new UnsupportedOperationException("set");
    }

    @Override
    public default void replaceAll(UnaryOperator<LocalDate> operator) {
        throw new UnsupportedOperationException("replaceAll");
    }

    @Override
    public default boolean removeIf(Predicate<? super LocalDate> filter) {
        throw new UnsupportedOperationException("removeIf");
    }

    @Override
    public default LocalDate remove(int index) {
        throw new UnsupportedOperationException("remove");
    }
    //</editor-fold>
    
}
