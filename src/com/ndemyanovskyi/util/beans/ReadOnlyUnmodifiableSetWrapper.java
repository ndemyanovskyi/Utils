/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.util.beans;

import com.ndemyanovskyi.collection.set.unmodifiable.UnmodifiableObservableSet;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlySetProperty;
import javafx.beans.property.ReadOnlySetWrapper;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;

/**
 *
 * @author Назарій
 */
public class ReadOnlyUnmodifiableSetWrapper<E> extends ReadOnlySetWrapper<E> {
    
    private ReadOnlySetPropertyImpl readOnlyProperty;

    public ReadOnlyUnmodifiableSetWrapper() {
    }

    public ReadOnlyUnmodifiableSetWrapper(ObservableSet<E> initialValue) {
        super(initialValue);
    }

    public ReadOnlyUnmodifiableSetWrapper(Object bean, String name) {
        super(bean, name);
    }

    public ReadOnlyUnmodifiableSetWrapper(Object bean, String name, ObservableSet<E> initialValue) {
        super(bean, name, initialValue);
    }

    @Override
    public ReadOnlySetProperty<E> getReadOnlyProperty() {
        return readOnlyProperty != null ? readOnlyProperty 
                : (readOnlyProperty = new ReadOnlySetPropertyImpl());
    }
    
    private class ReadOnlySetPropertyImpl extends ReadOnlySetProperty<E> {
        
        private UnmodifiableSet set;

        @Override
        public ReadOnlyIntegerProperty sizeProperty() {
            return ReadOnlyUnmodifiableSetWrapper.this.sizeProperty();
        }

        @Override
        public ReadOnlyBooleanProperty emptyProperty() {
            return ReadOnlyUnmodifiableSetWrapper.this.emptyProperty();
        }

        @Override
        public ObservableSet<E> get() {
            if(set == null || set.base() != ReadOnlyUnmodifiableSetWrapper.this.get()) {
                return (set = new UnmodifiableSet());
            }
            return set;
        }

        @Override
        public void addListener(ChangeListener<? super ObservableSet<E>> listener) {
            ReadOnlyUnmodifiableSetWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(ChangeListener<? super ObservableSet<E>> listener) {
            ReadOnlyUnmodifiableSetWrapper.this.removeListener(listener);
        }

        @Override
        public void addListener(InvalidationListener listener) {
            ReadOnlyUnmodifiableSetWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(InvalidationListener listener) {
            ReadOnlyUnmodifiableSetWrapper.this.removeListener(listener);
        }

        @Override
        public void addListener(SetChangeListener<? super E> listener) {
            ReadOnlyUnmodifiableSetWrapper.this.addListener(listener);
        }

        @Override
        public void removeListener(SetChangeListener<? super E> listener) {
            ReadOnlyUnmodifiableSetWrapper.this.removeListener(listener);
        }

        @Override
        public Object getBean() {
            return ReadOnlyUnmodifiableSetWrapper.this.getBean();
        }

        @Override
        public String getName() {
            return ReadOnlyUnmodifiableSetWrapper.this.getName();
        }
        
        private class UnmodifiableSet extends UnmodifiableObservableSet<E> {

            public UnmodifiableSet() {
                super(ReadOnlyUnmodifiableSetWrapper.this.get());
            }

            @Override
            public ObservableSet<E> base() {
                return super.base();
            }
            
        }
        
    }
    
}
