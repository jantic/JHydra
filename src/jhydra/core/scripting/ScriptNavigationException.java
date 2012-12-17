/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

import jhydra.core.RecoverableException;

/**
 *
 * @author jantic
 */
public class ScriptNavigationException extends RecoverableException{
    private final String details;
    
    public ScriptNavigationException(String details, Exception e){
        super("", e);
        this.details = details;
    }
    
    public ScriptNavigationException(String details){
        super("");
        this.details = details;
    }
    
    @Override
    public String getMessage(){
       return "Error while attempting to navigate user interface in script: " + details; 
    }
    
}
