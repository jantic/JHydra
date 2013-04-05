/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

import jhydra.core.config.IRuntimeConfig;
import jhydra.core.logging.ILog;
import jhydra.core.scripting.exceptions.CompileErrorException;
import jhydra.core.scripting.exceptions.ScriptFatalException;
import jhydra.core.scripting.exceptions.ScriptNotExistException;
import jhydra.core.scripting.java.JavaFileScriptFactory;
import jhydra.core.scripting.scriptinfo.exceptions.ScriptInfoLoadException;
import jhydra.core.uinav.IMasterNavigator;
import jhydra.core.valuemap.IValueMap;

/**
 *
 * @author jantic
 *
 * SUMMARY:  Scripts are loaded and resolved here.  They can potentially be of any language supported by the vm.
 * We're starting with dynamically loaded Java files, with support for Jython planned in the future.
 * The scripts must have a name unique per project, regardless of language they're written in.
 */
public class MasterScriptFactory implements IScriptFactory{
    private final ILog log;
    private final JavaFileScriptFactory javaFileScriptFactory;

    public MasterScriptFactory(IRuntimeConfig config, ILog log) throws ScriptInfoLoadException {
        this.log = log;
        this.javaFileScriptFactory = new JavaFileScriptFactory(config, log, this);
    }

    @Override
    public IScript getScript(String name, IValueMap valueMap, IMasterNavigator navigator)
            throws CompileErrorException, ScriptFatalException {
        if(javaFileScriptFactory.hasScript(name)){
            return javaFileScriptFactory.getScript(name, valueMap, navigator);
        }

        final String message = "Error- Attempt to load script named " + name + " failed- script could not be found.";
        log.error(message);
        throw new ScriptNotExistException(name);
    }

    @Override
    public Boolean hasScript(String name) {
        return javaFileScriptFactory.hasScript(name);
    }

}
