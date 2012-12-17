/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.java;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jhydra.core.config.IConfig;
import jhydra.core.logging.ILog;
import jhydra.core.scripting.CompileErrorException;
import jhydra.core.scripting.IScript;
import jhydra.core.scripting.IScriptCompiler;
import jhydra.core.scripting.IScriptFactory;
import jhydra.core.scripting.RobustScript;
import jhydra.core.scripting.ScriptInputLoadingException;
import jhydra.core.scripting.ScriptNotExistException;
import jhydra.core.scripting.ScriptOutputLoadingException;
import jhydra.core.scripting.ScriptType;
import jhydra.core.scripting.lexicon.IValueMap;
import jhydra.core.scripting.scriptinfo.IScriptInfo;
import jhydra.core.scripting.scriptinfo.IScriptInfoFactory;
import jhydra.core.scripting.scriptinfo.ScriptInfoFactory;
import jhydra.core.scripting.scriptinfo.ScriptInfoLoadException;
import jhydra.core.uinav.IMasterNavigator;


/**
 *
 * @author jantic
 */
public class JavaFileScriptFactory implements IScriptFactory {
    private final IConfig config;
    private final ILog log;
    private final IScriptInfoFactory scriptInfoFactory;
    private final Map<String,IScriptInfo> nameToScriptInfo = new HashMap<>();
    
    public JavaFileScriptFactory(IConfig config, ILog log) throws ScriptInfoLoadException{
        this.config = config;
        this.log = log;
        this.scriptInfoFactory = new ScriptInfoFactory();
        loadAllScriptInfos();
    }
    
    @Override
    public IScript getScript(String name, IValueMap valueMap, IMasterNavigator navigator)
                throws CompileErrorException, ScriptNotExistException, 
                ScriptOutputLoadingException, ScriptInputLoadingException{
        final IScriptInfo scriptInfo = getScriptInfo(name);
        final IScriptCompiler compiler = new DynamicJavaCompiler();
        final IScript rawScript = compiler.getCompiledScript(scriptInfo);
        return new RobustScript(rawScript, config, log);
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
