/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.scriptinfo;

import java.util.List;
import jhydra.core.config.IRuntimeConfig;
import jhydra.core.scripting.ScriptType;
import jhydra.core.scripting.scriptinfo.exceptions.ScriptInfoLoadException;

/**
 *
 * @author jantic
 */
public interface IScriptInfoFactory {

    List<IScriptInfo> getAllScriptInfosOfType(IRuntimeConfig config, ScriptType scriptType) throws ScriptInfoLoadException;
    
}
