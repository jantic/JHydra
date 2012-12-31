/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.logging;

import jhydra.core.exceptions.FatalException;

/**
 *
 * @author jantic
 */
public class LogDirectoryAccessDeniedException extends FatalException{
    private final String logDirectory;
    
    public LogDirectoryAccessDeniedException(String logDirectory){
        super("");
        this.logDirectory = logDirectory;
    }
    
    @Override
    public String getMessage(){
        return "Error while attempting to establish logging directory at " + logDirectory + "- access denied.  Check " + 
                "write permissions for this directory.";
    } 
}
