/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.properties;

import jhydra.core.properties.exceptions.NameNotValidException;

/**
 *
 * @author jantic
 */
public class NameValue implements INameValue{
    private final String name;
    private final String value;
    
    public static INameValue getInstance(String name, String value) throws NameNotValidException{
        final INameValueValidator nameValueValidator = new NameValueValidator();
        nameValueValidator.validateName(name);
        return new NameValue(name, value);
    } 

    private NameValue(String name, String value){
        this.name = name;
        this.value = value == null ? "" : value;
    }

    //We want this to be forgiving, so that users don't waste their time with
    //capitalization errors in config, etc.
    @Override
    public Boolean matchesName(String name) {
        if(name==null){
            return false;
        }
        return this.name.trim().equalsIgnoreCase(name.trim());
    }

    //We want this to be precise, so no trim/ignore case
    @Override
    public Boolean matchesValue(String value) {
        if(value==null){
            return this.value.isEmpty();
        }
        return this.value.trim().equals(value);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public INameValue copy(){
        return new NameValue(this.name, this.value);
    }
    
    @Override
    public INameValue copyWithNewValue(String value) {
        return new NameValue(this.name, value);
    }
    
}
