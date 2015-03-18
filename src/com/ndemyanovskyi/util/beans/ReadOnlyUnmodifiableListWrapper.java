/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.util.beans;

import com.ndemyanovskyi.collection.list.unmodifiable.UnmodifiableObservableList;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 *
 * @author Назарій
 */
public class ReadOnlyUnmodifiableListWrapper<E> extends ReadOnlyListWrapper<E> {
    
    private ReadOnlyLisPropertyImpl readOnlyProperty;

    public ReadOnlyUnmodifiableListWrapper() {
    }

    public ReadOnlyUnmodifiableListWrapper(ObservableList<E> initialValue) {
        super(initialValue);
    }

    public ReadOnlyUnmodifiableListWrapper(Object bean, String name) {
        super(bean, name);
    }

    public ReadOnlyUnmodifiableListWrapper(Object bean, String name, ObservableList<E> initialValue) {
        super(bean, name, initialValue);
    }

    @Override
    public ReadOnlyListProperty<E> getReadOnlyProperty() {
        return readOnlyProperty != null ? readOnlyProperty 
                : (readOnlyProperty = new ReadOnlyLisPropertyImpl());
    }
    
    private class ReadOnlyLisPropertyImpl extends ReadOnlyListProperty<E> {
        
        private UnmodifiableSet set;

        @Override
        public ReadOnlyIntegerProperty sizeProperty() {
            return ReadOnlyUnmodifiableListWrapper.this.sizeProperty();
        }

        @Override
        public ReadOnlyBooleanProperty emptyProperty() {
            return ReadOnlyUnmodifiableListWrapper.this.emptyProperty();
        }

        @Override
        public ObservableList<E> get() {
            if(set == null || set.base() != ReadOnlyUnmodifiableListWrapper.this.get()) {
                return (set = new UnmodifiableSet());
            }
            return set;
        }

        @Override
        public void addListener(ChangeListener<? super ObservableList<E>> listener) {
            ReadOnlyUnmodifiableListWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(ChangeListener<? super ObservableList<E>> listener) {
            ReadOnlyUnmodifiableListWrapper.this.removeListener(listener);
        }

        @Override
        public void addListener(InvalidationListener listener) {
            ReadOnlyUnmodifiableListWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            ReadOnlyUnmodifiableListWrapper.this.removeListener(listener);
        }

        @Override
        public void addListener(ListChangeListener<? super E> listener) {
            ReadOnlyUnmodifiableListWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(ListChangeListener<? super E> listener) {
            ReadOnlyUnmodifiableListWrapper.this.removeListener(listener);
        }

        @Override
        public Object getBean() {
            return ReadOnlyUnmodifiableListWrapper.this.getBean();
        }

        @Override
        public String getName() {
            return ReadOnlyUnmodifiableListWrapper.this.getName();
        }
        
        private class UnmodifiableSet extends UnmodifiableObservableList<E> {

            public UnmodifiableSet() {
                super(ReadOnlyUnmodifiableListWrapper.this.get());
            }

            @Override
            public ObservableList<E> base() {
                return super.base();
            }
            
        }
        
    }
    
}
