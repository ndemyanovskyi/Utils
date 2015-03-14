/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.time;

import com.ndemyanovskyi.collection.list.InvertedList;
import com.ndemyanovskyi.iterator.CombinedIterator;
import com.ndemyanovskyi.listiterator.ListIterators;
import static com.ndemyanovskyi.time.Period.EMPTY;
import static com.ndemyanovskyi.util.Compare.greater;
import static com.ndemyanovskyi.util.Compare.greaterOrEquals;
import static com.ndemyanovskyi.util.Compare.less;
import static com.ndemyanovskyi.util.Compare.lessOrEquals;
import static com.ndemyanovskyi.util.Compare.max;
import static com.ndemyanovskyi.util.Compare.min;
import com.ndemyanovskyi.util.Unmodifiable;
import java.time.LocalDate;
import static java.time.LocalDate.MAX;
import static java.time.LocalDate.MIN;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 *
 * @author Назарій
 */
public class DiscontinuousPeriod extends Period {

    private final List<ContinuousPeriod> periods;
    private List<ContinuousPeriod> invertedPeriods;
    
    private final LocalDate first, last;
    private int size;

    //Optional
    private List<ContinuousPeriod> excludedPeriods;
    private int hash;

    DiscontinuousPeriod(Builder builder) {
        List<ContinuousPeriod> modPeriods = new ArrayList<>();
        for(Range range : builder.getModifiableRanges()) {
            modPeriods.add(range.toPeriod());
        }
        this.periods = Unmodifiable.list(modPeriods);
        this.first = modPeriods.get(0).first();
        this.last = modPeriods.get(modPeriods.size() - 1).last();
    }

    @Override
    public boolean contains(Object o) {
        if(o == null || !(o instanceof LocalDate)) return false;
        LocalDate date = (LocalDate) o;
        
        if(greaterOrEquals(date, first()) && lessOrEquals(date, last())) {
            for(ContinuousPeriod period : periods) {
                if(period.contains(o)) return true;
            }
        }
        return false;
    }

    @Override
    public boolean isContinuous() {
        return false;
    }

    @Override
    public List<ContinuousPeriod> getIncluded() {
        return periods;
    }

    private List<ContinuousPeriod> getInvertedIncludedPeriods() {
        return invertedPeriods != null ? invertedPeriods : (invertedPeriods = new InvertedList<>(periods));
    }

    @Override
    public int hashCode() {
        if(hash == 0) {
            hash = 3;
            for(ContinuousPeriod period : periods) {
                hash = 53 * hash + period.hashCode();
            }
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof DiscontinuousPeriod) {
            DiscontinuousPeriod other = (DiscontinuousPeriod) obj;
            return getIncluded().equals(other.getIncluded());
        }
        return false;
    }

    @Override
    public Iterator<LocalDate> iterator() {
        List<Iterator<LocalDate>> iterators = new ArrayList<>();
        for(ContinuousPeriod period : getIncluded()) {
            iterators.add(period.iterator());
        }
        return new CombinedIterator<>(iterators);
    }

