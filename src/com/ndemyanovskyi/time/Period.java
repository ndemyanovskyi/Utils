package com.ndemyanovskyi.time;

import com.ndemyanovskyi.collection.list.unmodifiable.UnmodifiableConvertedList;
import static com.ndemyanovskyi.time.Period.of;
import com.ndemyanovskyi.util.Compare;
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
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterator;
import java.util.TreeSet;

/**
 *
 * @author Назарій
 */
public abstract class Period extends AbstractInterval implements Unmodifiable {
    
    private static final Comparator<Period> PERIOD_FIRST_LAST_COMPARATOR = (a, b) -> {
        if(a.isEmpty() && b.isEmpty()) return 0;
        if(a.isEmpty()) return -1;
        if(b.isEmpty()) return 1;
        int compare = a.first().compareTo(b.first());
        return compare == 0 
                ? a.last().compareTo(b.last()) 
                : compare;
    };
    
    public static final ContinuousPeriod EMPTY = new ContinuousPeriod();
    public static final ContinuousPeriod ALL = of(LocalDate.MIN, true, LocalDate.MAX, true);
 
    private Period inverted;
    
    public static ContinuousPeriod of(LocalDate fromInclusive, LocalDate toExclusive) {
        Objects.requireNonNull(fromInclusive, "fromInclusive");
        Objects.requireNonNull(toExclusive, "toExclusive");
        return of(fromInclusive, true, toExclusive, false);
    }
    
    public static ContinuousPeriod ofInclusive(LocalDate fromInclusive, LocalDate toInclusive) {
        return of(fromInclusive, true, toInclusive, true);
    }
    
    public static ContinuousPeriod of(LocalDate fromElement, boolean fromInclusive,  LocalDate toElement, boolean toInclusive) {
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
        return new ContinuousPeriod(fromElement, toElement);  
    }
    
    public static ContinuousPeriod of(LocalDate anchorDate, long offset) {
        Objects.requireNonNull(anchorDate, "anchorDate");
        if(offset == 0) return of(anchorDate);
        
        LocalDate from, to;
        LocalDate offsetDate = anchorDate.plusDays(offset);
        if(offset > 0) {
            from = anchorDate;
            to = offsetDate;
        } else {
            from = offsetDate;
            to = anchorDate;
        }
	return of(from, true, to, true);
    }
    
    public static Period parse(String text) {
        return parse(text, DateTimeFormatter.ISO_DATE);
    }
    
    public static Period parse(String text, DateTimeFormatter formatter) {
        return Parser.parse(text, formatter);
    }
    
    public static ContinuousPeriod of(LocalDate date) {
        return of(date, true, date, true);
    }
    
    public static Period of(Interval interval) {
        Objects.requireNonNull(interval, "interval");
        return of((Collection<LocalDate>) interval);
    }
    
    public static Period of(Collection<? extends LocalDate> dates) {
        Objects.requireNonNull(dates, "dates");
        if(!dates.isEmpty()) {
            return dates instanceof Period
                    ? (Period) dates
                    : dates instanceof Range
                            ? ((Range) dates).toPeriod()
                            : builder().plusDates(dates).build();
        } else {
            return EMPTY;
        }
    }

    @Override
    public abstract Period cross(Interval other);
    
    @Override
    public abstract List<ContinuousPeriod> getIncluded();
    @Override
    public abstract List<ContinuousPeriod> getExcluded();
    
