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
public class LogDirectoryCreationException extends FatalException{
    private final String logDirectory;
    private final String details;
    
    public LogDirectoryCreationException(String logDirectory, Exception e){
        super("", e);
        this.logDirectory = logDirectory;
        this.details = e.getMessage();
    }
    
    @Override
    public String getMessage(){
        return "Error while attempting to establish logging directory at " + logDirectory + "- " + details;
    }  
}
