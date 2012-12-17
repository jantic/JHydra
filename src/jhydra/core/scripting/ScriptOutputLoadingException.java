/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

import jhydra.core.FatalException;

/**
 *
 * @author jantic
 */
public class ScriptOutputLoadingException extends FatalException{
    private final String scriptName;
    private final String details;
    
    public ScriptOutputLoadingException(String scriptName, Exception e){
        super("", e);
        this.scriptName = scriptName;
        this.details = e.getMessage();
    }
    
    @Override
    public String getMessage(){
        return "Error while attempting to load compiled script named " + scriptName + " from compiler output: " + details;
    }
}
