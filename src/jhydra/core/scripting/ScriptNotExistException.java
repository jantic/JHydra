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
public class ScriptNotExistException extends FatalException{
    private final String scriptName;
    
    public ScriptNotExistException(String scriptName){
        super("");
        this.scriptName = scriptName;
    }
    
    public String getMessage(){
        return "Script named " + scriptName + " could not be found.";
    }
}
