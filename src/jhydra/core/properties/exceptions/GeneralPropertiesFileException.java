/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.properties.exceptions;

import jhydra.core.exceptions.FatalException;

/**
 *
 * @author jantic
 */
public class GeneralPropertiesFileException extends FatalException{
    private final String filepath;
    private final String details;
    
    public GeneralPropertiesFileException(String filepath, Exception e){
        super("", e);
        this.filepath = filepath;
        this.details = e.getMessage();
    }
    
    @Override
    public String getMessage(){
        return "Error while attempt to read properties file at " + filepath + ": " + details;
    }
}
