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
public class DuplicatedKeyException  extends FatalException{
    private final String name;
    private final String filepath;
    
    public DuplicatedKeyException(String name, String filepath){
        super("");
        this.name = name;
        this.filepath = filepath;
    }
    
    @Override
    public String getMessage(){
        return "Config file has duplicated key: '" + name + "', which is not permitted.  Path of " + 
                "file is: " + filepath;
    }
    
}
