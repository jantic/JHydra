/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.java;

import jhydra.core.scripting.IScriptFactory;
import jhydra.core.scripting.IBaseScript;
import jhydra.core.scripting.IDynamicCompiler;


/**
 *
 * @author jantic
 */
public class JavaFileScriptFactory implements IScriptFactory {
    @Override
    public IBaseScript getScript(String name) throws Exception{
        final String fileName = generateFileName(name);
        final String className = generateClassName(name);
        final IDynamicCompiler compiler = new DynamicJavaCompiler();
        final IBaseScript script = compiler.getScript(fileName, className);
        return script;
    }

    private String generateClassName(String name){
        return "jhydra.scripts." + name;
    }
    
    private String generateFileName(String name){
        return "scripts/" + name + ".java";
    }
}
