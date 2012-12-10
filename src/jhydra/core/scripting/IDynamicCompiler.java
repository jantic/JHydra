/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

import jhydra.core.scripting.IBaseScript;

/**
 *
 * @author jantic
 */
public interface IDynamicCompiler {

    void compile(String fileName, String className) throws Exception;

    IBaseScript getScript(String fileName, String className) throws Exception;
    
}
