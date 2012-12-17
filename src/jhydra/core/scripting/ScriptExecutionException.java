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
public class ScriptExecutionException extends RecoverableException{
    private final String scriptName;
    private final String details;
    
    public ScriptExecutionException(String scriptName, String details, Exception e){
        super("", e);
        this.scriptName = scriptName;
        this.details = details;
    }
    
    public ScriptExecutionException(String scriptName, String details){
        super("");
        this.scriptName = scriptName;
        this.details = details;
    }
    
    @Override
    public String getMessage(){
       return "Script named " + scriptName + " failed, and max number of attempts were made.  Details:  " + details;
    }
}
