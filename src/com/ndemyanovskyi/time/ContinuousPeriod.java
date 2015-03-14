/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.time;

import com.ndemyanovskyi.listiterator.ListIterators;
import com.ndemyanovskyi.map.Pool;
import com.ndemyanovskyi.map.WeakHashPool;
import com.ndemyanovskyi.map.unmodifiable.UnmodifiablePoolWrapper;
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
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 *
 * @author Назарій
 */
public class ContinuousPeriod extends Period {
    
    private final LocalDate first, last;
    private final int size;
    
    //Optional
    private List<ContinuousPeriod> continuousPeriods;
    private UnmodifiablePoolWrapper<Long, LocalDate> datePool;   
    private int hash;

    ContinuousPeriod() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    ContinuousPeriod(LocalDate first, LocalDate last) {
	this.first = Objects.requireNonNull(first, "first");
	this.last = Objects.requireNonNull(last, "last");
	this.size = (int) DAYS.between(first, last) + 1;
	
	if(first.isAfter(last)) {
	    throw new IllegalArgumentException(
                    String.format("first(%s) > last(%s)", first, last));
	}
    }

    @Override
    public Period cross(Interval other) {
        if(isEmpty() || other.isEmpty()) {
            return EMPTY;
        }
        
        if(other instanceof Period) {
            return ((Period) other).subSet(first(), true, last(), true);
        }

        LocalDate from = max(first(), other.first());
        LocalDate to = min(last(), other.last());
        return !from.isAfter(to)
                ? of(other.subSet(first(), true, last(), true))
                : EMPTY;
    }

    public ContinuousPeriod cross(ContinuousPeriod other) {
        return other.subSet(first(), true, last(), true);
    }

    @Override
    public boolean contentEquals(Interval other) {
        throw new UnsupportedOperationException("contentEquals");
    }
    
    static ContinuousPeriod of(LocalDate fromElement, boolean fromInclusive, 
            LocalDate toElement, boolean toInclusive, Map<Long, LocalDate> cache) {
        ContinuousPeriod period = of(fromElement, fromInclusive, toElement, toInclusive);
        
        long from = fromElement.toEpochDay();
        long to = toElement.toEpochDay();
        
        /*cache.entrySet().parallelStream().forEach(e -> {
            Long key = e.getKey();
            LocalDate value = e.getValue();
            if((fromInclusive ? key > from : key > from + 1) 
                    && (toInclusive ? key < to : key < to - 1)) {
                period.getModifiableDatePool().put(key, value);
            }
        });*/
        return period;
    }

    @Override
    public int size() {
	return size;
    }

    @Override
    public boolean isContinuous() {
        return true;
    }

    @Override
    public boolean contains(Object o) {
	if(isEmpty() || !(o instanceof LocalDate)) return false;
	LocalDate date = (LocalDate) o;
	return greaterOrEquals(date, first()) 
                && lessOrEquals(date, last());
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if(c instanceof Period) {
            Period p = (Period) c;
            if(!isEmpty() && !p.isEmpty()) {
                return greaterOrEquals(p.first(), first())
                        && lessOrEquals(p.last(), last());
            }
        }
        return super.containsAll(c);
    }

    @Override
    public final List<ContinuousPeriod> getIncluded() {
        return continuousPeriods != null 
                ? continuousPeriods 
                : (continuousPeriods = Unmodifiable.
                        list(new ContinuousPeriod[] { this }));
    }

    @Override
    public String toString() {
        switch(size()) {
            case 0: return "[]";
            case 1: return "[" + first() + "]";
            default: return String.format("[%s => %s]", first(), last());
        }
    }

