package jhydra.core.testcase;

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
 * Date: 3/17/13
 */
public class TestCaseTests {

    /***Tests normally executing test case that passes****************************************/
    @Test
    public void normalPassingTestCase_getDescription_correctDescription() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalPassingTestCase();
        final String expected = "normal test description";
        final String actual = testCase.getDescription();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_getName_correctName() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalPassingTestCase();
        final String expected = "normal test name";
        final String actual = testCase.getName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_getTestNumber_1() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalPassingTestCase();
        final Integer expected = 1;
        final Integer actual = testCase.getTestNumber();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getResultCategory_pass() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalPassingTestCase();
        final String expected = "pass";
        final String actual = testCase.execute().getResultCategory().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getAllTestValueResults_0_testValueResultType_PASS() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalPassingTestCase();
        final String expected = "PASS";
        final String actual = testCase.execute().getAllTestValueResults().get(0).getTestValueResultType().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getAllTestValueResults_0_getComments_correctComment() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalPassingTestCase();
        final String expected = "comment 1";
        final String actual = testCase.execute().getAllTestValueResults().get(0).getComments();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getAllTestValueResults_0_getVariableName_testValueName1() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalPassingTestCase();
        final String expected = "testValueName1";
        final String actual = testCase.execute().getAllTestValueResults().get(0).getVariableName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getAllTestValueResults_0_getActualValue_correctValue() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalPassingTestCase();
        final String expected = "actual value 1";
        final String actual = testCase.execute().getAllTestValueResults().get(0).getActualValue();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getAllTestValueResults_0_getExpectedValue_correctValue() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalPassingTestCase();
        final String expected = "expected value 1";
        final String actual = testCase.execute().getAllTestValueResults().get(0).getExpectedValue();
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void normalPassingTestCase_execute_getAllTestValueResults_1_testValueResultType_KNOWN() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalPassingTestCase();
        final String expected = "KNOWN";
        final String actual = testCase.execute().getAllTestValueResults().get(1).getTestValueResultType().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getAllTestValueResults_1_getComments_correctComment() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalPassingTestCase();
        final String expected = "comment 2";
        final String actual = testCase.execute().getAllTestValueResults().get(1).getComments();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getAllTestValueResults_1_getVariableName_testValueName1() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalPassingTestCase();
        final String expected = "testValueName2";
        final String actual = testCase.execute().getAllTestValueResults().get(1).getVariableName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getAllTestValueResults_1_getActualValue_correctValue() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalPassingTestCase();
        final String expected = "actual value 2";
        final String actual = testCase.execute().getAllTestValueResults().get(1).getActualValue();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getAllTestValueResults_1_getExpectedValue_correctValue() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalPassingTestCase();
        final String expected = "expected value 2";
        final String actual = testCase.execute().getAllTestValueResults().get(1).getExpectedValue();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_getErrorMessages_size0() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalPassingTestCase();
        final Integer expected = 0;
        final Integer actual = testCase.execute().getErrorMessages().size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPassingTestCase_execute_runCompletedTime_greaterThan_runStartTime() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalPassingTestCase();
        final Boolean expected = true;
        final ITestCaseResult testCaseResult = testCase.execute();
        final Boolean actual = testCaseResult.getRunCompletedTime().compareTo(testCaseResult.getRunStartTime()) > 0;
        Assert.assertEquals(expected, actual);
    }

    /***Tests normally executing test case that fails on values****************************************/
    @Test
    public void normalValueFailingTestCase_execute_getResultCategory_value_failure() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalValueFailingTestCase();
        final String expected = "value failure";
        final String actual = testCase.execute().getResultCategory().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalValueFailingTestCase_execute_getAllTestValueResults_2_testValueResultType_FAIL() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalValueFailingTestCase();
        final String expected = "FAIL";
        final String actual = testCase.execute().getAllTestValueResults().get(2).getTestValueResultType().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalValueFailingTestCase_execute_getAllTestValueResults_2_getComments_correctComment() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalValueFailingTestCase();
        final String expected = "comment 3";
        final String actual = testCase.execute().getAllTestValueResults().get(2).getComments();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalValueFailingTestCase_execute_getAllTestValueResults_2_getVariableName_testValueName1() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalValueFailingTestCase();
        final String expected = "testValueName3";
        final String actual = testCase.execute().getAllTestValueResults().get(2).getVariableName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalValueFailingTestCase_execute_getAllTestValueResults_2_getActualValue_correctValue() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalValueFailingTestCase();
        final String expected = "actual value 3";
        final String actual = testCase.execute().getAllTestValueResults().get(2).getActualValue();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalValueFailingTestCase_execute_getAllTestValueResults_2_getExpectedValue_correctValue() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalValueFailingTestCase();
        final String expected = "expected value 3";
        final String actual = testCase.execute().getAllTestValueResults().get(2).getExpectedValue();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalValueFailingTestCase_execute_getErrorMessages_size0() throws FatalException, RecoverableException{
        final TestCase testCase = getNormalValueFailingTestCase();
        final Integer expected = 0;
        final Integer actual = testCase.execute().getErrorMessages().size();
        Assert.assertEquals(expected, actual);
    }


    /***Tests recoverable abnormally exiting test case****************************************/
    @Test
    public void recoverableAbnormalExitTestCase_getDescription_correctDescription() throws FatalException, RecoverableException {
        final TestCase testCase = getRecoverableAbnormalExitingTestCase();
        final String expected = "normal test description";
        final String actual = testCase.getDescription();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void recoverableAbnormalExitTestCase_getName_correctName() throws FatalException, RecoverableException{
        final TestCase testCase = getRecoverableAbnormalExitingTestCase();
        final String expected = "normal test name";
        final String actual = testCase.getName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void recoverableAbnormalExitTestCase_getTestNumber_1() throws FatalException, RecoverableException{
        final TestCase testCase = getRecoverableAbnormalExitingTestCase();
        final Integer expected = 1;
        final Integer actual = testCase.getTestNumber();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void recoverableAbnormalExitTestCase_execute_getResultCategory_nonfatal_exit() throws FatalException, RecoverableException{
        final TestCase testCase = getRecoverableAbnormalExitingTestCase();
        final String expected = "non-fatal exit";
        final String actual = testCase.execute().getResultCategory().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void recoverableAbnormalExitTestCase_execute_getAllTestValueResults_size0() throws FatalException, RecoverableException{
        final TestCase testCase = getRecoverableAbnormalExitingTestCase();
        final Integer expected = 0;
        final Integer actual = testCase.execute().getAllTestValueResults().size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void recoverableAbnormalExitTestCase_execute_getErrorMessage_0_correctMessage() throws FatalException, RecoverableException{
        final TestCase testCase = getRecoverableAbnormalExitingTestCase();
        final String expected = "Error while attempting to navigate user interface in script: this is the error message";
        final String actual = testCase.execute().getErrorMessages().get(0);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void recoverableAbnormalExitTestCase_execute_runCompletedTime_greaterThan_runStartTime() throws FatalException, RecoverableException{
        final TestCase testCase = getRecoverableAbnormalExitingTestCase();
        final Boolean expected = true;
        final ITestCaseResult testCaseResult = testCase.execute();
        final Boolean actual = testCaseResult.getRunCompletedTime().compareTo(testCaseResult.getRunStartTime()) > 0;
        Assert.assertEquals(expected, actual);
    }


    /***Tests non-recoverable abnormally exiting test case****************************************/
    @Test
    public void nonRecoverableAbnormalExitTestCase_getDescription_correctDescription() throws FatalException, RecoverableException {
        final TestCase testCase = getNonRecoverableAbnormalExitingTestCase();
        final String expected = "normal test description";
        final String actual = testCase.getDescription();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void nonRecoverableAbnormalExitTestCase_getName_correctName() throws FatalException, RecoverableException{
        final TestCase testCase = getNonRecoverableAbnormalExitingTestCase();
        final String expected = "normal test name";
        final String actual = testCase.getName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void nonRecoverableAbnormalExitTestCase_getTestNumber_1() throws FatalException, RecoverableException{
        final TestCase testCase = getNonRecoverableAbnormalExitingTestCase();
        final Integer expected = 1;
        final Integer actual = testCase.getTestNumber();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void nonRecoverableAbnormalExitTestCase_execute_getResultCategory_fatal_exit() throws FatalException, RecoverableException{
        final TestCase testCase = getNonRecoverableAbnormalExitingTestCase();
        final String expected = "fatal exit";
        final String actual = testCase.execute().getResultCategory().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void nonRecoverableAbnormalExitTestCase_execute_getAllTestValueResults_size0() throws FatalException, RecoverableException{
        final TestCase testCase = getNonRecoverableAbnormalExitingTestCase();
        final Integer expected = 0;
        final Integer actual = testCase.execute().getAllTestValueResults().size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void nonRecoverableAbnormalExitTestCase_execute_getErrorMessage_0_correctMessage() throws FatalException, RecoverableException{
        final TestCase testCase = getNonRecoverableAbnormalExitingTestCase();
        final String expected = "Error while attempting to load script file named somescript at path somefile: the details";
        final String actual = testCase.execute().getErrorMessages().get(0);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void nonRecoverableAbnormalExitTestCase_execute_runCompletedTime_greaterThan_runStartTime() throws FatalException, RecoverableException{
        final TestCase testCase = getNonRecoverableAbnormalExitingTestCase();
        final Boolean expected = true;
        final ITestCaseResult testCaseResult = testCase.execute();
        final Boolean actual = testCaseResult.getRunCompletedTime().compareTo(testCaseResult.getRunStartTime()) > 0;
        Assert.assertEquals(expected, actual);
    }

    /***Tests a test case with a script throwing a null pointer exception***********************************************/
    /* Should be treated as fatal exception by default (as well as any other non-explicitly categorized exception/throwable) */
    @Test
    public void nullPointerExceptionThrowingTestCase_getDescription_correctDescription() throws FatalException, RecoverableException {
        final TestCase testCase = getNullPointerExceptionThrowingTestCase();
        final String expected = "normal test description";
        final String actual = testCase.getDescription();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void nullPointerExceptionThrowingTestCase_getName_correctName() throws FatalException, RecoverableException{
        final TestCase testCase = getNullPointerExceptionThrowingTestCase();
        final String expected = "normal test name";
        final String actual = testCase.getName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void nullPointerExceptionThrowingTestCase_getTestNumber_1() throws FatalException, RecoverableException{
        final TestCase testCase = getNullPointerExceptionThrowingTestCase();
        final Integer expected = 1;
        final Integer actual = testCase.getTestNumber();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void nullPointerExceptionThrowingTestCase_execute_getResultCategory_fatal_exit() throws FatalException, RecoverableException{
        final TestCase testCase = getNullPointerExceptionThrowingTestCase();
        final String expected = "fatal exit";
        final String actual = testCase.execute().getResultCategory().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void nullPointerExceptionThrowingTestCase_execute_getAllTestValueResults_size0() throws FatalException, RecoverableException{
        final TestCase testCase = getNullPointerExceptionThrowingTestCase();
        final Integer expected = 0;
        final Integer actual = testCase.execute().getAllTestValueResults().size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void nullPointerExceptionThrowingTestCase_execute_getErrorMessage_0_correctMessage() throws FatalException, RecoverableException{
        final TestCase testCase = getNullPointerExceptionThrowingTestCase();
        final String expected = "this is the message";
        final String actual = testCase.execute().getErrorMessages().get(0);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void nullPointerExceptionThrowingTestCase_execute_runCompletedTime_greaterThan_runStartTime() throws FatalException, RecoverableException{
        final TestCase testCase = getNullPointerExceptionThrowingTestCase();
        final Boolean expected = true;
        final ITestCaseResult testCaseResult = testCase.execute();
        final Boolean actual = testCaseResult.getRunCompletedTime().compareTo(testCaseResult.getRunStartTime()) > 0;
        Assert.assertEquals(expected, actual);
    }

    /***Tests a test case with a script throwing a throwable type "Error"**********************************************/
    /* This makes sure we're also properly handling Throwables that don't inherit from Exception.
    /* Should be treated as fatal exception by default (as well as any other non-explicitly categorized exception/throwable) */
    @Test
    public void errorThrowingTestCase_getDescription_correctDescription() throws FatalException, RecoverableException {
        final TestCase testCase = getErrorThrowingTestCase();
        final String expected = "normal test description";
        final String actual = testCase.getDescription();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void errorThrowingTestCase_getName_correctName() throws FatalException, RecoverableException{
        final TestCase testCase = getErrorThrowingTestCase();
        final String expected = "normal test name";
        final String actual = testCase.getName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void errorThrowingTestCase_getTestNumber_1() throws FatalException, RecoverableException{
        final TestCase testCase = getErrorThrowingTestCase();
        final Integer expected = 1;
        final Integer actual = testCase.getTestNumber();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void errorThrowingTestCase_execute_getResultCategory_fatal_exit() throws FatalException, RecoverableException{
        final TestCase testCase = getErrorThrowingTestCase();
        final String expected = "fatal exit";
        final String actual = testCase.execute().getResultCategory().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void errorThrowingTestCase_execute_getAllTestValueResults_size0() throws FatalException, RecoverableException{
        final TestCase testCase = getErrorThrowingTestCase();
        final Integer expected = 0;
        final Integer actual = testCase.execute().getAllTestValueResults().size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void errorThrowingTestCase_execute_getErrorMessage_0_correctMessage() throws FatalException, RecoverableException{
        final TestCase testCase = getErrorThrowingTestCase();
        final String expected = "this is the error";
        final String actual = testCase.execute().getErrorMessages().get(0);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void errorThrowingTestCase_execute_runCompletedTime_greaterThan_runStartTime() throws FatalException, RecoverableException{
        final TestCase testCase = getErrorThrowingTestCase();
        final Boolean expected = true;
        final ITestCaseResult testCaseResult = testCase.execute();
        final Boolean actual = testCaseResult.getRunCompletedTime().compareTo(testCaseResult.getRunStartTime()) > 0;
        Assert.assertEquals(expected, actual);
    }

    /*****************************************************************************************/
    /***PRIVATE METHODS***********************************************************************/
    /*****************************************************************************************/

    /****Normal Passing Test Case**********************************************/
    private TestCase getNormalPassingTestCase() throws FatalException, RecoverableException {
        final ITestInfo testInfo = getNormalTestInfo();
        final IScriptFactory scriptFactory = getNormalScriptFactory();
        final String entryScriptName = "script1";
        final ILexicon lexicon = getNormalLexicon();
        final IMasterNavigator masterNavigator = getNormalMasterNavigator();
        final ITestDataAnalyzer testDataAnalyzer = getNormalPassingTestDataAnalyzer();
        final ILog log = mock(ILog.class);
        return new TestCase(testInfo, scriptFactory, entryScriptName, lexicon, masterNavigator, testDataAnalyzer, log);
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
    private TestCase getNormalValueFailingTestCase() throws FatalException, RecoverableException {
        final ITestInfo testInfo = getNormalTestInfo();
        final IScriptFactory scriptFactory = getNormalScriptFactory();
        final String entryScriptName = "script1";
        final ILexicon lexicon = getNormalLexicon();
        final IMasterNavigator masterNavigator = getNormalMasterNavigator();
        final ITestDataAnalyzer testDataAnalyzer = getNormalFailingTestDataAnalyzer();
        final ILog log = mock(ILog.class);
        return new TestCase(testInfo, scriptFactory, entryScriptName, lexicon, masterNavigator, testDataAnalyzer, log);
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
    private TestCase getRecoverableAbnormalExitingTestCase() throws FatalException,RecoverableException{
        final ITestInfo testInfo = getNormalTestInfo();
        final IScriptFactory scriptFactory = getRecoverableAbnormalExitScriptFactory();
        final String entryScriptName = "script1";
        final ILexicon lexicon = getNormalLexicon();
        final IMasterNavigator masterNavigator = getNormalMasterNavigator();
        final ITestDataAnalyzer testDataAnalyzer = getRecoverableAbnormalExitingTestDataAnalyzer();
        final ILog log = mock(ILog.class);
        return new TestCase(testInfo, scriptFactory, entryScriptName, lexicon, masterNavigator, testDataAnalyzer, log);
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
    private TestCase getNonRecoverableAbnormalExitingTestCase() throws FatalException,RecoverableException{
        final ITestInfo testInfo = getNormalTestInfo();
        final IScriptFactory scriptFactory = getNonRecoverableAbnormalExitScriptFactory();
        final String entryScriptName = "script1";
        final ILexicon lexicon = getNormalLexicon();
        final IMasterNavigator masterNavigator = getNormalMasterNavigator();
        final ITestDataAnalyzer testDataAnalyzer = getNonRecoverableAbnormalExitingTestDataAnalyzer();
        final ILog log = mock(ILog.class);
        return new TestCase(testInfo, scriptFactory, entryScriptName, lexicon, masterNavigator, testDataAnalyzer, log);
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


    /****Null Pointer Exception Throwing Test Case************************************/
    private TestCase getNullPointerExceptionThrowingTestCase() throws FatalException,RecoverableException{
        final ITestInfo testInfo = getNormalTestInfo();
        final IScriptFactory scriptFactory = getNullPointerExceptionThrowingScriptFactory() ;
        final String entryScriptName = "script1";
        final ILexicon lexicon = getNormalLexicon();
        final IMasterNavigator masterNavigator = getNormalMasterNavigator();
        final ITestDataAnalyzer testDataAnalyzer = getNullPointerExceptionThrowingTestDataAnalyzer();
        final ILog log = mock(ILog.class);
        return new TestCase(testInfo, scriptFactory, entryScriptName, lexicon, masterNavigator, testDataAnalyzer, log);
    }

    private ITestDataAnalyzer getNullPointerExceptionThrowingTestDataAnalyzer() throws FatalException{
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

    private IScriptFactory getNullPointerExceptionThrowingScriptFactory() throws NullPointerException, RecoverableException, FatalException {
        final IScriptFactory scriptFactory = mock(IScriptFactory.class);
        final IScript entryScript = mock(IScript.class);
        Mockito.doAnswer(new Answer(){public Object answer(InvocationOnMock invocation) throws NullPointerException{
            nullPointerExceptionThrowingScriptExecute();
            return null;
        }}).when(entryScript).execute();
        when(scriptFactory.getScript(eq("script1"), any(IValueMap.class), any(IMasterNavigator.class))).thenReturn(entryScript);
        return scriptFactory;
    }

    private void nullPointerExceptionThrowingScriptExecute() throws NullPointerException {
        try{
            //Used to make sure start time is measured to be greater than completed time.
            Thread.sleep(10);
        }
        catch(Exception e){
            //ignore
        }
        throw new NullPointerException("this is the message");
    }


    /****Error Throwing Test Case************************************/
    private TestCase getErrorThrowingTestCase() throws FatalException,RecoverableException{
        final ITestInfo testInfo = getNormalTestInfo();
        final IScriptFactory scriptFactory = getErrorThrowingScriptFactory() ;
        final String entryScriptName = "script1";
        final ILexicon lexicon = getNormalLexicon();
        final IMasterNavigator masterNavigator = getNormalMasterNavigator();
        final ITestDataAnalyzer testDataAnalyzer = getErrorThrowingTestDataAnalyzer();
        final ILog log = mock(ILog.class);
        return new TestCase(testInfo, scriptFactory, entryScriptName, lexicon, masterNavigator, testDataAnalyzer, log);
    }

    private ITestDataAnalyzer getErrorThrowingTestDataAnalyzer() throws FatalException{
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

    private IScriptFactory getErrorThrowingScriptFactory() throws Error, RecoverableException, FatalException {
        final IScriptFactory scriptFactory = mock(IScriptFactory.class);
        final IScript entryScript = mock(IScript.class);
        Mockito.doAnswer(new Answer(){public Object answer(InvocationOnMock invocation) throws Error{
            errorThrowingScriptExecute();
            return null;
        }}).when(entryScript).execute();
        when(scriptFactory.getScript(eq("script1"), any(IValueMap.class), any(IMasterNavigator.class))).thenReturn(entryScript);
        return scriptFactory;
    }

    private void errorThrowingScriptExecute() throws Error {
        try{
            //Used to make sure start time is measured to be greater than completed time.
            Thread.sleep(10);
        }
        catch(Exception e){
            //ignore
        }
        throw new Error("this is the error");
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
