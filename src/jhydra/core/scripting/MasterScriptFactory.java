/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

import jhydra.core.scripting.lexicon.IValueMap;
import jhydra.core.uinav.IMasterNavigator;

/**
 *
 * @author jantic
 */
public class MasterScriptFactory implements IScriptFactory{

    @Override
    public IScript getScript(String name, IValueMap valueMap, IMasterNavigator navigator) 
            throws CompileErrorException, ScriptNotExistException, ScriptOutputLoadingException, ScriptInputLoadingException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}