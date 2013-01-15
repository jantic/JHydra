/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.config.exceptions;


import jhydra.core.exceptions.FatalException;

/**
 *
 * @author jantic
 */
public class InvalidProjectNameException extends FatalException {
    private final String name;

    public InvalidProjectNameException(String name){
        super("");
        this.name = name;
    }
    
    public String getMessage(){
        return "Project named " + name + " could not be found.";
    }
}
