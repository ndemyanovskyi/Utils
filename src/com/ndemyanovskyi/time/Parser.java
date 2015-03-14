/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.time;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Назарій
 */
final class Parser {
    
    private Parser() {}
    
    public static Period parse(String text, DateTimeFormatter formatter) {
        int openBracketIndex = text.indexOf("[");
        int closeBracketIndex = text.lastIndexOf("]");
       
        if(openBracketIndex != -1 && closeBracketIndex != -1) {
            if(text.indexOf("[", openBracketIndex + 1) == -1) {
                return parseContinuousPeriod(text, openBracketIndex, formatter);
            }
            
            text = text.substring(openBracketIndex + 1, closeBracketIndex).trim();
            
            List<ContinuousPeriod> periods = new ArrayList<>();
            int from = 0, to = text.indexOf(';');
            if(to != -1) {
                boolean end = false;
                while(true) {
                    String token = text.substring(from, to);
                    ContinuousPeriod period = parseContinuousPeriod(token, from, formatter);
                    if(period.equals(Period.EMPTY)) {
                        throw invalidLogic("'empty period contains "
                                + "in discontinuous period'", text, from);
                    }
                    
                    if(!periods.isEmpty()) {
                        ContinuousPeriod last = periods.get(periods.size() - 1);
                        long between = DAYS.between(last.last(), period.first());
                        if(between == 1) {
                            throw invalidLogic("collision of " + last + " and " + period, text, from);
                        }
                        if(between < 1) {
                            throw invalidLogic(period + " before " + last, text, from);
                        }
                    } 
                    
                    periods.add(period);
                    
                    if(end) break;
                    from = to + 1;
                    to = text.indexOf(';', to + 1);
                    if(to == -1) {
                        end = true;
                        to = text.length();
                    }
                }
            }
            switch(periods.size()) {
                case 0: throw invalidText(text, 0);
                //case 1: throw invalidLogic("discontinuous period contains 1 cont. period", text, 0);
                default: return Period.builder().plusIntervals(periods).build();
            }
        }
        
        throw invalidText(text, 0);
    }
    
    private static ContinuousPeriod parseContinuousPeriod(String text, int position, DateTimeFormatter formatter) {
        int openBracketIndex = text.indexOf("[");
        int closeBracketIndex = text.lastIndexOf("]");
        if(openBracketIndex != -1 && closeBracketIndex != -1) {
            text = text.substring(openBracketIndex + 1, closeBracketIndex).trim();
            if(text.equals("")) return Period.EMPTY;
            
            int index = text.indexOf("=>");
            if(index >= 0) {
                try {
                    LocalDate first = LocalDate.parse(
                            text.substring(0, index).trim(), formatter);
                    LocalDate second = LocalDate.parse(
                            text.substring(index + 2).trim(), formatter);
                    if(first.isAfter(second)) {
                        throw invalidLogic("from > to", text, position);
                    }
                    return Period.of(first, true, second, true);
                } catch(DateTimeParseException ex) {
                    throw new PeriodParseException(ex.getMessage());
                }
            }
        }
        throw invalidText(text, position);
    }
    
    private static PeriodLogicParseException invalidLogic(String logicExpression, String text, int index) {
        return new PeriodLogicParseException(
                "Invalid logic: " + logicExpression + 
                        " in text '" + text + "' at index " + index + ".");
    }
    
    private static PeriodLogicParseException invalidText(String text, int index) {
        return new PeriodLogicParseException(
                "Text '" + text + "' couldn`t be parsed at index " + index + ".");
    }
    
}
