/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.scriptinfo.exceptions;

import jhydra.core.exceptions.FatalException;

/**
 *
 * @author jantic
 */
public class ScriptInfoLoadException extends FatalException{
   
    public ScriptInfoLoadException(String message){
        super(message);
    }   
}
