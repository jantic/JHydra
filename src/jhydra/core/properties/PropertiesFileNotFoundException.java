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
public class PropertiesFileNotFoundException  extends FatalException{
    private final String filepath;
    
    public PropertiesFileNotFoundException(String filepath){
        super("");
        this.filepath = filepath;
    }
    
    @Override
    public String getMessage(){
        return "Properties file not found at: " + filepath;
    }
    
}
