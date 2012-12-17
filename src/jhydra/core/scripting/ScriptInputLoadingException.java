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
public class ScriptInputLoadingException extends FatalException{
    private final String scriptName;
    private final String fileName;
    private final String details;
    
    public ScriptInputLoadingException(String fileName, String scriptName, String details){
        super("");
        this.fileName = fileName;
        this.scriptName = scriptName;
        this.details = details;
    }
    
    public ScriptInputLoadingException(String fileName, String scriptName, Exception e){
        super("", e);
        this.fileName= fileName;
        this.scriptName = scriptName;
        this.details = e.getMessage();
    }
    
    @Override
    public String getMessage(){
        return "Error while attempting to load script file named " + scriptName + " at path " + fileName + ": " +details;
    }
}
