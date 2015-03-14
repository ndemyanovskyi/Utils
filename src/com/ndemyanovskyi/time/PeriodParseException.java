/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.time;

/**
 *
 * @author Назарій
 */
public class PeriodParseException extends RuntimeException {
    
    private static final long serialVersionUID = 14353252452353245L;

    public PeriodParseException() {}

    public PeriodParseException(String text) {
        super(text);
    }
    
}
