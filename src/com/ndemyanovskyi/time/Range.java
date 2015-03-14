/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.time;

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
import java.util.Objects;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author Назарій
 */
public class Range extends AbstractInterval implements Unmodifiable.Wrapper<Range>, Cloneable {

    private ObjectProperty<LocalDate> first;
    private ObjectProperty<LocalDate> last;
    
    private ContinuousPeriod period;
    
    private Range unmodifiable;
    
    private UnmodifiablePoolWrapper<Long, LocalDate> datePool;  

    //Specially for UnmodifiableRange
    private Range() {
        
    }

    public Range(LocalDate first, LocalDate last) {
        set(first, last);
    }
    
    public static void main(String[] args) {
        Range r = new Range(MIN.plusDays(3), MIN.plusDays(3));
        r.firstProperty().addListener(p -> System.out.println(p));
        r.set(MIN.plusDays(2), MIN.plusDays(4));
        System.out.println(r);
    }

    protected Range(LocalDate first, LocalDate last, Map<Long, LocalDate> cache) {
        set(first, last);
        getModifiableDatePool().putAll(cache);
    }

    public Range(ContinuousPeriod period) {
        set(period);
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
    public final boolean isContinuous() {
        return true;
    }

    public ObjectProperty<LocalDate> firstProperty() {
        if(first == null) {
            first = new SimpleObjectProperty<LocalDate>(this, "first") {

                @Override
                public void set(LocalDate first) {
                    Objects.requireNonNull(first, "first");

                    LocalDate oldFirst = get();
                    
                    if(oldFirst == null || !oldFirst.equals(first)) {
                        LocalDate last = lastProperty().get();
                        if(last != null) check(first, last);
                        super.set(first);

                        if(period != null
                                && !period.first().equals(first)) {
                            period = null;
                        }
                    }
                }

            };
        }
        return first;
    } 

    public ObjectProperty<LocalDate> lastProperty() {
        if(last == null) {
            last = new SimpleObjectProperty<LocalDate>(this, "first") {

                @Override
                public void set(LocalDate last) {
                    Objects.requireNonNull(last, "last");

                    LocalDate oldLast = get();
                    
                    if(oldLast == null || !oldLast.equals(last)) {
                        LocalDate first = firstProperty().get();
                        if(first != null) check(first, last);
                        super.set(last);

                        if(period != null
                                && !period.last().equals(last)) {
                            period = null;
                        }
                    }
                }

            };
            
        }
        return last;
    } 

    @Override
    public Range clone() {
        try {
            Range clone = (Range) super.clone();
            clone.first = null;
            clone.last = null;
            clone.unmodifiable = null;
            return clone;
        }catch(CloneNotSupportedException ex) {
            throw new InternalError(ex);
        }
    }

    public void set(LocalDate first, LocalDate last) {  
        Objects.requireNonNull(first, "first");
        Objects.requireNonNull(last, "last"); 
        check(first, last);
        
        LocalDate thisFirst = first();
        LocalDate thisLast = last();
        
        if(thisFirst == null || thisLast == null) {
            firstProperty().set(first);
            lastProperty().set(last);
        } else {
            if(first.isAfter(thisLast)) {
                lastProperty().set(last);
                firstProperty().set(first);
            } else {
                firstProperty().set(first);
                lastProperty().set(last);
            }
        }
    }

    public void set(ContinuousPeriod period) {
        Objects.requireNonNull(period, "period");
        set(period.first(), period.last());
        this.period = period;
        
        if(period.isDatePoolInitialized()) {
            getModifiableDatePool().putAll(period.getDatePool());
        }
    }

    @Override
    public Range modifiable() {
        return this;
    }

    @Override
    public Range unmodifiable() {
        return unmodifiable != null ? unmodifiable : 
                (unmodifiable = new UnmodifiableRange());
    }

    @Override
    public Interval cross(Interval other) {
        return other.subSet(first(), true, last(), true);
    }

    public Period cross(Period other) {
        return other.subSet(first(), true, last(), true);
    }

    public Range cross(Range other) {
        return other.subSet(first(), true, last(), true);
    }

    @Override
    public boolean contentEquals(Interval other) {
        throw new UnsupportedOperationException("contentEquals");
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
    public final boolean isEmpty() {
        return false;
    }

    @Override
    public Range subList(int fromInclusive, int toExclusive) {
        checkInBounds(fromInclusive, "fromInclusive");
        checkInBoundsExclusive(toExclusive, "toExclusive");
        
        if(fromInclusive == toExclusive) {
            throw new IllegalArgumentException("fromInclusive == toExclusive");
        }
        
        return new Range(get(fromInclusive), get(toExclusive - 1));
    }

    @Override
    public Range tailSet(LocalDate fromElement, boolean inclusive) {
        return subSet(fromElement, inclusive, last(), true);
    }

    @Override
    public Range headSet(LocalDate toElement, boolean inclusive) {
        return subSet(first(), true, toElement, inclusive);
    }

    @Override
    public Range subSet(LocalDate fromElement, boolean fromInclusive, LocalDate toElement, boolean toInclusive) {
        Objects.requireNonNull(fromElement, "fromElement");
        Objects.requireNonNull(toElement, "toElement");
        
        long between = DAYS.between(fromElement, toElement);
        
        if(between == 0 && !(fromInclusive && toInclusive)) {
            throw new IllegalArgumentException(
                    String.format("fromElement(%s) == toElement(%s)", 
                            fromInclusive ? "inclusive" : "exclusive",
                            toInclusive ? "inclusive" : "exclusive"));
        }
        if(between == 1 && !fromInclusive && !toInclusive)  {
            throw new IllegalArgumentException(
                    "fromElement(exclusive) less then toElement(exclusive) by 1 day.");
        }
        if(between < 0) {
            throw new IllegalArgumentException("fromElement > toElement");
        }
        
        if(!fromInclusive) {
            if(fromElement.equals(MAX)) {
                throw new IllegalArgumentException(
                        "fromElement(exclusive) == MAX");
            }
            fromElement = fromElement.plusDays(1);
        }
        if(!toInclusive) {
            if(toElement.equals(MIN)) {
                throw new IllegalArgumentException(
                        "toElement(exclusive) == MIN");
            }
            toElement = toElement.minusDays(1);
        }
        
        LocalDate from = max(first(), fromElement);
        LocalDate to = min(last(), toElement);
        
        if(from.isAfter(last())) {
            throw new IllegalArgumentException("fromElement > last");
        }
        if(to.isBefore(first())) {
            throw new IllegalArgumentException("toElement < first");
        }
        
        return new Range(from, to);
    }

    @Override
    public ListIterator<LocalDate> listIterator(int index) {
        throw new UnsupportedOperationException("listIterator");
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
        return first();
    }

    @Override
    public LocalDate pollLast() {
        return last();
    }

    @Override
    public int size() {
        return (int) DAYS.between(first(), last()) + 1;
    }

    @Override
    public List<? extends Interval> getExcluded() {
        return Unmodifiable.list();
    }
    
    private List<? extends Range> included;

    @Override
    public List<? extends Range> getIncluded() {
        return included != null ? included : 
                (included = Unmodifiable.<Range>list(this));
    }

    @Override
    public Interval inverted() {
        throw new UnsupportedOperationException("inverted");
    }

    @Override
    public Interval plus(Collection<? extends LocalDate> date) {
        throw new UnsupportedOperationException("plus");
    }

    @Override
    public Interval minus(Collection<? extends LocalDate> date) {
        throw new UnsupportedOperationException("minus");
    }

    static void check(LocalDate first, LocalDate second) {
        if(first.isAfter(second)) {
            throw new IllegalArgumentException("first > second");
        }
    }

    public void setFirst(LocalDate first) {
        firstProperty().set(first);
    }

    public void setLast(LocalDate last) {
        lastProperty().set(last);
    }
    
    public boolean is(Period period) {
        return is(period.first(), period.last());
    }
    
    public boolean is(Range range) {
        return is(range.first(), range.last());
    }

    @Override
    public LocalDate last() {
        return lastProperty().get();
    }

    @Override
    public LocalDate first() {
        return firstProperty().get();
    }
    
    protected ContinuousPeriod createPeriod(LocalDate first, LocalDate last) {
        return Period.of(first(), true, last(), true);
    }

    protected ContinuousPeriod getPeriod() {
        return period;
    }

    public ContinuousPeriod toPeriod() {
        return period != null ? period : 
                (period = createPeriod(first(), last()));
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash = 31 * hash + first().hashCode();
        hash = 31 * hash + last().hashCode();
        return hash;
    }

    @Override
    public String toString() {
        switch(size()) {
            case 1: return "[" + first() + "]";
            default: return String.format("[%s => %s]", first(), last());
        }
    }

    @Override
    public boolean equals(Object o) {
        return o != null && o instanceof Range && is((Range) o);
    }

    //<editor-fold defaultstate="collapsed" desc="Unsupported methods">
    @Override
    public final boolean add(LocalDate e) {
        throw new UnsupportedOperationException("add");
    }
    
    @Override
    public final boolean remove(Object o) {
        throw new UnsupportedOperationException("remove");
    }
    
    @Override
    public final boolean addAll(int index, Collection<? extends LocalDate> c) {
        throw new UnsupportedOperationException("addAll");
    }
    
    @Override
    public final LocalDate set(int index, LocalDate element) {
        throw new UnsupportedOperationException("set");
    }
    
    @Override
    public final void add(int index, LocalDate element) {
        throw new UnsupportedOperationException("add");
    }
    
    @Override
    public final LocalDate remove(int index) {
        throw new UnsupportedOperationException("remove");
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="UnmodifiableRange class">
    private class UnmodifiableRange extends Range {
        
        private UnmodifiableRange() {}
        
        @Override
        public LocalDate first() {
            return Range.this.first();
        }
        
        @Override
        public LocalDate last() {
            return Range.this.last();
        }
        
        @Override
        public ContinuousPeriod toPeriod() {
            return Range.this.toPeriod();
        }
        
        @Override
        public boolean is(Period period) {
            return Range.this.is(period);
        }
        
        @Override
        public Range clone() {
            return Range.this.clone();
        }
        
        @Override
        public boolean is(LocalDate first, LocalDate last) {
            return Range.this.is(first, last);
        }
        
        @Override
        public boolean is(Range range) {
            return Range.this.is(range);
        }
        
        @Override
        public Range modifiable() {
            throw new UnsupportedOperationException("modifiable");
        }
        
        @Override
        public Range unmodifiable() {
            return this;
        }
        
        @Override
        public String toString() {
            return Range.this.toString();
        }
        
        @Override
        public int hashCode() {
            return Range.this.hashCode();
        }
        
        @Override
        public boolean equals(Object o) {
            return Range.this.equals(o);
        }
        
        @Override
        protected ContinuousPeriod createPeriod(LocalDate first, LocalDate last) {
            return Range.this.createPeriod(first, last);
        }
        
        @Override
        protected ContinuousPeriod getPeriod() {
            return Range.this.getPeriod();
        }
        
        @Override
        public void setLast(LocalDate last) {
            throw new UnsupportedOperationException("setLast");
        }
        
        @Override
        public void setFirst(LocalDate first) {
            throw new UnsupportedOperationException("setFirst");
        }
        
        @Override
        public void set(LocalDate first, LocalDate last) {
            throw new UnsupportedOperationException("set");
        }
        
        @Override
        public void set(ContinuousPeriod period) {
            throw new UnsupportedOperationException("set");
        }
        
    }
    //</editor-fold>

}
