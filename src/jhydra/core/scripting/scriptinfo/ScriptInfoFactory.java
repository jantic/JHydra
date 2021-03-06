/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.scriptinfo;

import jhydra.core.config.IRuntimeConfig;
import jhydra.core.scripting.ScriptType;
import jhydra.core.scripting.scriptinfo.exceptions.ScriptInfoLoadException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URI;
import java.util.*;

/**
 *
 * @author jantic
 */
public class ScriptInfoFactory implements IScriptInfoFactory {
    private static final Map<String, List<IScriptInfo>> projectPathToScriptInfos = new HashMap<>();
    
    @Override
    public List<IScriptInfo> getAllScriptInfosOfType(IRuntimeConfig config, ScriptType scriptType)
            throws ScriptInfoLoadException{
        final String key = generateKey(config.getProjectDirectory(), scriptType);
        
        if(projectPathToScriptInfos.containsKey(key)){
            return projectPathToScriptInfos.get(key);
        }
        
        final List<IScriptInfo> scriptInfos = new ArrayList<>();
        final List<URI> scriptDirectories = config.getScriptDirectories();       
      
        for(URI scriptDirectory : scriptDirectories){
            scriptInfos.addAll(getAllScriptInfosOfType(scriptDirectory, scriptType));
        }
        
        projectPathToScriptInfos.put(key, scriptInfos);       
        return scriptInfos;
    }
    
    private List<IScriptInfo> getAllScriptInfosOfType(URI scriptsDirectory, ScriptType scriptType) 
            throws ScriptInfoLoadException{
        final File folder = new File(scriptsDirectory);
                       
        if(!folder.isDirectory()){
            final String message = "Could not find script directory of path: " + scriptsDirectory;
            throw new ScriptInfoLoadException(message);
        }
        
        final String[] extensions = {scriptType.getExtension()};       
        final Iterator iter = FileUtils.iterateFiles(folder, extensions, true);
        final List<IScriptInfo> scriptInfos = new ArrayList<>();
        
        while(iter.hasNext()){
            final File file = (File) iter.next();
            final String name = parseName(file.getName(), scriptType);
            final String path = file.getAbsolutePath(); 
            final IScriptInfo scriptInfo = new ScriptInfo(name, scriptType, path);
            scriptInfos.add(scriptInfo);
        }
        
        return scriptInfos;
    }
    
    private String parseName(String fileName, ScriptType scriptType){
        return fileName.replaceAll("\\." + scriptType.getExtension(), "");
    }
    
    private String generateKey(URI projectPath, ScriptType scriptType){
        if(projectPath == null){
            return "";
        }
        
        return projectPath.toString().trim().toLowerCase() + "." + scriptType.getExtension();
    }
}
