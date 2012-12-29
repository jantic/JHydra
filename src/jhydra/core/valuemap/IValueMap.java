/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.valuemap;

import jhydra.core.properties.exceptions.NameNotValidException;
import jhydra.core.lexicon.exceptions.NameNotInLexiconException;

/**
 *
 * @author jantic
 */
public interface IValueMap {

    String getValue(String name) throws NameNotInLexiconException, NameNotValidException;

    boolean hasValue(String name);

    void setValue(String name, String value) throws NameNotInLexiconException, NameNotValidException;
    
}
