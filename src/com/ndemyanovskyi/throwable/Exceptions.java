/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ndemyanovskyi.throwable;

import com.ndemyanovskyi.throwable.function.ThrowableRunnable;
import com.ndemyanovskyi.throwable.function.ThrowableSupplier;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;


public class Exceptions {
    
    public static void execute(ThrowableRunnable<? extends Exception> action) {
	try {
	    action.run();
	} catch(Exception ex) {
	    throw wrap(ex);
	}
    }
    
    public static void execute(ThrowableRunnable<? extends Exception> action, 
			       Class<? extends RuntimeException> exceptionWrapper) {
	try {
	    action.run();
	} catch(Exception ex) {
	    throw wrap(exceptionWrapper, ex);
	}
    }
    
    public static <T> T execute(ThrowableSupplier<T, ? extends Exception> action) {
	try {
	    return action.get();
	} catch(Exception ex) {
	    throw wrap(ex);
	}
    }
    
    public static <T> T execute(ThrowableSupplier<T, ? extends Exception> action, 
			       Class<? extends RuntimeException> exceptionWrapper) {
	try {
	    return action.get();
	} catch(Exception ex) {
	    throw wrap(exceptionWrapper, ex);
	}
    }
    
    public static Throwable unwrap(Throwable throwable) {
        Objects.requireNonNull(throwable, "throwable");
        
        if(throwable instanceof RuntimeException) {
            Throwable cause = throwable.getCause();
            while(cause != null && cause instanceof RuntimeException) {
                cause = cause.getCause();
            } 
            
            if(cause == null || cause instanceof RuntimeException) {
                throw new IllegalArgumentException(
                        "This exception(" + throwable + ") can`t be unwrapped.");
            }
            return cause;
        } else {
            return throwable;
        }
    }
    
    public static RuntimeException wrap(Throwable wrapped) {
        Objects.requireNonNull(wrapped, "wrapped");
        
	if(wrapped instanceof RuntimeException) {
	    return (RuntimeException) wrapped;
	}
        
        Class<? extends RuntimeException> exceptionType
                = (wrapped instanceof SQLException)
                        ? RuntimeSQLException.class
                        : (wrapped instanceof IOException)
                                ? RuntimeIOException.class
                                : RuntimeException.class;
        
        return wrap(exceptionType, wrapped);
    }
    
    public static RuntimeException wrap(
	    Class<? extends RuntimeException> exceptionType, Throwable wrapped) {
        Objects.requireNonNull(exceptionType, "exceptionType");
        Objects.requireNonNull(wrapped, "wrapped");
        
	if(wrapped instanceof RuntimeException) {
	    return (RuntimeException) wrapped;
	}
	
        Class<?> type = wrapped.getClass();
        while(true) {
            try {
                return exceptionType.getConstructor(type).
                        newInstance(wrapped);
            }catch(ReflectiveOperationException | SecurityException | IllegalArgumentException ex1) {}

            try {
                return exceptionType.getConstructor(String.class, type).
                        newInstance("Wrapped by: ", wrapped);
            }catch(ReflectiveOperationException | SecurityException | IllegalArgumentException ex1) {}

            
            if(!type.equals(Throwable.class)) {
                type = type.getSuperclass();
            } else break;
        }

        try {
            return exceptionType.newInstance();
        }catch(InstantiationException | IllegalAccessException ex2) {}
        
        throw new IllegalArgumentException(exceptionType.getName() + " haven`t valid constructors.");
    }

}
