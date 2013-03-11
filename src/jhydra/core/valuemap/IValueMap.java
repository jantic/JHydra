/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.valuemap;

import jhydra.core.lexicon.exceptions.NameNotInLexiconException;
import jhydra.core.properties.INameValue;
import jhydra.core.properties.exceptions.NameNotValidException;

import java.util.List;

/**
 *
 * @author jantic
 */
public interface IValueMap {

    String getValue(String name) throws NameNotInLexiconException, NameNotValidException;

    boolean hasValue(String name);

    void setValue(String name, String value) throws NameNotInLexiconException, NameNotValidException;

    void updateValues(List<INameValue> pairs) throws NameNotInLexiconException, NameNotValidException;

}
