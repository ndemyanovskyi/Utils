/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.throwable.function;

/**
 *
 * @author Назарій
 */
@FunctionalInterface
public interface ThrowableRunnable<E extends Throwable> {
    
    public void run() throws E;
    
}
