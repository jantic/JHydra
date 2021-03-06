/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

import jhydra.core.scripting.exceptions.CompileErrorException;
import jhydra.core.scripting.exceptions.ScriptFatalException;
import jhydra.core.scripting.scriptinfo.IScriptInfo;

/**
 *
 * @author jantic
 */
public interface IScriptCompiler {
    IBaseScript getCompiledScript(IScriptInfo scriptInfo) throws ScriptFatalException, CompileErrorException;  
}
