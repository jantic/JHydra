/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.properties;

import jhydra.core.properties.exceptions.NameNotValidException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author jantic
 */
public class NameValueValidator implements INameValueValidator {
    private static final String NAME_PATTERN = "^\\w+$";
    
    @Override
    public void validateName(String name) throws NameNotValidException{
        if(name == null || name.isEmpty()){
            throw new NameNotValidException("", "Name must not be null or empty");
        }
        
        final Pattern pattern = Pattern.compile(NAME_PATTERN);
        final Matcher matcher = pattern.matcher(name.trim());
        
        if(!matcher.matches()){
            final String details = "Variable name attempted is not valid! Attempted: " + name + 
                    ".  Names must be alphanumeric, and not have any spaces in between characters.";
            throw new NameNotValidException(name, details);
        }
    }
}
