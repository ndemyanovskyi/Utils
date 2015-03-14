/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.observing;

import com.ndemyanovskyi.collection.set.DefaultSet;
import com.ndemyanovskyi.iterator.Iterators;
import com.ndemyanovskyi.observing.Listeners.SubListeners;
import com.ndemyanovskyi.observing.Observable.Listener;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import static org.apache.commons.collections4.map.AbstractReferenceMap.ReferenceStrength.HARD;
import static org.apache.commons.collections4.map.AbstractReferenceMap.ReferenceStrength.SOFT;
import static org.apache.commons.collections4.map.AbstractReferenceMap.ReferenceStrength.WEAK;
import org.apache.commons.collections4.map.ReferenceMap;


public class Listeners<L extends Listener> implements DefaultSet<L> {
    
    private Set<L> hardSet;
    private Set<L> softSet;
    private Set<L> weakSet;
    private Map<Class<?>, SubListeners<?>> subs;
    
    private final Observable owner;

    public Listeners(Observable owner) {
	this.owner = Objects.requireNonNull(owner);
    }

    Set<L> hardSet() {
	if(hardSet == null) {
            hardSet = new HashSet<>();
        }
	return hardSet;
    }

    Set<L> softSet() {
	if(softSet == null) {
	    softSet = Collections.newSetFromMap(new ReferenceMap<>(SOFT, HARD));
	}
	return softSet;
    }

    Set<L> weakSet() {
	if(weakSet == null) {
	    weakSet = Collections.newSetFromMap(new ReferenceMap<>(WEAK, HARD));
	}
	return weakSet;
    }

    private Map<Class<?>, SubListeners<?>> subs() {
	if(subs == null) {
	    subs = new ReferenceMap<>(HARD, SOFT);
	}
	return subs;
    }
    
    public void notifyChange() {
	if(!isEmpty()) {
	    for(Listener l : this) {
		l.onChange(owner);
	    }
	}
    }

    @Override
    public int size() {
	int size = 0;
	if(hardSet != null) size += hardSet.size();
	if(softSet != null) size += softSet.size();
	if(weakSet != null) size += weakSet.size();
	return size;
    }

    @Override
    public Iterator<L> iterator() {
	Iterator<L> empty = Iterators.empty();
	return Iterators.combined(
		(hardSet != null) ? hardSet.iterator() : empty, 
		(softSet != null) ? softSet.iterator() : empty, 
		(weakSet != null) ? weakSet.iterator() : empty);
    }

    @Override
    public boolean add(L e) {
	return hardSet().add(e);
    }

    public boolean addSoft(L e) {
	return softSet().add(e);
    }

    public boolean addWeak(L e) {
	return weakSet().add(e);
    }

    @Override
    public boolean remove(Object o) {
	return (hardSet != null && hardSet.remove(o)) 
                && (softSet != null && softSet.remove(o)) 
                && (weakSet != null && weakSet.remove(o));
    }
    
    public <T extends L> SubListeners<T> sub(Class<T> c) {
	SubListeners<T> sub = (SubListeners<T>) subs().get(c);
	if(sub == null) {
	    sub = new SubListeners<>(this, c);
	    subs().put(c, sub);
	}
	return sub;
    }
    
    public static class SubListeners<S extends Listener> extends Listeners<S> {

	private final Class<S> representClass;
	private final Listeners<? super S> base;
	
	public SubListeners(Listeners<? super S> base, Class<S> representClass) {
	    super(base.owner);
	    this.base = base;
	    this.representClass = Objects.requireNonNull(representClass);
	}

	public Class<S> getRepresentClass() {
	    return representClass;
	}

	@Override
	public boolean add(S e) {
	    return base.add(e);
	}

	@Override
	public boolean addSoft(S e) {
	    return base.addSoft(e);
	}

	@Override
	public boolean addWeak(S e) {
	    return base.addWeak(e);
	}

	@Override
	public boolean remove(Object o) {
	    return representClass.isInstance(o) && base.remove(o);
	}

	@Override
	public Iterator<S> iterator() {
	    return Iterators.filtered(
		    (Iterator<S>) base.iterator(), representClass::isInstance);
	}

	@Override
	public <T extends S> SubListeners<T> sub(Class<T> c) {
	    if(!c.isAssignableFrom(representClass)) {
		throw new IllegalArgumentException(
			c + " not assignable from " + representClass);
	    }
	    
	    return base.sub((Class) c);
	}

        @Override
        public void notifyChange() {
            base.notifyChange();
        }

	public void notifyChange(Consumer<? super S> action) {
	    if(!base.isEmpty()) {
		for(Listener l : base) {
		    l.onChange(base.owner);
		    if(representClass.isInstance(l)) {
			action.accept((S) l);
		    }
		}
	    }
	}

	@Override
	public int size() {
	    return (int) stream().count();
	}

	@Override
	public boolean isEmpty() {
	    return size() == 0;
	}
	
    }

}
