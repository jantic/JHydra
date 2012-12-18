/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.lexicon;

import jhydra.core.FatalException;

/**
 *
 * @author jantic
 */
public class LexiconReadException extends FatalException{
    private final String filepath;
    private final String details;
    
    public LexiconReadException(String filepath, Exception e){
        super("", e);
        this.filepath = filepath;
        this.details = e.getMessage();
    }
    
    @Override
    public String getMessage(){
        return "Error while attempting to read Lexicon at path " + this.filepath + ": " + details;
    }
}
