package com.ndemyanovskyi.collection.set;

import com.ndemyanovskyi.collection.CombinedCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public interface CombinedSet<E> extends CombinedCollection<E>, DefaultSet<E> {

    @Override
    default public Iterator<E> iterator() {
        return CombinedCollection.super.iterator();
    }

    @Override
    default public int size() {
        return CombinedCollection.super.size();
    }

    @Override
    public Collection<? extends Set<E>> collections();

}
