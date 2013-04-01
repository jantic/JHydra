/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

import jhydra.core.config.IRuntimeConfig;
import jhydra.core.exceptions.FatalException;
import jhydra.core.exceptions.RecoverableException;
import jhydra.core.logging.ILog;
import jhydra.core.scripting.exceptions.ScriptExecutionException;
import jhydra.core.scripting.exceptions.ScriptOtherFatalException;
import sun.reflect.Reflection;

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
        final Integer timeInBetweenAttempts = config.getScriptWaitSecondsBetweenAttempts();
        Integer numberOfTries = 1;

        while (numberOfTries <= maxNumberOfTries) {
            if(attemptExecution()){return;}
            numberOfTries++;

            if (numberOfTries <= maxNumberOfTries) {
                final String message = "Attempt on script failed.  Attempt number " + numberOfTries.toString() +
                        " coming up, after a " + timeInBetweenAttempts.toString() + " second pause.";

                log.warn(message);
                waitForNextAttempt(timeInBetweenAttempts * 1000);
            }
        }

        final ScriptExecutionException ex = new ScriptExecutionException(this.getName(), "");
        log.error(ex.getMessage());
        throw ex;
    }
    
    private Boolean attemptExecution() throws FatalException, RecoverableException{
        try {
            log.info("\t\tSCRIPT: " + this.getName());
            this.script.execute();
            return true;
        } catch (ScriptExecutionException ex){
            //We only want to retry on RecoverableExceptions that are not ScriptExecutionExceptions,
            // because we don't want these retries cascading to each script in the parent call chain.
            // Instead, only the parent test case itself shall retry based on this exception.
            throw ex;
        } catch (RecoverableException ex) {
            //Only retry in this case if this script is embedded in another script.  Otherwise, the script
            //is being called directly by a test case, and that test case itself will be retried.
            if(isParentCallingObjectAScript()){
                log.warn(ex.getMessage(), ex);
                return false;
            }
            else{
                log.error(ex.getMessage(), ex);
                throw new ScriptExecutionException(this.getName(), ex.getMessage(), ex);
            }
        } catch (FatalException ex) {
            logFatalException(ex);
            throw ex;
        } catch (Exception ex){
            //Default to fatal, as no other categorization can be assumed at this point.
            logFatalException(ex);
            throw new ScriptOtherFatalException(this.getName(), ex);
        } catch (Throwable ex){
            //Default to fatal, as no other categorization can be assumed at this point.
            logFatalError(ex);
            throw new ScriptOtherFatalException(this.getName(), ex);
        }
    }

    private void logFatalException(Exception ex){
        final String message = "Script named " + this.getName() + " encountered a fatal error: " + ex.getMessage();
        log.error(message, ex);
    }

    private void logFatalError(Throwable ex){
        final String message = "Script named " + this.getName() + " encountered a fatal error: " + ex.getMessage();
        log.error(message);
    }

    private Boolean isParentCallingObjectAScript(){
        final StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        //Iterate up the stack until the calling object isn't this one
        Boolean executeMethodFound = false;

        for(int index = 0; index < stackTraceElements.length; index++){
            final StackTraceElement stackTraceElement = stackTraceElements[index];

            if(executeMethodFound){
                return containsScriptInterface(Reflection.getCallerClass(index).getInterfaces());
            }
            if(stackTraceElement.getMethodName().equalsIgnoreCase("execute")){
                executeMethodFound = true;
            }

        }

        return false;
    }

    private Boolean containsScriptInterface(Class<?>[] classes){
       for(Class<?> classObject : classes){
           if(classObject == IScript.class){
               return true;
           }
       }

        return false;
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
