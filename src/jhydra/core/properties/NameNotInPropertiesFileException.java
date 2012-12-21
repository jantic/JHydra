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
public class NameNotInPropertiesFileException extends FatalException{
    private final String name;
    private final String filepath;
    
    public NameNotInPropertiesFileException(String name, String filepath){
        super("");
        this.name = name;
        this.filepath = filepath;
    }
    
    @Override
    public String getMessage(){
       return "Property name not found: " + name + 
               ".  Name must be declared in property file, found here: " + filepath;
    }
}
