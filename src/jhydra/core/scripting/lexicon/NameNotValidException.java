/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.lexicon;

import jhydra.core.FatalException;

/**
 *
 * @author jantic
 */
public class NameNotValidException extends FatalException{
    private final String name;
    private final String details;
    
    public NameNotValidException(String name, String details){
        super("");
        this.name = name;
        this.details = details;
    }
    
    @Override
    public String getMessage(){
       return "Lexicon name is not valid: " + name + ".  Details:  " + details;
    }
    
}
