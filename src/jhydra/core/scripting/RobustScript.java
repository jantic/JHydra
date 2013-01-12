/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

import jhydra.core.config.IRuntimeConfig;
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
    private final IRuntimeConfig config;
    private final ILog log;
    
    public RobustScript(IScript script, IRuntimeConfig config, ILog log){
        this.script = script;
        this.config = config;
        this.log = log;
    }

    @Override
    public void execute() throws RecoverableException, FatalException {  
        final Integer maxNumberOfTries = config.getScriptMaxNumTries();
        Integer numberOfTries = 1;

        while (numberOfTries <= maxNumberOfTries) {
            if(attemptExecution()){return;}
            numberOfTries++;

            if (numberOfTries <= maxNumberOfTries) {
                final String message = "Attempt on script failed.  Attempt number " + numberOfTries.toString() + " coming up.";
                log.error(message);
            }
        }

        final ScriptExecutionException ex = new ScriptExecutionException(this.getName(), "");
        log.error(ex.getMessage());
        throw ex;
    }
    
    private Boolean attemptExecution() throws FatalException{
        final Integer timeInBetweenAttempts = config.getScriptWaitSecondsBetweenAttempts();
        
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
