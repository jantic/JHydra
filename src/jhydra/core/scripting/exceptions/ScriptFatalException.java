/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.exceptions;

import jhydra.core.exceptions.FatalException;

/**
 *
 * @author jantic
 */
public abstract class ScriptFatalException extends FatalException {
    //Convenience type so we can consolidate exception handling.
    public ScriptFatalException(String message, Exception e){
        super(message, e);
    }
    
    public ScriptFatalException(String message){
        super(message);
    }
}
