/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ndemyanovskyi.util.beans;

import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Назарій
 */
class UntypedBinding extends ConvertedBinding {
    
    private final Property property1;
    private final Property property2;

    public UntypedBinding(Property property1, Property property2) {
        this.property1 = property1;
        this.property2 = property2;
    }

    @Override
    public Property getProperty1() {
        return property1;
    }

    @Override
    public Property getProperty2() {
        return property2;
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {}
    
}
