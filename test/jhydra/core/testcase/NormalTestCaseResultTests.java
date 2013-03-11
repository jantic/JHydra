package jhydra.core.testcase;

import jhydra.core.properties.INameValue;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Author: jantic
 * Date: 3/10/13
 */
public class NormalTestCaseResultTests {

    /***Tests on test case result without any failures****************************************/
    @Test
    public void noFailuresResult_getResultCategory_PASS(){
        final NormalTestCaseResult normalTestCaseResult = getNoFailuresTestResult();
        final String expected = "pass";
        final String actual = normalTestCaseResult.getResultCategory().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void noFailuresResult_getErrorMessage_size_0(){
        final NormalTestCaseResult normalTestCaseResult = getNoFailuresTestResult();
        final int expected = 0;
        final int actual = normalTestCaseResult.getErrorMessages().size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void noFailuresResult_getAllTestValueResults_1_getExpectedValue_expected2(){
        final NormalTestCaseResult normalTestCaseResult = getNoFailuresTestResult();
        final String expected = "expected2";
        final String actual = normalTestCaseResult.getAllTestValueResults().get(1).getExpectedValue();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void noFailuresResult_getAllTestValueResults_1_getActualValue_actual2(){
        final NormalTestCaseResult normalTestCaseResult = getNoFailuresTestResult();
        final String expected = "actual2";
        final String actual = normalTestCaseResult.getAllTestValueResults().get(1).getActualValue();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void noFailuresResult_getAllTestValueResults_1_getTestValueResultType_PASS(){
        final NormalTestCaseResult normalTestCaseResult = getNoFailuresTestResult();
        final String expected = "PASS";
        final String actual = normalTestCaseResult.getAllTestValueResults().get(1).getTestValueResultType().name();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void noFailuresResult_getAllTestValueResults_1_getVariableName_number2(){
        final NormalTestCaseResult normalTestCaseResult = getNoFailuresTestResult();
        final String expected = "number2";
        final String actual = normalTestCaseResult.getAllTestValueResults().get(1).getVariableName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void noFailuresResult_getAllTestValueResults_1_getComments_comments2(){
        final NormalTestCaseResult normalTestCaseResult = getNoFailuresTestResult();
        final String expected = "comments2";
        final String actual = normalTestCaseResult.getAllTestValueResults().get(1).getComments();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void noFailuresResult_getRunStartTime_correctDateTime(){
        final NormalTestCaseResult normalTestCaseResult = getNoFailuresTestResult();
        final String expected = "09-Mar-13 08.50.10 AM";
        final String dateTimePattern = "dd-MMM-yy hh.mm.ss aa";
        final String actual = normalTestCaseResult.getRunStartTime().toString(DateTimeFormat.forPattern(dateTimePattern));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void noFailuresResult_getRunCompletedTime_correctDateTime(){
        final NormalTestCaseResult normalTestCaseResult = getNoFailuresTestResult();
        final String expected = "09-Mar-13 08.53.12 AM";
        final String dateTimePattern = "dd-MMM-yy hh.mm.ss aa";
        final String actual = normalTestCaseResult.getRunCompletedTime().toString(DateTimeFormat.forPattern(dateTimePattern));
        Assert.assertEquals(expected, actual);
    }

    /***Tests on test case result with all passes except 1 known failure****************************************/
    @Test
    public void allPassOneKnownResult_getResultCategory_PASS(){
        final NormalTestCaseResult normalTestCaseResult = getAllPassOneKnownFailureTestResult();
        final String expected = "pass";
        final String actual = normalTestCaseResult.getResultCategory().toString();
        Assert.assertEquals(expected, actual);
    }

    /***Tests on test case result with all passes except 1 failure****************************************/
    @Test
    public void allPassOneKnownResult_getResultCategory_VALUE_FAILURE(){
        final NormalTestCaseResult normalTestCaseResult = getAllPassOneFailureTestResult();
        final String expected = "value failure";
        final String actual = normalTestCaseResult.getResultCategory().toString();
        Assert.assertEquals(expected, actual);
    }

    /***Tests on test case result with 1 pass, 1 known and 1 failure****************************************/
    @Test
    public void onePassOneFailureOneKnownResult_getResultCategory_VALUE_FAILURE(){
        final NormalTestCaseResult normalTestCaseResult = getOnePassOneFailureOneKnownFailureTestResult();
        final String expected = "value failure";
        final String actual = normalTestCaseResult.getResultCategory().toString();
        Assert.assertEquals(expected, actual);
    }

    /***Tests on test case result with no values to test****************************************/
    @Test
    public void noValuesToTestResult_getResultCategory_PASS(){
        final NormalTestCaseResult normalTestCaseResult = getNormalTestResultWithoutValuesToTest();
        final String expected = "pass";
        final String actual = normalTestCaseResult.getResultCategory().toString();
        Assert.assertEquals(expected, actual);
    }

    /***PRIVATE METHODS******************************************************/
    private NormalTestCaseResult getNoFailuresTestResult(){
        return getNormalTestResult(TestValueResultType.PASS, TestValueResultType.PASS, TestValueResultType.PASS);
    }

    private NormalTestCaseResult getAllPassOneKnownFailureTestResult(){
        return getNormalTestResult(TestValueResultType.PASS, TestValueResultType.KNOWN, TestValueResultType.PASS);
    }

    private NormalTestCaseResult getAllPassOneFailureTestResult(){
        return getNormalTestResult(TestValueResultType.PASS, TestValueResultType.FAIL, TestValueResultType.PASS);
    }

    private NormalTestCaseResult getOnePassOneFailureOneKnownFailureTestResult(){
        return getNormalTestResult(TestValueResultType.KNOWN, TestValueResultType.FAIL, TestValueResultType.PASS);
    }

    private NormalTestCaseResult getNormalTestResult(TestValueResultType result1, TestValueResultType result2, TestValueResultType result3){
        final ITestDataAnalyzer testDataAnalyzer = mock(ITestDataAnalyzer.class);
        final List<TestValueResult> testValueResults = new ArrayList<>();
        testValueResults.add(TestValueResult.getInstance("expected1", "actual1", result1, "number1", "comments1"));
        testValueResults.add(TestValueResult.getInstance("expected2", "actual2", result2, "number2", "comments2"));
        testValueResults.add(TestValueResult.getInstance("expected3", "actual3", result3, "number3", "comments3"));
        when(testDataAnalyzer.getValueResults(anyListOf(INameValue.class))).thenReturn(testValueResults);
        final List<INameValue> actualValues = new ArrayList<>();
        final String pattern = "dd-MMM-yy hh.mm.ss aa";
        final DateTime runStartTime = LocalDateTime.parse("09-MAR-13 08.50.10 AM", DateTimeFormat.forPattern(pattern)).toDateTime();
        final DateTime runCompletedTime = LocalDateTime.parse("09-MAR-13 08.53.12 AM", DateTimeFormat.forPattern(pattern)).toDateTime();
        return new NormalTestCaseResult(testDataAnalyzer, actualValues, runStartTime, runCompletedTime);
    }

    private NormalTestCaseResult getNormalTestResultWithoutValuesToTest(){
        final ITestDataAnalyzer testDataAnalyzer = mock(ITestDataAnalyzer.class);
        final List<TestValueResult> testValueResults = new ArrayList<>();
        when(testDataAnalyzer.getValueResults(anyListOf(INameValue.class))).thenReturn(testValueResults);
        final List<INameValue> actualValues = new ArrayList<>();
        final String pattern = "dd-MMM-yy hh.mm.ss aa";
        final DateTime runStartTime = LocalDateTime.parse("09-MAR-13 08.50.10 AM", DateTimeFormat.forPattern(pattern)).toDateTime();
        final DateTime runCompletedTime = LocalDateTime.parse("09-MAR-13 08.53.12 AM", DateTimeFormat.forPattern(pattern)).toDateTime();
        return new NormalTestCaseResult(testDataAnalyzer, actualValues, runStartTime, runCompletedTime);
    }
}
