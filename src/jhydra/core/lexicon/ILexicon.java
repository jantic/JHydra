/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.lexicon;

import jhydra.core.lexicon.exceptions.NameNotInLexiconException;
import jhydra.core.properties.exceptions.NameNotValidException;
import java.util.List;
import jhydra.core.properties.INameValue;

/**
 *
 * @author jantic
 */
public interface ILexicon {
    List<INameValue> getAllNameDefaultValuePairs();
    void registerVariable(String variableName, String defaultValue);
    String getFilePath();
    INameValue getNameValue(String name) throws NameNotInLexiconException, NameNotValidException;
    Boolean hasNameValue(String name);
}
