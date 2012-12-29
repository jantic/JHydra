/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.exceptions;

/**
 *
 * @author jantic
 */ 
public abstract class FatalException extends Exception {
    //Convenience type so we can consolidate exception handling.
    public FatalException(String message, Exception e){
        super(message, e);
    }
    
    public FatalException(String message){
        super(message);
    }
}
