/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.java;

import jhydra.core.FatalException;
import jhydra.core.RecoverableException;
import jhydra.core.config.IConfig;
import jhydra.core.logging.ILog;
import jhydra.core.scripting.IScript;
import jhydra.core.scripting.IScriptFactory;
import jhydra.core.scripting.lexicon.ILexicon;
import jhydra.core.scripting.lexicon.IValueMap;
import jhydra.core.scripting.lexicon.NameNotInLexiconException;
import jhydra.core.properties.NameNotValidException;
import jhydra.core.uinav.IMasterNavigator;
import jhydra.core.uinav.selenium.IExtendedSelenium;

/**
 *
 * @author jantic
 */
public class JavaBaseScript implements IScript{
    private final IMasterNavigator navigator;
    private final IValueMap valueMap;
    private final IScriptFactory scriptFactory;
    private final IConfig config;
    
    //The following are meant to be accessed as is by children scripts.  I just wish Java has C# style properties!
    protected final IExtendedSelenium selenium;
    protected final ILog log;
    protected final Integer timeout;

    public JavaBaseScript(IMasterNavigator navigator, IValueMap valueMap, ILog log, IScriptFactory scriptFactory, IConfig config) {
        this.navigator = navigator;
        this.valueMap = valueMap;
        this.scriptFactory = scriptFactory;
        this.config = config;
       
        this.selenium = navigator.getSelenium();
        this.log = log;
        this.timeout = config.getScriptTimeout();
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
        return this.getClass().getName();
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
