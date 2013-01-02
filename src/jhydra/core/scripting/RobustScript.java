/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

import jhydra.core.config.IProjectConfig;
import jhydra.core.exceptions.FatalException;
import jhydra.core.exceptions.RecoverableException;
import jhydra.core.lexicon.ILexicon;
import jhydra.core.logging.ILog;
import jhydra.core.scripting.exceptions.ScriptExecutionException;

/**
 *
 * @author jantic
 */

/*Used to encapsulate scripts in a more robust execution cycle.
 * Multiple attemps will be made when errors are thrown before giving up
 * and rethrowing errors to the parent test case.
*/

public class RobustScript implements IScript{
    private final IScript script;
    private final IProjectConfig config;
    private final ILog log;
    
    public RobustScript(IScript script, IProjectConfig config, ILog log){
        this.script = script;
        this.config = config;
        this.log = log;
    }

    @Override
    public void registerVariables(ILexicon lexicon) {
        this.script.registerVariables(lexicon);
    }

    @Override
    public void execute() throws RecoverableException, FatalException {  
        final Integer maxNumberOfTries = config.getScriptMaxTries();
        Integer numberOfTries = 1;

        while (numberOfTries <= maxNumberOfTries) {
            final Boolean success = attemptExecution();
            if(success){return;}
            numberOfTries++;

            if (!success && numberOfTries <= maxNumberOfTries) {
                final String message = "Attempt on script failed.  Attempt number " + numberOfTries.toString() + " coming up.";
                log.error(message);
            }
        }

        final ScriptExecutionException ex = new ScriptExecutionException(this.getName(), "");
        log.error(ex.getMessage());
        throw ex;
    }
    
    private Boolean attemptExecution() throws FatalException{
        final Integer timeInBetweenAttempts = config.getScriptTimeBetweenAttempts();
        
        try {
            log.info("\t\tSCRIPT: " + this.getName());
            this.script.execute();
            return true;
        } catch (RecoverableException ex) {
            log.warn(ex.getMessage(), ex);
            waitForNextAttempt(timeInBetweenAttempts * 1000);
            return false;
        } catch (FatalException ex) {
            log.error(ex.getMessage(), ex);
            throw ex;
        }
    }
    
    private void waitForNextAttempt(Integer milliseconds){
        try{
            Thread.sleep(milliseconds);
        }
        catch(Exception e){
            final String message = "Sleep attempt failed: " + e.getMessage();
            log.warn(message, e);
        }
    }

    @Override
    public String getName() {
        return script.getName();
    }
    
}
