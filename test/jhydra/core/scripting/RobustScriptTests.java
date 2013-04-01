package jhydra.core.scripting;

import jhydra.core.config.IRuntimeConfig;
import jhydra.core.exceptions.FatalException;
import jhydra.core.exceptions.RecoverableException;
import jhydra.core.logging.ILog;
import jhydra.core.scripting.exceptions.ClassNotInScriptFileException;
import jhydra.core.scripting.exceptions.ScriptExecutionException;
import jhydra.core.scripting.exceptions.ScriptNavigationException;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Author: jantic
 * Date: 3/29/13
 */
public class RobustScriptTests {

    /***Tests normally executing script ****************************************/
    @Test
    public void normallyExecutingSingleInnerScript_execute_runElapsedTime_LessThanSecond() throws FatalException, RecoverableException{
        final RobustScript script = getNormallyExecutingSingleInnerScript();
        final Boolean expected = true;
        final DateTime startTime = DateTime.now();
        script.execute();
        final DateTime endTime = DateTime.now();
        final Interval interval = new Interval(startTime, endTime);
        final Long duration = interval.toDurationMillis();
        final Boolean actual = duration < 1000;
        Assert.assertEquals(expected, actual);
    }

    /***Tests abnormally (but recoverable) exiting script ****************************************/
    @Test(expected = ScriptExecutionException.class)
    public void recoverableAbnormallyExitingSingleInnerScript_execute_throwsRecoverableException() throws FatalException, RecoverableException{
        final RobustScript script = getRecoverableAbnormallyExitingSingleInnerScript();
        script.execute();
    }

    //Note:  Should NOT retry, because it's a single script with no parent script and therefore its parent
    //test case would just be retried instead.
    @Test
    public void recoverableAbnormallyExitingSingleInnerScript_execute_runElapsedTime_LessThanOneSecond() throws FatalException, RecoverableException{
        final RobustScript script = getRecoverableAbnormallyExitingSingleInnerScript();
        final Boolean expected = true;
        final DateTime startTime = DateTime.now();

        try{
            script.execute();
        }
        catch(RecoverableException e){
            //ignore for testing purposes.
        }

        final DateTime endTime = DateTime.now();
        final Interval interval = new Interval(startTime, endTime);
        final Long duration = interval.toDurationMillis();
        //1 attempt, no pause
        final Boolean actual = duration < 1000;
        Assert.assertEquals(expected, actual);
    }


    //NOTE:  We want only the most immediate error throwing script to retry.  We don't want
    //all the calling scripts to retry as well.  This tests that behavior
    @Test
    public void recoverableAbnormallyExitingMultipleInnerScripts_execute_runElapsedTime_OverTwoSeconds() throws FatalException, RecoverableException{
        final RobustScript script = getRecoverableAbnormallyExitingMultipleInnerScripts();
        final Boolean expected = true;
        final DateTime startTime = DateTime.now();

        try{
            script.execute();
        }
        catch(RecoverableException e){
            //ignore for testing purposes.
        }

        final DateTime endTime = DateTime.now();
        final Interval interval = new Interval(startTime, endTime);
        final Long duration = interval.toDurationMillis();
        //3 attempts expected, 2 of which are followed by 1 second pauses.
        final Boolean actual = duration >= 2000 && duration < 3000;
        Assert.assertEquals(expected, actual);
    }

    /***Tests abnormally (non-recoverable) exiting script ****************************************/
    //Note:  Should NOT retry!
    @Test(expected = ClassNotInScriptFileException.class)
    public void nonRecoverableAbnormallyExitingSingleInnerScript_execute_throwsInnerScriptsException() throws FatalException, RecoverableException{
        final RobustScript script = getNonRecoverableAbnormallyExitingSingleInnerScript();
        script.execute();
    }

    @Test
    public void nonRecoverableAbnormallyExitingSingleInnerScript_execute_runElapsedTime_LessThanSecond() throws FatalException, RecoverableException{
        final RobustScript script = getNonRecoverableAbnormallyExitingSingleInnerScript();
        final Boolean expected = true;
        final DateTime startTime = DateTime.now();
        try{
            script.execute();
        }
        catch(FatalException e){
            //just ignore for testing purposes.
        }
        final DateTime endTime = DateTime.now();
        final Interval interval = new Interval(startTime, endTime);
        final Long duration = interval.toDurationMillis();
        final Boolean actual = duration < 1000;
        Assert.assertEquals(expected, actual);
    }