    @Override
    public final boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        if(size == 0) {
            size = periods.stream().
                    mapToInt(p -> p.size()).sum();
        }
        return size;
    }

    private Period subSetInclusive(LocalDate from, LocalDate to) {
        to = min(to, last());
        from = max(from, first());
        
        if(from.isAfter(to)) return EMPTY;
        if(is(from, to)) return this;

        Period begin = null, end = null;
        int beginIndex = -1, endIndex = -1;
        
        for(int i = 0; i < periods.size(); i++) {
            ContinuousPeriod period = periods.get(i);
            
            //Begin
            if(begin == null && lessOrEquals(from, period.last())) {
                begin = less(to, period.last()) 
                        ? less(from, period.first()) ? EMPTY : of(from, true, to, true)
                        : greater(from, period.first())
                                ? of(from, true, period.last(), true)
                                : period;
                beginIndex = i;
            }

            //End
            if(lessOrEquals(to, period.last())) {
                end = less(to, period.last())
                        ? less(to, period.first()) 
                                ? EMPTY 
                                : of(period.first(), true, to, true)
                        : period;
                endIndex = i;
            }
            if(begin != null && end != null) break;
        }
        Builder builder = builder();
        for(int i = beginIndex + 1; i < endIndex; i++) {
            builder.plusInterval(periods.get(i));
        }
        return builder.plusIntervals(begin, end).build();
    }

    @Override
    public LocalDate first() {
        return first;
    }

    @Override
    public LocalDate last() {
        return last;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("[");
        for(Iterator<ContinuousPeriod> it = getIncluded().iterator(); it.hasNext();) {
            ContinuousPeriod period = it.next();
            b.append(period);
            if(it.hasNext()) b.append("; ");
        }
        return b.append("]").toString();
    }

    @Override
    public boolean crosses(Interval other) {
        Objects.requireNonNull(other, "other");
        
        if(other.isEmpty()) return false;

        if(other instanceof ContinuousPeriod) {
            if(other.crosses(this)) return true;
            for(ContinuousPeriod conPeriod : getIncluded()) {
                if(conPeriod.crosses(other)) return true;
            }
        } else if(other instanceof Period) {
            Period period = (Period) other;
            for(ContinuousPeriod conPeriod : getIncluded()) {
                for(ContinuousPeriod conPeriod1 : period.getIncluded()) {
                    if(conPeriod.crosses(conPeriod1)) return true;
                }
            }
        } 
        
        return containsAny(other);
    }

    @Override
    public List<ContinuousPeriod> getExcluded() {
        if(excludedPeriods == null) {
            List<ContinuousPeriod> data = new ArrayList<>();
            for(int i = 1; i < periods.size(); i++) {
                LocalDate from = periods.get(i - 1).last().plusDays(1);
                LocalDate to = periods.get(i).first().minusDays(1);
                data.add(of(from, true, to, true));
            }
            excludedPeriods = Unmodifiable.list(data);
        }
        return excludedPeriods;
    }

    @Override
    public LocalDate get(int index) {
        checkInBounds(index);

        if(index == 0) return first();
        if(index == size() - 1) return last();
        
        int count = 0;
        for(int i = 0, s = periods.size(); i < s; i++) {
            ContinuousPeriod period = periods.get(i);
            count += period.size();
            if(index < count) {
                return period.get(index - count + period.size());
            }
        }
        throw new AssertionError("Why?");
    }

    @Override
    public int indexOf(Object o) {
        int count = 0;
        for(int i = 0, s = periods.size(); i < s; i++) {
            ContinuousPeriod period = periods.get(i);
            int index = period.indexOf(o);
            if(index != -1) return count + index;
            count += period.size();
        }
        return -1;
    }

    @Override
    public Period subList(int fromInclusive, int toExclusive) {
        checkInBounds(fromInclusive, "fromInclusive");
        checkInBoundsExclusive(toExclusive, "toExclusive");
        if(fromInclusive > toExclusive) {
            throw new IndexOutOfBoundsException("fromInclusive > toExclusive");
        }

        LocalDate from = get(fromInclusive);
        LocalDate to = get(toExclusive - 1);
        return subSetInclusive(from, to);
    }

    @Override
    public ListIterator<LocalDate> listIterator(int index) {
        return ListIterators.combined(
                index, ListIterators.get(periods));
    }

    @Override
    public LocalDate lower(LocalDate e) {
        for(ContinuousPeriod period : getInvertedIncludedPeriods()) {
            LocalDate date = period.lower(e);
            if(date != null) return date;
        }
        return null;
    }

    @Override
    public LocalDate floor(LocalDate e) {
        for(ContinuousPeriod period : getInvertedIncludedPeriods()) {
            LocalDate date = period.floor(e);
            if(date != null) return date;
        }
        return null;
    }

    @Override
    public LocalDate ceiling(LocalDate e) {
        for(ContinuousPeriod period : getIncluded()) {
            LocalDate date = period.ceiling(e);
            if(date != null) return date;
        }
        return null;
    }

    @Override
    public LocalDate higher(LocalDate e) {
        for(ContinuousPeriod period : getIncluded()) {
            LocalDate date = period.higher(e);
            if(date != null) return date;
        }
        return null;
    }

    @Override
    public LocalDate pollFirst() {
        return first();
    }

    @Override
    public LocalDate pollLast() {
        return last();
    }

    @Override
    public Period subSet(LocalDate fromElement, boolean fromInclusive, LocalDate toElement, boolean toInclusive) {
        Objects.requireNonNull(fromElement, "fromElement");
        Objects.requireNonNull(toElement, "toElement");

        long between = DAYS.between(fromElement, toElement);

        if(between == 0 && (!fromInclusive || !toInclusive)) return EMPTY;
        if(between == 1 && !fromInclusive && !toInclusive) return EMPTY;
        if(between < 0) {
            throw new IllegalArgumentException(
                    String.format("fromElement(%s, %s) > toElement(%s, %s)", 
                            fromElement, fromInclusive ? "inclusive" : "exclusive",
                            toElement, toInclusive ? "inclusive" : "exclusive"));
        }
        
        if(!fromInclusive) {
            if(fromElement.equals(MAX)) return EMPTY;
            fromElement = fromElement.plusDays(1);
        }
        if(!toInclusive) {
            if(toElement.equals(MIN)) return EMPTY;
            toElement = toElement.minusDays(1);
        }
        return subSetInclusive(fromElement, toElement);
    }
    
    public Period cross(Period period) {
        Objects.requireNonNull(period, "period");
        
        if(period.isEmpty()) return EMPTY;
        
        if(period instanceof ContinuousPeriod) {
            return period.cross(this);
        }
        
        LocalDate from = max(first(), period.first());
        LocalDate to = min(last(), period.last());

        if(from.isAfter(to)) return EMPTY;

        final Builder builder = builder();

        Period thisExcluded = builder.clear().plusIntervals(subSet(from, true, to, true).getExcluded()).
                build();
        Period periodExcluded = builder.clear().plusIntervals(period.subSet(from, true, to, true).getExcluded()).
                build();

        return builder.clear().plusInterval(this).minusIntervals(thisExcluded, periodExcluded).
                build();
    }

    @Override
    public Period cross(Interval other) {
        Objects.requireNonNull(other, "other");
        
        if(other.isEmpty()) return EMPTY;
        
        if(other instanceof Period) return cross((Period) other);
        
        
        LocalDate from = max(first(), other.first());
        LocalDate to = min(last(), other.last());

        if(from.isAfter(to)) return EMPTY;
        
        if(other.isContinuous()) {
            return subSetInclusive(from, to);
        } 
        
        Builder builder = builder();
        Iterator<ContinuousPeriod> periodIterator = periods.iterator();
        ContinuousPeriod period = periodIterator.next();

        for(LocalDate date : other) {
            while(period.isBefore(date)) {
                if(periodIterator.hasNext()) {
                    period = periodIterator.next();
                }else {
                    return builder.build();
                }
            }
            if(period.isAfter(date)) {
                return builder.build();
            }

            builder.plusDate(date);
        }
        return builder.build();
    }

}
