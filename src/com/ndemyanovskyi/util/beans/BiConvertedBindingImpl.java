package com.ndemyanovskyi.util.beans;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.ndemyanovskyi.util.BiConverter;
import java.lang.ref.WeakReference;
import java.util.Objects;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author Назарій
 */
class BiConvertedBindingImpl<T1, T2> extends ConvertedBinding<T1, T2> {

    private final WeakReference<Property<T1>> propertyRef1;
    private final WeakReference<Property<T2>> propertyRef2;
    private final BiConverter<T1, T2> converter;

    private boolean updating = false;

    public BiConvertedBindingImpl(Property<T1> property1, Property<T2> property2, BiConverter<T1, T2> converter) {
        propertyRef1 = new WeakReference<>(property1);
        propertyRef2 = new WeakReference<>(property2);
        this.converter = Objects.requireNonNull(converter, "converter");
        
        property1.setValue(converter.from(property2.getValue()));
        property1.addListener(this);
        property2.addListener(this);
    }

    public BiConverter<T1, T2> converter() {
        return converter;
    }

    @Override
    public void changed(ObservableValue property, Object oldValue, Object newValue) {
        if(!updating) {
            final Property<T1> property1 = propertyRef1.get();
            final Property<T2> property2 = propertyRef2.get();
            
            if((property1 == null) || (property2 == null)) {
                if(property1 != null) {
                    property1.removeListener(this);
                }
                if(property2 != null) {
                    property2.removeListener(this);
                }
            }else {
                try {
                    updating = true;
                    if(property == property2) {
                        property1.setValue(converter().from((T2) newValue));
                    } else {
                        property2.setValue(converter().to((T1) newValue));
                    }
                }catch(RuntimeException e) {
                    try {
                        if(property == property2) {
                            property1.setValue(converter().from((T2) oldValue));
                        }else {
                            property2.setValue(converter().to((T1) oldValue));
                        }
                    }catch(Exception e2) {
                        e2.addSuppressed(e);
                        unbind(property1, property2);
                        throw new RuntimeException(
                                "Bidirectional binding failed together with an attempt"
                                + " to restore the source property to the previous value."
                                + " Removing the bidirectional binding from properties "
                                + property1 + " and " + property2, e2);
                    }
                    throw new RuntimeException(
                            "Bidirectional binding failed, setting to the previous value", e);
                }finally {
                    updating = false;
                }
            }
        }
    }

    @Override
    public Property<T1> getProperty1() {
        return propertyRef1.get();
    }

    @Override
    public Property<T2> getProperty2() {
        return propertyRef2.get();
    }

}
