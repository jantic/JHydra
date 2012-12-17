/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.lexicon;

import java.util.HashMap;
import java.util.Map;
import jhydra.core.config.IConfig;

/**
 *
 * @author jantic
 */
public class ValueMap implements IValueMap {
    private final IConfig config;
    private final Map<String,String> map = new HashMap<>();
    
    public ValueMap(IConfig config){   
        this.config = config;
        //TODO:  Initialize with lexicon registry;
    }
    
    @Override
    public String getValue(String name) throws  NameNotInLexiconException{
        validateName(name);
        final String key = generateKey(name);
        return map.get(key);
    }
    
    @Override
    public boolean hasValue(String name){
        final String key = generateKey(name);    
        return map.containsKey(key);
    }
    
    @Override
    public void setValue(String name, String value) throws  NameNotInLexiconException{
        validateName(name);
        final String key = generateKey(name);
        map.put(key, value);
    }
    
    private void validateName(String name) throws  NameNotInLexiconException{
        if(name == null){
            final String message = "Value name must not be null!";
            throw new NameNotInLexiconException(message, this.config);
        }
        
        final String key = generateKey(name);
        
        if(!map.containsKey(key)){
            final String message = "Value named '" + name + "' could not be found!";
            throw new NameNotInLexiconException(message, this.config);
        }

    }
    
    private String generateKey(String name){
        if(name == null){
            return "";
        }
        
        return name.toLowerCase().trim();
    }
}
