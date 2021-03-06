/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.java;

import jhydra.core.config.IRuntimeConfig;
import jhydra.core.logging.ILog;
import jhydra.core.scripting.*;
import jhydra.core.scripting.exceptions.CompileErrorException;
import jhydra.core.scripting.exceptions.ScriptFatalException;
import jhydra.core.scripting.exceptions.ScriptNotExistException;
import jhydra.core.scripting.scriptinfo.IScriptInfo;
import jhydra.core.scripting.scriptinfo.IScriptInfoFactory;
import jhydra.core.scripting.scriptinfo.ScriptInfoFactory;
import jhydra.core.scripting.scriptinfo.exceptions.ScriptInfoLoadException;
import jhydra.core.uinav.IMasterNavigator;
import jhydra.core.valuemap.IValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author jantic
 */
public class JavaFileScriptFactory implements IScriptFactory {
    private final IRuntimeConfig config;
    private final ILog log;
    private final IScriptInfoFactory scriptInfoFactory;
    private final IScriptFactory masterScriptFactory;
    private final Map<String,IScriptInfo> nameToScriptInfo = new HashMap<>();
    
    public JavaFileScriptFactory(IRuntimeConfig config, ILog log, IScriptFactory masterScriptFactory) throws ScriptInfoLoadException{
        this.config = config;
        this.log = log;
        this.masterScriptFactory = masterScriptFactory;
        this.scriptInfoFactory = new ScriptInfoFactory();
        loadAllScriptInfos();
    }
    
    @Override
    public IScript getScript(String name, IValueMap valueMap, IMasterNavigator navigator)
                throws ScriptFatalException, CompileErrorException{
        final IScriptInfo scriptInfo = getScriptInfo(name);
        final IScriptCompiler compiler = new DynamicJavaCompiler();
        final IBaseScript baseScript = compiler.getCompiledScript(scriptInfo);
        baseScript.initialize(navigator, valueMap, this.masterScriptFactory, this.config, this.log);
        return new RobustScript(baseScript, config, log);
    }

    @Override
    public Boolean hasScript(String name){
        final String key = generateKey(name);
        return nameToScriptInfo.containsKey(key);
    }

    private IScriptInfo getScriptInfo(String name) throws ScriptNotExistException{
        final String key = generateKey(name);
        
        if(nameToScriptInfo.containsKey(key)){
            return nameToScriptInfo.get(key);
        }
        
        throw new ScriptNotExistException(name);
    }
    
    private String generateKey(String scriptName){
        if(scriptName == null){
            return "";
        }
               
        return scriptName.trim().toLowerCase();
    }

    private void loadAllScriptInfos() throws ScriptInfoLoadException{
        final List<IScriptInfo> scriptInfos = scriptInfoFactory.getAllScriptInfosOfType(config, ScriptType.JAVA);
        
        for(IScriptInfo scriptInfo : scriptInfos){
            final String key = generateKey(scriptInfo.getName());
            if(!nameToScriptInfo.containsKey(key)){
                nameToScriptInfo.put(key, scriptInfo);
            }
            else{
                final String message = "Error- two scripts sharing the same name found!  Name: " + scriptInfo.getName();
                throw new ScriptInfoLoadException(message);
            }
        }
    }
}
