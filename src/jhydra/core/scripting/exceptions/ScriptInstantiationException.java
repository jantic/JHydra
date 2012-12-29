/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.exceptions;

/**
 *
 * @author jantic
 */
public class ScriptInstantiationException extends ScriptFatalException{
    private final String scriptPath;
    
    public ScriptInstantiationException(String scriptPath, Exception e){
        super("", e);
        this.scriptPath = scriptPath;
    }
    
    @Override
    public String getMessage(){
        return "Error while attempting to load compiled script at " + scriptPath + 
                " - script class could not be instantiated." + 
                "Make sure that the class is not declared abstract";
    }  
}
