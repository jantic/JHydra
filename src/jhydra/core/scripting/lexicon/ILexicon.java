/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.lexicon;

import java.util.List;

/**
 *
 * @author jantic
 */
public interface ILexicon {
    List<INameValue> getAllNameDefaultValuePairs();
    void registerVariable(String variableName, String defaultValue);
    String getFilePath();
}
