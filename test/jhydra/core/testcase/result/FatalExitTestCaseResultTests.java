package jhydra.core.testcase.result;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: jantic
 * Date: 3/10/13
 */
public class FatalExitTestCaseResultTests {
    @Test
    public void fatalExitTestCaseResult_getResultCategory_FATAL_EXIT(){
        final FatalExitTestCaseResult testCaseResult = getFatalExitTestResult();
        final String expected = "fatal exit";
        final String actual = testCaseResult.getResultCategory().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void fatalExitTestCaseResult_getErrorMessage_1_correctMessage(){
        final FatalExitTestCaseResult testCaseResult = getFatalExitTestResult();
        final String expected = "error message 2";
        final String actual = testCaseResult.getErrorMessages().get(1);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void fatalExitTestCaseResult_getAllTestValueResults_size_0(){
        final FatalExitTestCaseResult testCaseResult = getFatalExitTestResult();
        final int expected = 0;
        final int actual = testCaseResult.getAllTestValueResults().size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void fatalExitTestCaseResult_getRunStartTime_correctDateTime(){
        final FatalExitTestCaseResult testCaseResult = getFatalExitTestResult();
        final String expected = "09-Mar-13 08.50.10 AM";
        final String dateTimePattern = "dd-MMM-yy hh.mm.ss aa";
        final String actual = testCaseResult.getRunStartTime().toString(DateTimeFormat.forPattern(dateTimePattern));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void fatalExitTestCaseResult_getRunCompletedTime_correctDateTime(){
        final FatalExitTestCaseResult testCaseResult = getFatalExitTestResult();
        final String expected = "09-Mar-13 08.53.12 AM";
        final String dateTimePattern = "dd-MMM-yy hh.mm.ss aa";
        final String actual = testCaseResult.getRunCompletedTime().toString(DateTimeFormat.forPattern(dateTimePattern));
        Assert.assertEquals(expected, actual);
    }

    /***PRIVATE METHODS******************************************************/
    private FatalExitTestCaseResult getFatalExitTestResult(){
        final String pattern = "dd-MMM-yy hh.mm.ss aa";
        final DateTime runStartTime = LocalDateTime.parse("09-MAR-13 08.50.10 AM", DateTimeFormat.forPattern(pattern)).toDateTime();
        final DateTime runCompletedTime = LocalDateTime.parse("09-MAR-13 08.53.12 AM", DateTimeFormat.forPattern(pattern)).toDateTime();
        final List<String> errorMessages = new ArrayList<>();
        errorMessages.add("error message 1");
        errorMessages.add("error message 2");
        errorMessages.add("error message 3");
        return new FatalExitTestCaseResult(runStartTime, runCompletedTime, errorMessages);
    }

}
