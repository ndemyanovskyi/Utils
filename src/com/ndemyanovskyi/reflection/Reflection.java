/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.reflection;


public class Reflection {
    
    public static Class<?> getCallerClass() {
	StackTraceElement[] elements = new Throwable().getStackTrace();
	for(int i = 2; i < elements.length; i++) {
	    try {
		return Class.forName(elements[i].getClassName());
	    } catch(ClassNotFoundException ignored) {}
	}
	
	return null;
    }

}
