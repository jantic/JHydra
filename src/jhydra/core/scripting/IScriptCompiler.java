/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

import jhydra.core.scripting.scriptinfo.IScriptInfo;

/**
 *
 * @author jantic
 */
public interface IScriptCompiler {
    IScript getCompiledScript(IScriptInfo scriptInfo) 
        throws CompileErrorException, ScriptOutputLoadingException, ScriptNotExistException, ScriptInputLoadingException;  
}
