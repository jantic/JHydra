/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.scriptinfo;

import jhydra.core.scripting.ScriptType;
import jhydra.core.scripting.scriptinfo.IScriptInfo;

/**
 *
 * @author jantic
 */
public class ScriptInfo implements IScriptInfo{
    
    private final String name;
    private final ScriptType scriptType;
    private final String filepath;
    
    public ScriptInfo(String name, ScriptType scriptType, String filepath){
        this.name = name;
        this.scriptType = scriptType;
        this.filepath = filepath;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getClassName() {
        return "jhydra.scripts." + name;   
    }

    @Override
    public String getFilePath() {
        return this.filepath;
    }

    @Override
    public ScriptType getType() {
        return this.scriptType;
    }
    
}
