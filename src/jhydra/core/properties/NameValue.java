/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.properties;

/**
 *
 * @author jantic
 */
public class NameValue implements INameValue{
    private final String name;
    private final String value;

    public NameValue(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public Boolean matchesName(String name) {
        if(name==null){
            return false;
        }
        return this.name.trim().equalsIgnoreCase(name.trim());
    }

    @Override
    public Boolean matchesValue(String value) {
        if(value==null){
            return false;
        }
        return this.value.trim().equalsIgnoreCase(value.trim());
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
    public INameValue copy() {
        return new NameValue(this.name, this.value);
    }
    
    @Override
    public INameValue copyWithNewValue(String value) {
        return new NameValue(this.name, value);
    }
    
}
