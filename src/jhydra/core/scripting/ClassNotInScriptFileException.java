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
public class ClassNotInScriptFileException extends FatalException{
    private final String scriptPath;
    
    public ClassNotInScriptFileException(String scriptPath, Exception e){
        super("", e);
        this.scriptPath = scriptPath;
    }
    
    @Override
    public String getMessage(){
        return "Error while attempting to load compiled script at " + scriptPath + "- script file doesn't " +
                "contain the expected class definition (should have the same name as the file name).";
    }
    
}
