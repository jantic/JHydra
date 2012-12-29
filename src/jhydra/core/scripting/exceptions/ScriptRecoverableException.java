/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.exceptions;

import jhydra.core.exceptions.RecoverableException;

/**
 *
 * @author jantic
 */
public abstract class ScriptRecoverableException extends RecoverableException{
    //Convenience type to consolidate exception handling.
    
    public ScriptRecoverableException(String message, Exception e){
        super(message, e);
    }
    
    public ScriptRecoverableException(String message){
        super(message);
    } 
}
