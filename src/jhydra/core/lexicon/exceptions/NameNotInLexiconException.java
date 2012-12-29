/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.lexicon.exceptions;

import jhydra.core.exceptions.FatalException;

/**
 *
 * @author jantic
 */
public class NameNotInLexiconException extends FatalException{
    private final String name;
    private final String lexiconPath;
    
    public NameNotInLexiconException(String name, String lexiconPath){
        super("");
        this.name = name;
        this.lexiconPath = lexiconPath;
    }
    
    @Override
    public String getMessage(){
       return "Lexicon name not found: " + name + 
               ".  Name must be declared in Lexicon file, found here: " + lexiconPath;
    }
}
