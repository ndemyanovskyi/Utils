/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.observing;

public interface Observable {
    
    public void addListener(Listener listener);
    public void addSoftListener(Listener listener);
    public void addWeakListener(Listener listener);
    public void removeListener(Listener listener);
    
    public void notifyChange();
    
    @FunctionalInterface
    public interface Listener {
	public void onChange(Observable object);
    }
    
}
