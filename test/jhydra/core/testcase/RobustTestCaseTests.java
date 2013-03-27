package jhydra.core.testcase;

import jhydra.core.config.IRuntimeConfig;
import jhydra.core.exceptions.FatalException;
import jhydra.core.exceptions.RecoverableException;
import jhydra.core.lexicon.ILexicon;
import jhydra.core.logging.ILog;
import jhydra.core.properties.INameValue;
import jhydra.core.properties.NameValue;
import jhydra.core.scripting.IScript;
import jhydra.core.scripting.IScriptFactory;
import jhydra.core.scripting.exceptions.ScriptInputLoadingException;
import jhydra.core.scripting.exceptions.ScriptNavigationException;
import jhydra.core.uinav.IMasterNavigator;
import jhydra.core.valuemap.IValueMap;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Author: jantic
 * Date: 3/26/13
 */
public class RobustTestCaseTests {

    /***Tests normally executing test case that passes****************************************/
    @Test
    public void normalPassingTestCase_getDescription_correctDescription() throws FatalException, RecoverableException{
        final RobustTestCase testCase = getNormalPassingTestCase();
        final String expected = "normal test description";
        final String actual = testCase.getDescription();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_getName_correctName() throws FatalException, RecoverableException{
        final RobustTestCase testCase = getNormalPassingTestCase();
        final String expected = "normal test name";
        final String actual = testCase.getName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_getTestNumber_1() throws FatalException, RecoverableException{
        final RobustTestCase testCase = getNormalPassingTestCase();
        final Integer expected = 1;
        final Integer actual = testCase.getTestNumber();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getResultCategory_pass() throws FatalException, RecoverableException{
        final RobustTestCase testCase = getNormalPassingTestCase();
        final String expected = "pass";
        final String actual = testCase.execute().getResultCategory().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getAllTestValueResults_0_testValueResultType_PASS() throws FatalException, RecoverableException{
        final RobustTestCase testCase = getNormalPassingTestCase();
        final String expected = "PASS";
        final String actual = testCase.execute().getAllTestValueResults().get(0).getTestValueResultType().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getAllTestValueResults_0_getComments_correctComment() throws FatalException, RecoverableException{
        final RobustTestCase testCase = getNormalPassingTestCase();
        final String expected = "comment 1";
        final String actual = testCase.execute().getAllTestValueResults().get(0).getComments();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getAllTestValueResults_0_getVariableName_testValueName1() throws FatalException, RecoverableException{
        final RobustTestCase testCase = getNormalPassingTestCase();
        final String expected = "testValueName1";
        final String actual = testCase.execute().getAllTestValueResults().get(0).getVariableName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getAllTestValueResults_0_getActualValue_correctValue() throws FatalException, RecoverableException{
        final RobustTestCase testCase = getNormalPassingTestCase();
        final String expected = "actual value 1";
        final String actual = testCase.execute().getAllTestValueResults().get(0).getActualValue();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getAllTestValueResults_0_getExpectedValue_correctValue() throws FatalException, RecoverableException{
        final RobustTestCase testCase = getNormalPassingTestCase();
        final String expected = "expected value 1";
        final String actual = testCase.execute().getAllTestValueResults().get(0).getExpectedValue();
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void normalPassingTestCase_execute_getAllTestValueResults_1_testValueResultType_KNOWN() throws FatalException, RecoverableException{
        final RobustTestCase testCase = getNormalPassingTestCase();
        final String expected = "KNOWN";
        final String actual = testCase.execute().getAllTestValueResults().get(1).getTestValueResultType().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getAllTestValueResults_1_getComments_correctComment() throws FatalException, RecoverableException{
        final RobustTestCase testCase = getNormalPassingTestCase();
        final String expected = "comment 2";
        final String actual = testCase.execute().getAllTestValueResults().get(1).getComments();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getAllTestValueResults_1_getVariableName_testValueName1() throws FatalException, RecoverableException{
        final RobustTestCase testCase = getNormalPassingTestCase();
        final String expected = "testValueName2";
        final String actual = testCase.execute().getAllTestValueResults().get(1).getVariableName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getAllTestValueResults_1_getActualValue_correctValue() throws FatalException, RecoverableException{
        final RobustTestCase testCase = getNormalPassingTestCase();
        final String expected = "actual value 2";
        final String actual = testCase.execute().getAllTestValueResults().get(1).getActualValue();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getAllTestValueResults_1_getExpectedValue_correctValue() throws FatalException, RecoverableException{
        final RobustTestCase testCase = getNormalPassingTestCase();
        final String expected = "expected value 2";
        final String actual = testCase.execute().getAllTestValueResults().get(1).getExpectedValue();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getErrorMessages_size0() throws FatalException, RecoverableException{
        final RobustTestCase testCase = getNormalPassingTestCase();
        final Integer expected = 0;
        final Integer actual = testCase.execute().getErrorMessages().size();
        Assert.assertEquals(expected, actual);
    }

    @Test
         public void normalPassingTestCase_execute_runCompletedTime_greaterThan_runStartTime() throws FatalException, RecoverableException{
        final RobustTestCase testCase = getNormalPassingTestCase();
        final Boolean expected = true;
        final ITestCaseResult testCaseResult = testCase.execute();
        final Boolean actual = testCaseResult.getRunCompletedTime().compareTo(testCaseResult.getRunStartTime()) > 0;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_runElapsedTime_LessThanSecond() throws FatalException, RecoverableException{
        final RobustTestCase testCase = getNormalPassingTestCase();
        final Boolean expected = true;
        final DateTime startTime = DateTime.now();
        testCase.execute();
        final DateTime endTime = DateTime.now();
        final Interval interval = new Interval(startTime, endTime);
        final Boolean actual = interval.toDurationMillis() < 1000;
        Assert.assertEquals(expected, actual);
    }

    /***Tests normally executing test case that fails on values****************************************/
    //Note:  Should NOT retry!
    @Test
    public void normalValueFailingTestCase_execute_runElapsedTime_LessThanSecond() throws FatalException, RecoverableException{
        final RobustTestCase testCase = getNormalValueFailingTestCase();
        final Boolean expected = true;
        final DateTime startTime = DateTime.now();
        testCase.execute();
        final DateTime endTime = DateTime.now();
        final Interval interval = new Interval(startTime, endTime);
        final Boolean actual = interval.toDurationMillis() < 1000;
        Assert.assertEquals(expected, actual);
    }


    /***Tests abnormally (but recoverable) exiting test case****************************************/
    //Note:  Should retry!
    @Test
    public void recoverableAbnormalExitingTestCase_execute_runElapsedTime_OverThreeSeconds() throws FatalException, RecoverableException{
        final RobustTestCase testCase = getRecoverableAbnormalExitingTestCase();
        final Boolean expected = true;
        final DateTime startTime = DateTime.now();
        testCase.execute();
        final DateTime endTime = DateTime.now();
        final Interval interval = new Interval(startTime, endTime);
        final Long duration = interval.toDurationMillis();
        //3 attempts expected, two of which are followed by 1 second pauses.
        final Boolean actual = duration >= 2000 && duration < 3000;
        Assert.assertEquals(expected, actual);
    }

    /***Tests abnormally (non-recoverable) exiting test case****************************************/
    //Note:  Should NOT retry!
    @Test
    public void nonRecoverableAbnormalExitingTestCase_execute_runElapsedTime_LessThanSecond() throws FatalException, RecoverableException{
        final RobustTestCase testCase = getNonRecoverableAbnormalExitingTestCase();
        final Boolean expected = true;
        final DateTime startTime = DateTime.now();
        testCase.execute();
        final DateTime endTime = DateTime.now();
        final Interval interval = new Interval(startTime, endTime);
        final Long duration = interval.toDurationMillis();
        final Boolean actual = duration < 1000;
        Assert.assertEquals(expected, actual);
    }

    /*****************************************************************************************/
    /***PRIVATE METHODS***********************************************************************/
    /*****************************************************************************************/

    /****Normal Passing Test Case**********************************************/
    private RobustTestCase getNormalPassingTestCase() throws FatalException, RecoverableException {
        final ITestInfo testInfo = getNormalTestInfo();
        final IScriptFactory scriptFactory = getNormalScriptFactory();
        final String entryScriptName = "script1";
        final ILexicon lexicon = getNormalLexicon();
        final IMasterNavigator masterNavigator = getNormalMasterNavigator();
        final ITestDataAnalyzer testDataAnalyzer = getNormalPassingTestDataAnalyzer();
        final ILog log = mock(ILog.class);
        final ITestCase testCase = new TestCase(testInfo, scriptFactory, entryScriptName, lexicon, masterNavigator, testDataAnalyzer, log);
        final IRuntimeConfig config = getNormalRuntimeConfig();
        return new RobustTestCase(testCase, config, log);
    }

    private IRuntimeConfig getNormalRuntimeConfig(){
        final IRuntimeConfig config = mock(IRuntimeConfig.class);
        when(config.getTestCaseMaxNumTries()).thenReturn(3);
        when(config.getTestCaseWaitSecondsBetweenAttempts()).thenReturn(1);
        return config;
    }

    private ITestDataAnalyzer getNormalPassingTestDataAnalyzer() throws FatalException{
        final ITestDataAnalyzer testDataAnalyzer = mock(ITestDataAnalyzer.class);
        final List<INameValue> inputs = new ArrayList<>();
        inputs.add(NameValue.getInstance("inputName1", "input value 1"));
        inputs.add(NameValue.getInstance("inputName2", "input value 2"));
        inputs.add(NameValue.getInstance("inputName3", "input value 3"));
        when(testDataAnalyzer.getAllTestInputs()).thenReturn(inputs);
        final List<TestValueResult> results = new ArrayList<>();
        results.add(TestValueResult.getInstance("expected value 1", "actual value 1", TestValueResultType.PASS, "testValueName1", "comment 1"));
        results.add(TestValueResult.getInstance("expected value 2", "actual value 2", TestValueResultType.KNOWN, "testValueName2", "comment 2"));
        results.add(TestValueResult.getInstance("expected value 3", "actual value 3", TestValueResultType.PASS, "testValueName3", "comment 3"));
        when(testDataAnalyzer.getValueResults(anyListOf(INameValue.class))).thenReturn(results);
        return testDataAnalyzer;
    }

    /****Normal Value Failing Test Case************************************/
    private RobustTestCase getNormalValueFailingTestCase() throws FatalException, RecoverableException {
        final ITestInfo testInfo = getNormalTestInfo();
        final IScriptFactory scriptFactory = getNormalScriptFactory();
        final String entryScriptName = "script1";
        final ILexicon lexicon = getNormalLexicon();
        final IMasterNavigator masterNavigator = getNormalMasterNavigator();
        final ITestDataAnalyzer testDataAnalyzer = getNormalFailingTestDataAnalyzer();
        final ILog log = mock(ILog.class);
        final ITestCase testCase = new TestCase(testInfo, scriptFactory, entryScriptName, lexicon, masterNavigator, testDataAnalyzer, log);
        final IRuntimeConfig config = getNormalRuntimeConfig();
        return new RobustTestCase(testCase, config, log);
    }

    private ITestDataAnalyzer getNormalFailingTestDataAnalyzer() throws FatalException{
        final ITestDataAnalyzer testDataAnalyzer = mock(ITestDataAnalyzer.class);
        final List<INameValue> inputs = new ArrayList<>();
        inputs.add(NameValue.getInstance("inputName1", "input value 1"));
        inputs.add(NameValue.getInstance("inputName2", "input value 2"));
        inputs.add(NameValue.getInstance("inputName3", "input value 3"));
        when(testDataAnalyzer.getAllTestInputs()).thenReturn(inputs);
        final List<TestValueResult> results = new ArrayList<>();
        results.add(TestValueResult.getInstance("expected value 1", "actual value 1", TestValueResultType.PASS, "testValueName1", "comment 1"));
        results.add(TestValueResult.getInstance("expected value 2", "actual value 2", TestValueResultType.KNOWN, "testValueName2", "comment 2"));
        results.add(TestValueResult.getInstance("expected value 3", "actual value 3", TestValueResultType.FAIL, "testValueName3", "comment 3"));
        results.add(TestValueResult.getInstance("expected value 4", "actual value 4", TestValueResultType.PASS, "testValueName4", "comment 4"));
        when(testDataAnalyzer.getValueResults(anyListOf(INameValue.class))).thenReturn(results);
        return testDataAnalyzer;
    }

    /****Recoverable Abnormal Exiting Test Case************************************/
    private RobustTestCase getRecoverableAbnormalExitingTestCase() throws FatalException,RecoverableException{
        final ITestInfo testInfo = getNormalTestInfo();
        final IScriptFactory scriptFactory = getRecoverableAbnormalExitScriptFactory();
        final String entryScriptName = "script1";
        final ILexicon lexicon = getNormalLexicon();
        final IMasterNavigator masterNavigator = getNormalMasterNavigator();
        final ITestDataAnalyzer testDataAnalyzer = getRecoverableAbnormalExitingTestDataAnalyzer();
        final ILog log = mock(ILog.class);
        final ITestCase testCase = new TestCase(testInfo, scriptFactory, entryScriptName, lexicon, masterNavigator, testDataAnalyzer, log);
        final IRuntimeConfig config = getNormalRuntimeConfig();
        return new RobustTestCase(testCase, config, log);
    }

    private ITestDataAnalyzer getRecoverableAbnormalExitingTestDataAnalyzer() throws FatalException{
        final ITestDataAnalyzer testDataAnalyzer = mock(ITestDataAnalyzer.class);
        final List<INameValue> inputs = new ArrayList<>();
        inputs.add(NameValue.getInstance("inputName1", "input value 1"));
        inputs.add(NameValue.getInstance("inputName2", "input value 2"));
        inputs.add(NameValue.getInstance("inputName3", "input value 3"));
        when(testDataAnalyzer.getAllTestInputs()).thenReturn(inputs);
        final List<TestValueResult> results = new ArrayList<>();
        results.add(TestValueResult.getInstance("expected value 1", "", TestValueResultType.FAIL, "testValueName1", "comment 1"));
        results.add(TestValueResult.getInstance("expected value 2", "", TestValueResultType.KNOWN, "testValueName2", "comment 2"));
        results.add(TestValueResult.getInstance("expected value 3", "actual value 3", TestValueResultType.PASS, "testValueName3", "comment 3"));
        results.add(TestValueResult.getInstance("expected value 4", "", TestValueResultType.FAIL, "testValueName4", "comment 4"));
        when(testDataAnalyzer.getValueResults(anyListOf(INameValue.class))).thenReturn(results);
        return testDataAnalyzer;
    }

    private IScriptFactory getRecoverableAbnormalExitScriptFactory() throws FatalException, RecoverableException {
        final IScriptFactory scriptFactory = mock(IScriptFactory.class);
        final IScript entryScript = mock(IScript.class);
        Mockito.doAnswer(new Answer(){public Object answer(InvocationOnMock invocation) throws ScriptNavigationException{
            recoverableAbnormalExitScriptExecute();
            return null;
        }}).when(entryScript).execute();
        when(scriptFactory.getScript(eq("script1"), any(IValueMap.class), any(IMasterNavigator.class))).thenReturn(entryScript);
        return scriptFactory;
    }

    private void recoverableAbnormalExitScriptExecute() throws ScriptNavigationException{
        try{
            //Used to make sure start time is measured to be greater than completed time.
            Thread.sleep(10);
        }
        catch(Exception e){
            //ignore
        }
        throw new ScriptNavigationException("this is the error message");
    }


    /****Non-Recoverable Abnormal Exiting Test Case************************************/
    private RobustTestCase getNonRecoverableAbnormalExitingTestCase() throws FatalException,RecoverableException{
        final ITestInfo testInfo = getNormalTestInfo();
        final IScriptFactory scriptFactory = getNonRecoverableAbnormalExitScriptFactory();
        final String entryScriptName = "script1";
        final ILexicon lexicon = getNormalLexicon();
        final IMasterNavigator masterNavigator = getNormalMasterNavigator();
        final ITestDataAnalyzer testDataAnalyzer = getNonRecoverableAbnormalExitingTestDataAnalyzer();
        final ILog log = mock(ILog.class);
        final ITestCase testCase = new TestCase(testInfo, scriptFactory, entryScriptName, lexicon, masterNavigator, testDataAnalyzer, log);
        final IRuntimeConfig config = getNormalRuntimeConfig();
        return new RobustTestCase(testCase, config, log);
    }

    private ITestDataAnalyzer getNonRecoverableAbnormalExitingTestDataAnalyzer() throws FatalException{
        final ITestDataAnalyzer testDataAnalyzer = mock(ITestDataAnalyzer.class);
        final List<INameValue> inputs = new ArrayList<>();
        inputs.add(NameValue.getInstance("inputName1", "input value 1"));
        inputs.add(NameValue.getInstance("inputName2", "input value 2"));
        inputs.add(NameValue.getInstance("inputName3", "input value 3"));
        when(testDataAnalyzer.getAllTestInputs()).thenReturn(inputs);
        final List<TestValueResult> results = new ArrayList<>();
        results.add(TestValueResult.getInstance("expected value 1", "", TestValueResultType.FAIL, "testValueName1", "comment 1"));
        results.add(TestValueResult.getInstance("expected value 2", "", TestValueResultType.KNOWN, "testValueName2", "comment 2"));
        results.add(TestValueResult.getInstance("expected value 3", "actual value 3", TestValueResultType.PASS, "testValueName3", "comment 3"));
        results.add(TestValueResult.getInstance("expected value 4", "", TestValueResultType.FAIL, "testValueName4", "comment 4"));
        when(testDataAnalyzer.getValueResults(anyListOf(INameValue.class))).thenReturn(results);
        return testDataAnalyzer;
    }

    private IScriptFactory getNonRecoverableAbnormalExitScriptFactory() throws FatalException, RecoverableException {
        final IScriptFactory scriptFactory = mock(IScriptFactory.class);
        final IScript entryScript = mock(IScript.class);
        Mockito.doAnswer(new Answer(){public Object answer(InvocationOnMock invocation) throws ScriptInputLoadingException{
            nonRecoverableAbnormalExitScriptExecute();
            return null;
        }}).when(entryScript).execute();
        when(scriptFactory.getScript(eq("script1"), any(IValueMap.class), any(IMasterNavigator.class))).thenReturn(entryScript);
        return scriptFactory;
    }

    private void nonRecoverableAbnormalExitScriptExecute() throws ScriptInputLoadingException {
        try{
            //Used to make sure start time is measured to be greater than completed time.
            Thread.sleep(10);
        }
        catch(Exception e){
            //ignore
        }
        throw new ScriptInputLoadingException("somefile", "somescript", "the details");
    }

    private void normalScriptExecute(){
        try{
            //Used to make sure start time is measured to be greater than completed time.
            Thread.sleep(10);
        }
        catch(Exception e){
            //ignore
        }
    }

    private ITestInfo getNormalTestInfo() throws FatalException{
        final ITestInfo testInfo = mock(ITestInfo.class);
        when(testInfo.getName()).thenReturn("normal test name");
        when(testInfo.getTestNumber()).thenReturn(1);
        when(testInfo.getDescription()).thenReturn("normal test description");
        return testInfo;
    }

    private IScriptFactory getNormalScriptFactory() throws FatalException, RecoverableException {
        final IScriptFactory scriptFactory = mock(IScriptFactory.class);
        final IScript entryScript = mock(IScript.class);
        Mockito.doAnswer(new Answer(){public Object answer(InvocationOnMock invocation) throws ScriptNavigationException{
            normalScriptExecute();
            return null;
        }}).when(entryScript).execute();
        when(scriptFactory.getScript(eq("script1"), any(IValueMap.class), any(IMasterNavigator.class))).thenReturn(entryScript);
        return scriptFactory;
    }

    private ILexicon getNormalLexicon() throws FatalException{
        final ILexicon lexicon = mock(ILexicon.class);
        final List<INameValue> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(NameValue.getInstance("inputName1", ""));
        nameValuePairs.add(NameValue.getInstance("inputName2", ""));
        nameValuePairs.add(NameValue.getInstance("inputName3", ""));
        nameValuePairs.add(NameValue.getInstance("testValueName1", ""));
        nameValuePairs.add(NameValue.getInstance("testValueName2", ""));
        nameValuePairs.add(NameValue.getInstance("testValueName3", ""));
        nameValuePairs.add(NameValue.getInstance("testValueName4", ""));
        when(lexicon.getAllNameDefaultValuePairs()).thenReturn(nameValuePairs);
        return lexicon;
    }

    private IMasterNavigator getNormalMasterNavigator() throws FatalException{
        return mock(IMasterNavigator.class);
    }




}
