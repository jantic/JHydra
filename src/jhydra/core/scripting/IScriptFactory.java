/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

import jhydra.core.uinav.IMasterNavigator;
import jhydra.core.valuemap.IValueMap;

/**
 *
 * @author jantic
 */
public interface IScriptFactory {

    IScript getScript(String name, IValueMap valueMap, IMasterNavigator navigator) 
            throws CompileErrorException, ScriptNotExistException, ScriptOutputLoadingException, ScriptInputLoadingException, 
            NonPublicScriptClassException, ClassNotInScriptFileException, ScriptInstantiationException;  
}
