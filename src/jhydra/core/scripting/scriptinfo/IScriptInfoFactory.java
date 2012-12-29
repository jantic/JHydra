/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.scriptinfo;

import jhydra.core.scripting.scriptinfo.exceptions.ScriptInfoLoadException;
import java.util.List;
import jhydra.core.config.IConfig;
import jhydra.core.scripting.ScriptType;

/**
 *
 * @author jantic
 */
public interface IScriptInfoFactory {

    List<IScriptInfo> getAllScriptInfosOfType(IConfig config, ScriptType scriptType) throws ScriptInfoLoadException;
    
}
