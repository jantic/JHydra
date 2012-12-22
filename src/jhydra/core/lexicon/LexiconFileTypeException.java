/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.lexicon;

import jhydra.core.FatalException;

/**
 *
 * @author jantic
 */
public class LexiconFileTypeException extends FatalException{
    private final String correctExtension;
    private final String attemptedExtension;
    private final String lexiconPath;
    
    public LexiconFileTypeException(String correctExtension, String attemptedExtension, String lexiconPath){
        super("");
        this.correctExtension = correctExtension;
        this.attemptedExtension = attemptedExtension;
        this.lexiconPath = lexiconPath;
    }
    
    @Override
    public String getMessage(){
        return "Lexicon file specified has wrong file extension.  It should be ." + 
                correctExtension + ", yet was ." + attemptedExtension + ".  Path of " + 
                "lexicon file in configuration is: " + lexiconPath;
    }
}
