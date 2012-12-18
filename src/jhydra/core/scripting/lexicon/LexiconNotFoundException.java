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
public class LexiconNotFoundException extends FatalException{
    private final String lexiconPath;
    
    public LexiconNotFoundException(String lexiconPath){
        super("");
        this.lexiconPath = lexiconPath;
    }
    
    @Override
    public String getMessage(){
        return "Lexicon file could not be found at path: " + this.lexiconPath;
    }
}
