/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.properties;

import jhydra.core.FatalException;

/**
 *
 * @author jantic
 */
public class PropertiesFileReadPermissionsException extends FatalException{
    private final String filepath;
    
    public PropertiesFileReadPermissionsException(String filepath){
        super("");
        this.filepath = filepath;
    }
    
    @Override
    public String getMessage(){
        return "Properties file could not be read at: " + filepath + ".  This may be due to lack of read permissions for this application.";
    }
    
}
