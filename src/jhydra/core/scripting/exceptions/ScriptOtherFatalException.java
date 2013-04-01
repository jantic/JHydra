package jhydra.core.scripting.exceptions;

/**
 * Author: jantic
 * Date: 3/29/13
 */
public class ScriptOtherFatalException extends ScriptFatalException{
    private final String scriptName;
    private final String details;

    public ScriptOtherFatalException(String scriptName, Throwable e){
        super("");
        this.scriptName = scriptName;
        this.details = e.getMessage();
    }

    public ScriptOtherFatalException(String scriptName, Exception e){
        super("", e);
        this.scriptName = scriptName;
        this.details = e.getMessage();
    }

    @Override
    public String getMessage(){
        return "Error while attempting to load compiled script named " + scriptName + " from compiler output: " + details;
    }
}
