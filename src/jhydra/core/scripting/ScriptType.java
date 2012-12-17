/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

/**
 *
 * @author jantic
 */
public class ScriptType {
    private final String name;
    private final String extension;
    
    public static final ScriptType JAVA = new ScriptType("Java", "java");
    public static final ScriptType JYTHON = new ScriptType("Jython", "py");
    
    private ScriptType(String name, String extension){
        this.name = name;
        this.extension = extension;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getExtension(){
        return this.extension;
    }
}
