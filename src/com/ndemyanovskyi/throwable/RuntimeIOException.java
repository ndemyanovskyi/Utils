/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.throwable;

import java.io.IOException;

public class RuntimeIOException extends RuntimeException {
    
    private static final long serialVersionUID = 123423654634346346L;

    @Override
    public synchronized IOException getCause() {
        return (IOException) super.getCause();
    }

    public RuntimeIOException(String message, IOException cause) {
        super(message, cause);
    }

    public RuntimeIOException(IOException cause) {
        super(cause);
    }

    protected RuntimeIOException(String message, IOException cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
