/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.scriptinfo;

import jhydra.core.scripting.scriptinfo.exceptions.ScriptInfoLoadException;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import jhydra.core.config.IConfig;
import jhydra.core.scripting.ScriptType;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author jantic
 */
public class ScriptInfoFactory implements IScriptInfoFactory {
    private static Map<String, List<IScriptInfo>> projectPathToScriptInfos = new HashMap<>();
    
    @Override
    public List<IScriptInfo> getAllScriptInfosOfType(IConfig config, ScriptType scriptType) throws ScriptInfoLoadException{
        final String key = generateKey(config.getProjectPath());
        
        if(projectPathToScriptInfos.containsKey(key)){
            return projectPathToScriptInfos.get(key);
        }
        
        final List<IScriptInfo> scriptInfos = new ArrayList<>();
        final String scriptsPath = config.getScriptsPath();       
        final File folder = new File(scriptsPath);
        
        if(!folder.isDirectory()){
            final String message = "Could not find script directory of path: " + scriptsPath;
            throw new ScriptInfoLoadException(message);
        }
        
        final String[] extensions = {scriptType.getExtension()};       
        final Iterator iter = FileUtils.iterateFiles(folder, extensions, true);
        
        while(iter.hasNext()){
            final File file = (File) iter.next();
            final String name = parseName(file.getName(), scriptType);
            final String path = file.getAbsolutePath(); 
            final IScriptInfo scriptInfo = new ScriptInfo(name, scriptType, path);
            scriptInfos.add(scriptInfo);
        }   
        
        projectPathToScriptInfos.put(key, scriptInfos);       
        return scriptInfos;
    }
    
    private String parseName(String fileName, ScriptType scriptType){
        return fileName.replaceAll("\\." + scriptType.getExtension(), "");
    }
    
    private String generateKey(String projectPath){
        if(projectPath == null){
            return "";
        }
        
        return projectPath.trim().toLowerCase();
    }
}
