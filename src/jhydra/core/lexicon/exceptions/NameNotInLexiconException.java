/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.lexicon.exceptions;

import jhydra.core.exceptions.FatalException;

import java.net.URI;
import java.util.List;

/**
 *
 * @author jantic
 */
public class NameNotInLexiconException extends FatalException{
    private final String name;
    private final List<URI> lexiconPaths;
    
    public NameNotInLexiconException(String name, List<URI> lexiconPaths){
        super("");
        this.name = name;
        this.lexiconPaths = lexiconPaths;
    }
    
    @Override
    public String getMessage(){
       return "Lexicon name not found: " + name + 
               ".  Name must be declared in one of the following Lexicon files: " +
               generateLexiconPathsString();
    }

    private String generateLexiconPathsString(){
        final StringBuilder sb = new StringBuilder();

        for(URI lexiconPath : lexiconPaths){
            sb.append(lexiconPath.toString());
        }

        return sb.toString();
    }
}
