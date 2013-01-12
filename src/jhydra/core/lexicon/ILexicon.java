/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.lexicon;

import java.net.URI;
import java.util.List;
import jhydra.core.lexicon.exceptions.NameNotInLexiconException;
import jhydra.core.properties.INameValue;
import jhydra.core.properties.exceptions.NameNotValidException;

/**
 *
 * @author jantic
 */
public interface ILexicon {
    List<INameValue> getAllNameDefaultValuePairs();
    void registerVariable(String variableName, String defaultValue);
    List<URI> getFilePaths();
    INameValue getNameValue(String name) throws NameNotInLexiconException, NameNotValidException;
    Boolean hasNameValue(String name);
}
