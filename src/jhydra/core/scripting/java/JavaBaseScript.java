/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.java;

import jhydra.core.config.IProjectConfig;
import jhydra.core.exceptions.FatalException;
import jhydra.core.exceptions.RecoverableException;
import jhydra.core.lexicon.ILexicon;
import jhydra.core.lexicon.exceptions.NameNotInLexiconException;
import jhydra.core.logging.ILog;
import jhydra.core.properties.exceptions.NameNotValidException;
import jhydra.core.scripting.IBaseScript;
import jhydra.core.scripting.IScript;
import jhydra.core.scripting.IScriptFactory;
import jhydra.core.uinav.IMasterNavigator;
import jhydra.core.uinav.selenium.IExtendedSelenium;
import jhydra.core.valuemap.IValueMap;

/**
 *
 * @author jantic
 */
public class JavaBaseScript implements IBaseScript{
    private IMasterNavigator navigator;
    private IValueMap valueMap;
    private IScriptFactory scriptFactory;
    private IProjectConfig config;
    
    //The following are meant to be accessed as is by children scripts.  I just wish Java has C# style properties!
    protected IExtendedSelenium selenium;
    protected ILog log;
    protected Integer timeout;

    public JavaBaseScript(){

    }
    
    //We need setters to keep the construction hidden from the scripts 
    //(i.e. we just want them to deal with a default constructor).
    @Override
    public void setNavigator(IMasterNavigator navigator){
        this.navigator = navigator;
        this.selenium = navigator.getSelenium();
    }
    
    @Override
    public void setValueMap(IValueMap valueMap){
        this.valueMap = valueMap;
    }
    
    @Override
    public void setScriptFactory(IScriptFactory scriptFactory){
        this.scriptFactory = scriptFactory;
    }
    
    @Override
    public void setConfig(IProjectConfig config){
        this.config = config;
        this.timeout = config.getScriptTimeout();
    }
    
    @Override
    public void setLog(ILog log){
        this.log = log;
    }

    @Override
    public void registerVariables(ILexicon registry) {
        //do nothing- just a place holder which is to be overriden by scripts that need this functionality.     
    }

    @Override
    public void execute() throws RecoverableException, FatalException {
        
    }

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }
 
    protected void screenshot() {
        this.navigator.screenshot();
    }

    protected void screenshot(String filename) {
        this.navigator.screenshot(filename);
    }

    protected void executeScript(String scriptName) 
            throws RecoverableException, FatalException {
        final IScript script = this.scriptFactory.getScript(scriptName, valueMap, navigator);
        script.execute();
    }
    
    protected String getValue(String variableName) throws NameNotInLexiconException, NameNotValidException {
        return this.valueMap.getValue(variableName);
    }

    protected void setValue(String variableName, String value) throws NameNotInLexiconException, NameNotValidException {
        this.valueMap.setValue(variableName, value);
    }
}
