/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.throwable;

public class ValueConvertException extends IllegalArgumentException {
    
    private static final long serialVersionUID = 2374891755461L;
    
    public ValueConvertException(Object value, Class<?> toType) {
	this(value, toType, (String) null);
    }
    
    public ValueConvertException(Object value, Class<?> toType, String message) {
	super(createMessage(value, toType, message));
    }
    
    public ValueConvertException(Class<?> fromType, Class<?> toType) {
	this(fromType, toType, (String) null);
    }

    public ValueConvertException(Class<?> fromType, Class<?> toType, String message) {
        super(createMessage(fromType, toType, message));
    }

    public ValueConvertException(Class<?> fromType, Class<?> toType, Throwable cause) {
        this(fromType, toType, null, cause);
    }

    public ValueConvertException(Class<?> fromType, Class<?> toType, String message, Throwable cause) {
        super(createMessage(fromType, toType, message), cause);
    }
    
    public ValueConvertException(Object value, Class<?> toType, Throwable cause) {
	this(value, toType, null, cause);
    }
    
    public ValueConvertException(Object value, Class<?> toType, String message, Throwable cause) {
	super(createMessage(value, toType, message), cause);
    }
    
    private static String createMessage(Object value, Class<?> toType, String message) {
        return "Value [" + value + ", class=" + (value != null ? value.getClass() : null)
                + "] can`t convert to " + toType + 
                ((message != null) ? ". Message: " + message : "");
    }
    
    private static String createMessage(Class<?> fromType, Class<?> toType, String message) {
        return "Value with type " + fromType + " can`t convert to " + toType + 
                ((message != null) ? ". Message: " + message : "");
    }
    
}
