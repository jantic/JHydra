/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.exceptions;

/**
 *
 * @author jantic
 */
public abstract class RecoverableException extends Exception{
    //Convenience type to consolidate exception handling.
    
    protected RecoverableException(String message, Exception e){
        super(message, e);
    }
    
    protected RecoverableException(String message){
        super(message);
    } 
}