    @Override
    public final Period inverted() {
        if(inverted == null) {
            if(!isEmpty()) {
                Period begin = !first().equals(LocalDate.MIN)
                        ? of(LocalDate.MIN, true, first(), false)
                        : EMPTY;
                Period end = !last().equals(LocalDate.MAX)
                        ? of(last(), false, LocalDate.MAX, true)
                        : EMPTY;
                
                inverted = builder().plusIntervals(getExcluded()).plusIntervals(begin, end).
                        build();
                if(!inverted.isEmpty()) {
                    inverted.inverted = this;
                }
            } else {
                inverted = ALL;
            }
        }
        return inverted;
    }

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);
    
    @Override
    public abstract LocalDate get(int index);

    @Override
    public abstract int indexOf(Object o);

    @Override
    public abstract Period subList(int fromInclusive, int toExclusive);

    @Override
    public Period subSet(LocalDate fromInclusive, LocalDate toExclusive) {
        return subSet(fromInclusive, true, toExclusive, false);
    }

    @Override
    public Period tailSet(LocalDate fromInclusive) {
        return tailSet(fromInclusive, true);
    }
    
    @Override
    public Period headSet(LocalDate toExclusive) {
        return headSet(toExclusive, false);
    }

    @Override
    public Period tailSet(LocalDate fromElement, boolean inclusive) {
        return (inclusive ? lessOrEquals(fromElement, last()) : less(fromElement, last()))
                ? subSet(fromElement, inclusive, last(), true) 
                : EMPTY;
    }

    @Override
    public Period headSet(LocalDate toElement, boolean inclusive) {
        return (inclusive ? greaterOrEquals(toElement, first()) : greater(toElement, first()))
                ? subSet(first(), true, toElement, inclusive) 
                : EMPTY;
    }
    
    @Override
    public abstract Period subSet(LocalDate fromElement, boolean fromInclusive, LocalDate toElement, boolean toInclusive);
    
    @Override
    public abstract ListIterator<LocalDate> listIterator(int index);

    @Override
    public ListIterator<LocalDate> listIterator() {
        return listIterator(0);
    }
    
    public static final Builder builder() {
        return new Builder();
    }
    
    public static final Builder builder(Period period) {
        Objects.requireNonNull(period, "period");
        return builder().plusInterval(period);
    }

    @Override
    public Spliterator<LocalDate> spliterator() {
        return super.spliterator();
    }
    
    public Range toRange() {
        if(isEmpty()) {
            throw new IllegalStateException("Period is empty. His can`t be convert to Range.");
        }
        return new Range(first(), last());
    }
    
    //<editor-fold defaultstate="collapsed" desc="Plus / minus methods">
    
    @Override
    public Period plus(Collection<? extends LocalDate> dates) {
        return builder(this).plusDates(dates).build();
    }
    
    @Override
    public Period plus(Interval other) {
        return builder(this).plusInterval(other).build();
    }
    
    @Override
    public Period plus(LocalDate date) {
        return !contains(date)
                ? builder(this).plusDate(date).build()
                : this;
    }
    
    @Override
    public Period minus(Collection<? extends LocalDate> dates) {
        return builder(this).minusDates(dates).build();
    }
    
    @Override
    public Period minus(Interval interval) {
        return builder(this).minusInterval(interval).build();
    }
    
    @Override
    public Period minus(LocalDate date) {
        return builder(this).minusDate(date).build();
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Builder">
    public static class Builder {
        
        private final List<BuilderRange> ranges = new ArrayList<>();
        private List<Range> unmodifiableRanges;
        private Map<Long, LocalDate> dateCache;
        private Period lastResult;
        
        private Builder() {
        }
        
        public boolean contentEquals(Interval interval) {
            if(interval == null) return false;
            
            int size = ranges.size();
            if(size == 0) return interval.isEmpty();
            
            if(interval instanceof ContinuousPeriod) {
                if(size == 1) {
                    Range range = ranges.get(0);
                    return range.first().equals(interval.first())
                            && range.last().equals(interval.last());
                }  else {
                    return false;
                }
            } else {
                List<? extends Interval> subIntervals = interval.getIncluded();
                for(int i = 0; i < subIntervals.size(); i++) {
                    Interval subInterval = subIntervals.get(i);
                    Range range = ranges.get(i);
                    if(!range.contentEquals(subInterval)) {
                        return false;
                    }
                }
                return true;
            }
        }
        
        public Map<Long, LocalDate> getDateCache() {
            return dateCache != null ? dateCache : (dateCache = new HashMap<>());
        }
        
        public Builder plusDates(Collection<? extends LocalDate> dates) {
            Objects.requireNonNull(dates, "dates");
            
            if(dates instanceof Period) {
                return plusInterval((Period) dates);
            }
            int size = dates.size();
            switch(size) {
                case 0: return this;
                case 1: return plusDate(dates.iterator().next());
                default: {
                    int index = 0;
                    for(LocalDate date : new TreeSet<>(dates)) {
                        index = plusDate0(index, date);
                    }
                    return this;
                }
            }
        }
        
        public Builder plusIntervals(Interval... intervals) {
            Objects.requireNonNull(intervals, "periods");
            return intervals.length == 1
                    ? plusInterval(intervals[0])
                    : plusIntervals(Arrays.asList(intervals));
        }
        
        public Builder plusIntervals(Collection<? extends Interval> intervals) {
            Objects.requireNonNull(intervals, "intervals");
            for(Interval interval : intervals) {
                plusInterval(interval);
            }
            return this;
        }
        
        public Builder plusInterval(Interval interval) {
            Objects.requireNonNull(interval, "interval");
            if(!interval.isEmpty()) {
                if(interval.isContinuous()) {
                    plusContinuousInterval(interval);
                }else {
                    for(Interval conPeriod : interval.getIncluded()) {
                        plusContinuousInterval(conPeriod);
                    }
                }
            }
            return this;
        }
        
        public Builder plusDates(LocalDate... dates) {
            Objects.requireNonNull(dates, "dates");
            return dates.length == 1
                    ? plusDate(dates[0])
                    : plusDates(Arrays.asList(dates));
        }
        
        private int plusDate0(int index, LocalDate date) {
            Objects.requireNonNull(date, "date");
            
            //Copying date to inner cache
            getDateCache().put(date.toEpochDay(), date);
            
            for(int i = index; i < ranges.size(); i++) {
                Range current = ranges.get(i);
                long before = DAYS.between(date, current.first());
                if(before == 1) {
                    current.setFirst(date);
                    if(i > 0) {
                        Range previous = ranges.get(i - 1);
                        long after = DAYS.between(previous.last(), date);
                        if(after == 1) {
                            previous.setLast(current.last());
                            ranges.remove(i);
                            return i;
                        }
                    }
                    return i;
                } else if(before > 1) {
                    if(i > 0) {
                        Range previous = ranges.get(i - 1);
                        long after = DAYS.between(previous.last(), date);
                        if(after == 1) {
                            previous.setLast(date);
                            return i;
                        } else if(after > 1) {
                            ranges.add(i, new BuilderRange(date, date));
                            return i;
                        }
                    } else {
                        ranges.add(0, new BuilderRange(date, date));
                        return 0;
                    }
                }
            }
            
            if(ranges.size() > 0) {
                Range last = ranges.get(ranges.size() - 1);
                long after = DAYS.between(last.last(), date);
                if(after == 1) {
                    last.setLast(date);
                } else if(after > 1) {
                    ranges.add(new BuilderRange(date, date));
                    return ranges.size() - 1;
                }
            } else {
                ranges.add(new BuilderRange(date, date));
                return ranges.size() - 1;
            }
            return 0;
        }
        
        public Builder plusDate(LocalDate date) {
            plusDate0(0, date);
            return this;
        }
        
        public Builder minusDates(Collection<? extends LocalDate> dates) {
            Objects.requireNonNull(dates, "dates");
            for(LocalDate date : dates) {
                minusDate(date);
            }
            return this;
        }
        
        public Builder minusDates(LocalDate... dates) {
            Objects.requireNonNull(dates, "dates");
            return dates.length == 1
                    ? minusDate(dates[0])
                    : Builder.this.minusDates(Arrays.asList(dates));
        }
        
        public Builder minusDate(LocalDate date) {
            Objects.requireNonNull(date, "date");
            
            if(!ranges.isEmpty()) {
                for(int i = 0; i < ranges.size(); i++) {
                    Range range = ranges.get(i);
                    if(range.contains(date)) {
                        int size = range.size();
                        switch(size) {
                            
                            case 1: 
                                ranges.remove(i);
                                break;
                                
                            case 2:
                                if(range.first().equals(date)) {
                                    range.setFirst(range.last());
                                }else {
                                    range.setLast(range.first());
                                }
                                break;
                                
                            default:
                                long after = DAYS.between(range.first(), date);
                                if(after == 0) {
                                    range.setFirst(date.plusDays(1));
                                }else if(after == size) {
                                    range.setLast(date.minusDays(1));
                                }else {
                                    BuilderRange newRange
                                            = new BuilderRange(date.plusDays(1), range.last());
                                    range.setLast(date.minusDays(1));
                                    ranges.add(i + 1, newRange);
                                }
                        }
                    }
                }
            }
            return this;
        }
        
        public Builder plusBuilder(Builder b) {
            return plusInterval(b.build());
        }
        
        public Builder minusBuilder(Builder b) {
            return minusInterval(b.build());
        }
        
        public Builder plusBuilders(Builder... builders) {
            for(Builder b : builders) {
                plusBuilder(b);
            }
            return this;
        }
        
        public Builder minusBuilders(Builder... builders) {
            for(Builder b : builders) {
                minusBuilder(b);
            }
            return this;
        }
        
        public Builder plusBuilders(Collection<? extends Builder> builders) {
            for(Builder b : builders) {
                plusBuilder(b);
            }
            return this;
        }
        
        public Builder minusBuilders(Collection<? extends Builder> builders) {
            for(Builder b : builders) {
                minusBuilder(b);
            }
            return this;
        }
        
        public Builder minusIntervals(Interval... intervals) {
            Objects.requireNonNull(intervals, "intervals");
            return intervals.length == 1
                    ? minusInterval(intervals[0])
                    : minusIntervals(Arrays.asList(intervals));
        }
        
        public Builder minusIntervals(Collection<? extends Interval> intervals) {
            Objects.requireNonNull(intervals, "intervals");
            for(Interval interval : intervals) {
                minusInterval(interval);
            }
            return this;
        }
        
        public Builder minusInterval(Interval interval) {
            Objects.requireNonNull(interval, "interval");
            if(!interval.isEmpty()) {
                if(interval.isContinuous()) {
                    minusContinuousInterval(interval);
                }else {
                    for(Interval i : interval.getIncluded()) {
                        minusContinuousInterval(i);
                    }
                }
            }
            return this;
        }
        
        public Builder clear() {
            ranges.clear();
            lastResult = null;
            return this;
        }

        List<BuilderRange> getModifiableRanges() {
            return ranges;
        }
        
        public List<Range> getRanges() {
            return unmodifiableRanges != null ? unmodifiableRanges
                    : (unmodifiableRanges = new UnmodifiableConvertedList<>(ranges, Range::unmodifiable));
        }
        
        private void minusContinuousInterval(Interval interval) {
            if(interval.isDiscontinuous()) {
                throw new IllegalArgumentException("Interval is discontinuous.");
            }
            
            if(ranges.isEmpty()) return;
            
            final Range first = ranges.get(0);
            final Range last = ranges.get(ranges.size() - 1);
            Range begin = null, end = null;
            int beginIndex = -1, endIndex = -1;
            
            //Checking before
            if(interval.first().compareTo(first.first()) < 0) {
                if(interval.last().compareTo(first.first()) < 0) {
                    return;
                }
                begin = first;
                beginIndex = 0;
            }
            
            //Checking after
            if(interval.last().compareTo(last.last()) > 0) {
                if(interval.first().compareTo(last.last()) > 0) {
                    return;
                }
                end = last;
                endIndex = ranges.size() - 1;
            }
            
            //Checking within
            for(int i = 0; i < ranges.size(); i++) {
                Range range = ranges.get(i);
                
                if(begin == null) {
                    long between = DAYS.between(interval.first(), range.last());
                    
                    if(between >= -1 || i == ranges.size() - 1) {
                        begin = range;
                        beginIndex = i;
                    } else {
                        Range next = ranges.get(i + 1);
                        long betweenNext = DAYS.between(interval.first(), next.first());
                        if(betweenNext > 1) {
                            begin = range;
                            beginIndex = i + 1;
                        }
                    }
                }
                if(end == null && interval.last().compareTo(range.last()) <= 0) {
                    long between = DAYS.between(interval.first(), range.first());
                    if(between < 1) {
                        end = range;
                        endIndex = i;
                    } else {
                        end = ranges.get(i - 1);
                        endIndex = i - 1;
                    }
                }
                if(begin != null && end != null) break;
            }
            
            if(begin == end) {
                if(interval.first().compareTo(begin.first()) <= 0) {
                    if(interval.last().compareTo(begin.last()) >= 0) {
                        ranges.remove(beginIndex);
                    } else {
                        begin.setFirst(interval.last().plusDays(1));
                    }
                } else {
                    if(interval.last().compareTo(begin.last()) >= 0) {
                        begin.setLast(interval.first().minusDays(1));
                    } else {
                        ranges.add(beginIndex + 1, new BuilderRange(
                                interval.last().plusDays(1), begin.last()));
                        begin.setLast(interval.first().minusDays(1));
                        return;
                    }
                }
            } else {
                if(interval.first().compareTo(begin.first()) <= 0) {
                    ranges.remove(beginIndex);
                    beginIndex++;
                    endIndex--;
                } else if(interval.first().compareTo(begin.last()) <= 0) {
                    begin.setLast(interval.first().minusDays(1));
                }
                if(interval.last().compareTo(end.last()) >= 0) {
                    ranges.remove(endIndex);
                    endIndex--;
                } else {
                    end.setFirst((LocalDate) Compare.max(
                            end.first(), interval.last().plusDays(1)));
                }
            }
        }
        
        private void plusContinuousInterval(Interval interval) {
            if(interval.isDiscontinuous()) {
                throw new IllegalArgumentException("Interval is discontinuous.");
            }
            
            if(!ranges.isEmpty()) {
                final BuilderRange first = ranges.get(0);
                final BuilderRange last = ranges.get(ranges.size() - 1);
                BuilderRange begin = null, end = null;
                int beginIndex = -1, endIndex = -1;
                
                if(interval.first().isBefore(first.first())) {
                    long between = DAYS.between(interval.last(), first.first());
                    if(between == 1) {
                        first.setFirst(interval.first());
                        return;
                    } else if(between > 1) {
                        ranges.add(0, new BuilderRange(interval));
                        return; //break;
                    } else {
                        begin = first;
                        beginIndex = 0;
                    }
                }
                
                if(interval.last().isAfter(last.last())) {
                    long between = DAYS.between(last.last(), interval.first());
                    if(between == 1) {
                        last.setLast(interval.last());
                        return;
                    } else if(between > 1) {
                        ranges.add(new BuilderRange(interval));
                        return; //break;
                    } else {
                        end = last;
                        endIndex = ranges.size() - 1;
                    }
                }
                
                for(int i = 0; i < ranges.size(); i++) {
                    BuilderRange range = ranges.get(i);
                    
                    if(begin == null) {
                        long between = DAYS.between(interval.first(), range.last());
                        
                        if(between >= -1) {
                            begin = range;
                            beginIndex = i;
                        } else if(i < ranges.size() - 1) {
                            Range next = ranges.get(i + 1);
                            long betweenNext = DAYS.between(interval.first(), next.first());
                            if(betweenNext > 1) {
                                begin = range;
                                beginIndex = i + 1;
                            }
                        }
                    }
                    if(end == null && interval.last().compareTo(range.last()) <= 0) {
                        end = range;
                        endIndex = i;
                    }
                    if(begin != null && end != null) break;
                }
                
                long betweenFirst = DAYS.between(begin.last(), interval.first());
                long betweenLast = DAYS.between(end.first(), interval.last());
                
                if(betweenFirst <= 1) {
                    if(betweenLast >= -1) {
                        LocalDate from = min(begin.first(), interval.first());
                        LocalDate to = max(end.last(), interval.last());
                        
                        if(interval.is(from, to)) {
                            begin.set(interval);
                        } else {
                            begin.set(from, to);
                        }
                        for(int i = endIndex; i > beginIndex; i--) {
                            ranges.remove(i);
                        }
                    } else {
                        begin.setLast(interval.last());
                        for(int i = endIndex - 1; i > beginIndex; i--) {
                            ranges.remove(i);
                        }
                    }
                } else {
                    if(betweenLast >= -1) {
                        end.setFirst(interval.first());
                        for(int i = endIndex - 1; i > beginIndex; i--) {
                            ranges.remove(i);
                        }
                    } else {
                        for(int i = endIndex - 1; i > beginIndex + 1; i--) {
                            ranges.remove(i);
                        }
                        ranges.add(beginIndex, new BuilderRange(interval));
                    }
                }
            } else {
                ranges.add(new BuilderRange(interval));
            }
            
            //Copying period cached dates to inner cache
            if(interval instanceof ContinuousPeriod) {
                ContinuousPeriod p = (ContinuousPeriod) interval;
                if(p.isDatePoolInitialized()) {
                    getDateCache().putAll(p.getDatePool());
                }
            }
        }
        
        public Period build() {
            if(lastResult != null) return lastResult;

            switch(ranges.size()) {
                case 0: return (lastResult = EMPTY);
                case 1: return (lastResult = ranges.get(0).toPeriod());
                default: return (lastResult = new DiscontinuousPeriod(this));
            }
        }

        @Override
        public boolean equals(Object o) {
            return o != null && o instanceof Builder
                    && ranges.equals(((Builder) o).ranges);
        }

        @Override
        public String toString() {
            int size = ranges.size();
            switch(size) {
                case 0: return "[]";
                case 1: return ranges.get(0).toString();
                default: 
                    StringBuilder b = new StringBuilder();
                    
                    b.append("[");
                    for(int i = 0; i < size; i++) {
                        Range range = ranges.get(i);
                        b.append(range);
                        if(i < size - 1) b.append("; ");
                    }
                    b.append("]");
                    return b.toString();
            }
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }
        
        //<editor-fold defaultstate="collapsed" desc="Query methods">
        public boolean contains(LocalDate date) {
            return indexOf(date) != -1;
        }
        
        public boolean isEmpty() {
            return ranges.isEmpty();
        }
        
        public int indexOf(LocalDate date) {
            if(date != null && !ranges.isEmpty()) {
                int offset = 0;
                for(Range range : ranges) {
                    if(range.contains(date)) {
                        return offset + (int) DAYS.between(range.first(), date);
                    }
                    offset += range.size();
                }
            }
            return -1;
        }
        //</editor-fold>
        
        private final class BuilderRange extends Range {
            
            public BuilderRange(LocalDate first, LocalDate last) {
                super(first, last);
            }
            
            public BuilderRange(Interval interval) {
                super(interval.first(), interval.last());
                set(interval);
            }
            
            private void set(Interval interval) {
                set(interval.first(), interval.last());
                if(interval instanceof ContinuousPeriod) {
                    set((ContinuousPeriod) interval);
                }
            }
            
            @Override
            public void set(LocalDate first, LocalDate last) {
                Period oldPeriod = getPeriod();
                super.set(first, last);
                Period newPeriod = getPeriod();
                if(newPeriod == null || !newPeriod.equals(oldPeriod)) {
                    Builder.this.lastResult = null;
                }
            }
            
            @Override
            public void setFirst(LocalDate first) {
                Period oldPeriod = getPeriod();
                super.setFirst(first);
                Period newPeriod = getPeriod();
                if(newPeriod == null || !newPeriod.equals(oldPeriod)) {
                    Builder.this.lastResult = null;
                }
            }
            
            @Override
            public void setLast(LocalDate last) {
                Period oldPeriod = getPeriod();
                super.setLast(last);
                Period newPeriod = getPeriod();
                if(newPeriod == null || !newPeriod.equals(oldPeriod)) {
                    Builder.this.lastResult = null;
                }
            }
            
            @Override
            public ContinuousPeriod createPeriod(LocalDate first, LocalDate last) {
                return dateCache != null
                        ? ContinuousPeriod.of(first, true, last, true, dateCache)
                        : super.createPeriod(first, last);
            }
            
        }
        
    }
    //</editor-fold>
    
}