    @Override
    public LocalDate first() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
	return first;
    }

    @Override
    public LocalDate last() {
        if(isEmpty()) {
            throw new NoSuchElementException();
        }
	return last;
    }

    @Override
    public int hashCode() {
        if(hash == 0) {
            int temp = 3;
            temp = 53 * temp + Objects.hashCode(this.first);
            temp = 53 * temp + Objects.hashCode(this.last);
            hash = temp;
        }
	return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ContinuousPeriod) {
            ContinuousPeriod other = (ContinuousPeriod) obj;
            if(isEmpty()) {
                return other.isEmpty();
            } else {
                return !other.isEmpty()
                        ? first().equals(other.first()) && last().equals(other.last())
                        : false;
            }
        } 
        return false;
    }
    
    private UnmodifiablePoolWrapper<Long, LocalDate> getModifiableDatePool() {
        if(datePool == null) {
            datePool = new UnmodifiablePoolWrapper<>(
                    new WeakHashPool<>(Long.class, LocalDate::ofEpochDay));
        }
        return datePool;
    }
    
    public Pool<Long, LocalDate> getDatePool() {
        return getModifiableDatePool().unmodifiable();
    }
    
    public boolean isDatePoolInitialized() {
        return datePool != null;
    }

    @Override
    public List<ContinuousPeriod> getExcluded() {
        return Unmodifiable.list();
    }

    @Override
    public LocalDate get(int index) {
        checkInBounds(index);
        if(index == 0) return first();
        if(index == size() - 1) return last();
        return getDatePool().get(first().toEpochDay() + index);
    }

    @Override
    public int indexOf(Object o) {
        if(isEmpty()) return -1;
        
        if(o != null && o instanceof LocalDate) {
            LocalDate date = (LocalDate) o;
            long between = DAYS.between(first(), date);
            if(inBounds(between)) {
                return (int) between;
            }
        }
        return -1;
    }

    @Override
    public ContinuousPeriod subList(int fromInclusive, int toExclusive) {
        checkInBounds(fromInclusive, "fromInclusive");
        checkInBoundsExclusive(toExclusive, "toExclusive");
        if(fromInclusive > toExclusive) {
            throw new IndexOutOfBoundsException("fromInclusive > toExclusive");
        }
        
        if(fromInclusive == toExclusive) return EMPTY;
        
        return (fromInclusive != 0 || toExclusive != size)
                ? of(get(fromInclusive), true, get(toExclusive - 1), true, datePool)
                : this;
    }

    @Override
    public ListIterator<LocalDate> listIterator(int index) {
        return ListIterators.of(index, this);
    }
    
    public static void main(String[] args) {
        LocalDate date = LocalDate.now();
        LocalDate date1 = LocalDate.now().plusDays(2);
        LocalDate date2 = LocalDate.now().plusDays(3);
        LocalDate date3 = LocalDate.now().plusDays(4);
        Period p = builder().plusDates(date, date2, date1, date3).build();
        System.out.println(date2 == p.get(2));
    }

    @Override
    public LocalDate lower(LocalDate e) {
        if(isEmpty() || lessOrEquals(e, first())) return null;
        if(greater(e, last())) return last();
        return getDatePool().get(e.toEpochDay() - 1);
    }

    @Override
    public LocalDate floor(LocalDate e) {
        if(isEmpty() || less(e, first())) return null;
        if(greater(e, last())) return last();
        return e;
    }

    @Override
    public LocalDate ceiling(LocalDate e) {
        if(isEmpty() || greater(e, last())) return null;
        if(less(e, first())) return first();
        return e;
    }

    @Override
    public LocalDate higher(LocalDate e) {
        if(isEmpty() || greaterOrEquals(e, last())) return null;
        if(less(e, first())) return first();
        return getDatePool().get(e.toEpochDay() + 1);
    }

    @Override
    public LocalDate pollFirst() {
        return isEmpty() ? null : first();
    }

    @Override
    public LocalDate pollLast() {
        return isEmpty() ? null : last();
    }

    @Override
    public ContinuousPeriod subSet(LocalDate fromInclusive, LocalDate toExclusive) {
        return (ContinuousPeriod) super.subSet(fromInclusive, toExclusive);
    }

    @Override
    public ContinuousPeriod headSet(LocalDate toElement, boolean inclusive) {
        return (ContinuousPeriod) super.headSet(toElement, inclusive);
    }

    @Override
    public ContinuousPeriod headSet(LocalDate toExclusive) {
        return (ContinuousPeriod) super.headSet(toExclusive);
    }

    @Override
    public ContinuousPeriod tailSet(LocalDate fromElement, boolean inclusive) {
        return (ContinuousPeriod) super.tailSet(fromElement, inclusive);
    }

    @Override
    public ContinuousPeriod tailSet(LocalDate fromInclusive) {
        return (ContinuousPeriod) super.tailSet(fromInclusive);
    }

    @Override
    public ContinuousPeriod subSet(LocalDate fromElement, boolean fromInclusive, LocalDate toElement, boolean toInclusive) {
        Objects.requireNonNull(fromElement, "fromElement");
        Objects.requireNonNull(toElement, "toElement");
        
        long between = DAYS.between(fromElement, toElement);
        
        if(between == 0 && !(fromInclusive && toInclusive)) return EMPTY;
        if(between == 1 && !fromInclusive && !toInclusive) return EMPTY;
        if(between < 0) {
            throw new IllegalArgumentException("fromElement > toElement");
        }
        
        if(!fromInclusive) {
            if(fromElement.equals(MAX)) return EMPTY;
            fromElement = fromElement.plusDays(1);
        }
        if(!toInclusive) {
            if(toElement.equals(MIN)) return EMPTY;
            toElement = toElement.minusDays(1);
        }
        
        LocalDate from = max(first(), fromElement);
        LocalDate to = min(last(), toElement);
        return !from.isAfter(to)
                ? is(from, to) ? this : of(from, true, to, true, datePool) 
                : EMPTY;  
    }
    
}