/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.java;

import jhydra.core.config.IRuntimeConfig;
import jhydra.core.exceptions.FatalException;
import jhydra.core.exceptions.RecoverableException;
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
    private IRuntimeConfig config;
    
    //The following are meant to be accessed as is by children scripts.  I just wish Java has C# style properties!
    protected IExtendedSelenium selenium;
    protected ILog log;
    protected Integer timeout;

    public JavaBaseScript(){

    }

    //We need this initialization function to keep the construction hidden from the scripts
    //(i.e. we just want them to deal with a default constructor).

    public void initialize(IMasterNavigator navigator, IValueMap valueMap, IScriptFactory scriptFactory,
                           IRuntimeConfig config, ILog log){
        this.navigator = navigator;
        this.selenium = navigator.getSelenium();
        this.valueMap = valueMap;
        this.scriptFactory = scriptFactory;
        this.config = config;
        this.timeout = config.getScriptTimeoutSeconds();
        this.log = log;
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