    //Should NOT retry!
    @Test
    public void nonRecoverableAbnormallyExitingMultipleInnerScripts_execute_runElapsedTime_LessThanSecond() throws FatalException, RecoverableException{
        final RobustScript script = getNonRecoverableAbnormallyExitingMultipleInnerScripts();
        final Boolean expected = true;
        final DateTime startTime = DateTime.now();

        try{
            script.execute();
        }
        catch(FatalException e){
            //ignore for testing purposes.
        }

        final DateTime endTime = DateTime.now();
        final Interval interval = new Interval(startTime, endTime);
        final Long duration = interval.toDurationMillis();
        //3 attempts expected, 2 of which are followed by 1 second pauses.
        final Boolean actual = duration < 1000;
        Assert.assertEquals(expected, actual);
    }


    /*****************************************************************************************/
    /***PRIVATE METHODS***********************************************************************/
    /*****************************************************************************************/

    private RobustScript getNormallyExecutingSingleInnerScript(){
        final IRuntimeConfig config = getNormalRuntimeConfig();
        final ILog log = mock(ILog.class);
        final RobustScript innerScript = getNormallyExecutingScript();
        return new RobustScript(innerScript, config, log);
    }

    private IRuntimeConfig getNormalRuntimeConfig(){
        final IRuntimeConfig config = mock(IRuntimeConfig.class);
        when(config.getScriptMaxNumTries()).thenReturn(3);
        when(config.getScriptWaitSecondsBetweenAttempts()).thenReturn(1);
        when(config.getScriptTimeoutSeconds()).thenReturn(2);
        return config;
    }

    private RobustScript getNormallyExecutingScript(){
        return mock(RobustScript.class);
    }


    private RobustScript getRecoverableAbnormallyExitingSingleInnerScript() throws RecoverableException, FatalException {
        final IRuntimeConfig config = getNormalRuntimeConfig();
        final ILog log = mock(ILog.class);
        final RobustScript innerScript = getRecoverableAbnormallyExitingScript();
        return new RobustScript(innerScript, config, log);
    }

    private RobustScript getRecoverableAbnormallyExitingMultipleInnerScripts() throws RecoverableException, FatalException {
        final IRuntimeConfig config = getNormalRuntimeConfig();
        final ILog log = mock(ILog.class);
        final RobustScript innerScript = getRecoverableAbnormallyExitingScript();
        final RobustScript middleScript1 = new RobustScript(innerScript, config, log);
        final RobustScript middleScript2 = new RobustScript(middleScript1, config, log);
        return new RobustScript(middleScript2, config, log);
    }

    private RobustScript getRecoverableAbnormallyExitingScript() throws RecoverableException, FatalException {
        final RobustScript script = mock(RobustScript.class);
        Mockito.doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) throws ScriptNavigationException {
                recoverableAbnormalExitScriptExecute();
                return null;
            }
        }).when(script).execute();

        return script;
    }

    private void recoverableAbnormalExitScriptExecute() throws ScriptNavigationException {
        throw new ScriptNavigationException("These are the failure details.");
    }

    private RobustScript getNonRecoverableAbnormallyExitingMultipleInnerScripts() throws RecoverableException, FatalException {
        final IRuntimeConfig config = getNormalRuntimeConfig();
        final ILog log = mock(ILog.class);
        final RobustScript innerScript = getNonRecoverableAbnormallyExitingScript();
        final RobustScript middleScript1 = new RobustScript(innerScript, config, log);
        final RobustScript middleScript2 = new RobustScript(middleScript1, config, log);
        return new RobustScript(middleScript2, config, log);
    }


    private RobustScript getNonRecoverableAbnormallyExitingSingleInnerScript() throws RecoverableException, FatalException {
        final IRuntimeConfig config = getNormalRuntimeConfig();
        final ILog log = mock(ILog.class);
        final RobustScript innerScript = getNonRecoverableAbnormallyExitingScript();
        return new RobustScript(innerScript, config, log);
    }

    private RobustScript getNonRecoverableAbnormallyExitingScript() throws RecoverableException, FatalException {
        final RobustScript script = mock(RobustScript.class);
        Mockito.doAnswer(new Answer() {
            public Object answer(InvocationOnMock invocation) throws ClassNotInScriptFileException {
                nonRecoverableAbnormalExitScriptExecute();
                return null;
            }
        }).when(script).execute();

        return script;
    }

    private void nonRecoverableAbnormalExitScriptExecute() throws ClassNotInScriptFileException {
        throw new ClassNotInScriptFileException("These are the failure details.", new Exception());
    }
}
