/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.lexicon;

import jhydra.core.properties.NameNotValidException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jhydra.core.properties.INameValue;
import jhydra.core.properties.INameValueValidator;
import jhydra.core.properties.NameValueValidator;

/**
 *
 * @author jantic
 */
public class ValueMap implements IValueMap {
    private final ILexicon lexicon;
    private final INameValueValidator nameValueValidator = new NameValueValidator();
    private final Map<String,INameValue> map = new HashMap<>();
    
    public ValueMap(ILexicon lexicon){   
        this.lexicon = lexicon;
        initializeFromLexicon();
    }
    
    @Override
    public String getValue(String name) throws  NameNotInLexiconException, NameNotValidException{
        validateName(name);
        final String key = generateKey(name);
        return map.get(key).getValue();
    }
    
    @Override
    public boolean hasValue(String name){
        final String key = generateKey(name);    
        return map.containsKey(key);
    }
    
    @Override
    public void setValue(String name, String value) throws  NameNotInLexiconException, NameNotValidException{
        validateName(name);
        final String key = generateKey(name);
        final INameValue oldPair = map.get(key);
        final INameValue newPair = oldPair.copyWithNewValue(value);
        map.put(key, newPair);
    }
    
    private void initializeFromLexicon(){
        final List<INameValue> initPairs = lexicon.getAllNameDefaultValuePairs();
        
        for(INameValue pair : initPairs){
            addPairFromLexicon(pair);
        }
    }
    
    private void addPairFromLexicon(INameValue pair){
        final String name = pair.getName();
        final String key = generateKey(name);
        map.put(key, pair);
    }
    
    private void validateName(String name) throws  NameNotInLexiconException, NameNotValidException{
        nameValueValidator.validateName(name); 
        
        final String key = generateKey(name);
        
        if(!map.containsKey(key)){
            final String message = "Value named '" + name + "' could not be found!";
            throw new NameNotInLexiconException(message, this.lexicon.getFilePath());
        }

    }
    
    private String generateKey(String name){
        if(name == null){
            return "";
        }
        
        return name.toLowerCase().trim();
    }
}
