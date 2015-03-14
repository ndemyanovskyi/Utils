/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.time;

import com.ndemyanovskyi.collection.DefaultCollection;
import com.ndemyanovskyi.collection.list.InvertedList;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.SortedSet;

/**
 *
 * @author Назарій
 */
class IntervalDescendingSet extends InvertedList<LocalDate> implements NavigableSet<LocalDate> {
    
    private static final Comparator<LocalDate> COMPARATOR = (a, b) -> b.compareTo(a);

    private final Interval abstractRange;

    public IntervalDescendingSet(Interval abstractRange) {
        super(abstractRange);
        this.abstractRange = Objects.requireNonNull(abstractRange, "abstractRange");
    }

    @Override
    public LocalDate lower(LocalDate e) {
        return abstractRange.higher(e);
    }

    @Override
    public LocalDate floor(LocalDate e) {
        return abstractRange.ceiling(e);
    }

    @Override
    public LocalDate ceiling(LocalDate e) {
        return abstractRange.floor(e);
    }

    @Override
    public LocalDate higher(LocalDate e) {
        return abstractRange.lower(e);
    }

    @Override
    public LocalDate pollFirst() {
        return abstractRange.pollLast();
    }

    @Override
    public LocalDate pollLast() {
        return abstractRange.pollFirst();
    }

    @Override
    public NavigableSet<LocalDate> descendingSet() {
        return abstractRange;
    }

    @Override
    public Iterator<LocalDate> descendingIterator() {
        return abstractRange.iterator();
    }

    @Override
    public NavigableSet<LocalDate> subSet(LocalDate fromElement, boolean fromInclusive, LocalDate toElement, boolean toInclusive) {
        return abstractRange.subSet(toElement, toInclusive, fromElement, fromInclusive).descendingSet();
    }

    @Override
    public NavigableSet<LocalDate> headSet(LocalDate toElement, boolean inclusive) {
        return abstractRange.tailSet(toElement, inclusive).descendingSet();
    }

    @Override
    public NavigableSet<LocalDate> tailSet(LocalDate fromElement, boolean inclusive) {
        return abstractRange.headSet(fromElement, inclusive).descendingSet();
    }

    @Override
    public SortedSet<LocalDate> subSet(LocalDate fromElement, LocalDate toElement) {
        return subSet(fromElement, true, toElement, false);
    }

    @Override
    public SortedSet<LocalDate> headSet(LocalDate toElement) {
        return headSet(toElement, false);
    }

    @Override
    public SortedSet<LocalDate> tailSet(LocalDate fromElement) {
        return tailSet(fromElement, true);
    }

    @Override
    public Comparator<? super LocalDate> comparator() {
        return COMPARATOR;
    }

    @Override
    public LocalDate first() {
        return abstractRange.last();
    }

    @Override
    public LocalDate last() {
        return abstractRange.first();
    }

    @Override
    public String toString() {
        int size = size();
        if(size == 0) return "[]";
        if(size == 1) return "[" + first() + "]";
        
        if(abstractRange instanceof ContinuousPeriod || abstractRange instanceof Range) {
            return String.format("[%s <= %s]", first(), last());
        } else if(abstractRange instanceof Period) {
            Period period = (Period) abstractRange;
            List<ContinuousPeriod> cps = period.getIncluded();
            
            if(cps.size() > 1) {
                StringBuilder b = new StringBuilder();
                b.append("[");
                for(int i = 0; i < cps.size(); i++) {
                    ContinuousPeriod cp = cps.get(i);
                    b.append(cp);
                    if(i < cps.size() - 1) b.append("; ");
                }
                b.append("]");
                return b.toString();
            } else {
                return String.format("[%s <= %s]", period.first(), period.last());
            }
        } else {
            return DefaultCollection.toString(this);
        }
    }

}
