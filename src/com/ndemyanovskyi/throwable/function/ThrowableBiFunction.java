/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.throwable.function;


public interface ThrowableBiFunction<P1, P2, R, T extends Throwable> {

    public R apply(P1 p1, P2 p2) throws T;
    
}
