/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.observing;

import java.util.function.Consumer;


/**
 *
 * @author Nazariy
 */
public abstract class AbstractObservable implements Observable {
    
    private Listeners<Listener> listeners;

    protected final Listeners<Listener> listeners() {
        return listeners != null ? listeners : 
		(listeners = new Listeners<>(this));
    }
    
    protected <L extends Listener> void notifyChange(Class<L> subClass, Consumer<? super L> action) {
        if(isListenersRegistered()) {
            listeners().sub(subClass).notifyChange(action);
        }
    }
    
    protected final boolean isListenersRegistered() {
	return listeners != null && !listeners.isEmpty();
    }
    
    interface SubListener extends Listener {
        public void onSubChange(Observable o, Object data);
    }

    @Override
    public void removeListener(Listener listener) {
        listeners().sub(SubListener.class).
                notifyChange(l -> l.onSubChange(this, null));
	if(isListenersRegistered()) listeners().remove(listener);
    }

    @Override
    public void addListener(Listener listener) {
	listeners().add(listener);
    }

    @Override
    public void addSoftListener(Listener listener) {
	listeners().addSoft(listener);
    }

    @Override
    public void addWeakListener(Listener listener) {
	listeners().addWeak(listener);
    }

    @Override
    public void notifyChange() {
	if(isListenersRegistered()) {
	    listeners().notifyChange();
	}
    }
    
}
