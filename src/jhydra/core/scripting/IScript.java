/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

import jhydra.core.exceptions.FatalException;
import jhydra.core.exceptions.RecoverableException;
import jhydra.core.lexicon.ILexicon;

/**
 *
 * @author jantic
 */
public interface IScript {
    public void execute() throws RecoverableException, FatalException;
    public String getName();
}
