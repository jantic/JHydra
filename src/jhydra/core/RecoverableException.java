/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core;

/**
 *
 * @author jantic
 */
public abstract class RecoverableException extends Exception{
    //Convenience type to consolidate exception handling.
    
    public RecoverableException(String message, Exception e){
        super(message, e);
    }
    
    public RecoverableException(String message){
        super(message);
    } 
}
