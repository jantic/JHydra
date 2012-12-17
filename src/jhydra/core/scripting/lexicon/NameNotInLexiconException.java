/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.lexicon;

import jhydra.core.FatalException;
import jhydra.core.config.IConfig;

/**
 *
 * @author jantic
 */
public class NameNotInLexiconException extends FatalException{
    private final String name;
    private IConfig config;
    
    public NameNotInLexiconException(String name, IConfig config){
        super("");
        this.name = name;
        this.config = config;
    }
    
    @Override
    public String getMessage(){
       return "Lexicon name not found: " + name + 
               ".  Name must be declared in Lexicon file, found here: " + config.getLexiconPath();
    }
}
