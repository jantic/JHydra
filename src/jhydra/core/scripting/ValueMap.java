/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

import jhydra.core.scripting.IValueMap;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jantic
 */
public class ValueMap implements IValueMap {
    private final Map<String,String> map = new HashMap<>();
    
    public ValueMap(){   
    }
    
    @Override
    public String getValue(String name) throws Exception{
        validateName(name);
        final String key = generateKey(name);
        
        if(map.containsKey(key)){
            return map.get(key);
        }
        
        final String message = "Value named '" + name + "' could not be found!";
        throw new Exception(message);
    }
    
    @Override
    public boolean hasValue(String name){
        final String key = generateKey(name);    
        return map.containsKey(key);
    }
    
    @Override
    public void setValue(String name, String value) throws Exception{
        validateName(name);
        final String key = generateKey(name);
        map.put(key, value);
    }
    
    private void validateName(String name) throws Exception{
        if(name == null){
            final String message = "Value name must not be null!";
            throw new Exception(message);
        }
    }
    
    private String generateKey(String name){
        if(name == null){
            return "";
        }
        
        return name.toLowerCase().trim();
    }
}
