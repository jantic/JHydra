/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.scriptinfo;

import jhydra.core.scripting.ScriptType;

/**
 *
 * @author jantic
 */
public interface IScriptInfo {
    String getName();
    String getClassName();
    String getFilePath();
    ScriptType getType();    
}
