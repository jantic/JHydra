package jhydra.core.testcase;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: jantic
 * Date: 3/8/13
 */
public class NonFatalExitTestCaseResult implements ITestCaseResult {
    private final TestResultCategory testResultCategory;
    private final DateTime runStartTime;
    private final DateTime runCompletedTime;
    private final List<String> errorMessages;


    public NonFatalExitTestCaseResult(DateTime runStartTime, DateTime runCompletedTime, List<String> errorMessages){
        this.testResultCategory = TestResultCategory.NON_FATAL_EXIT;
        this.runStartTime = runStartTime;
        this.runCompletedTime = runCompletedTime;
        this.errorMessages = errorMessages;
    }

    @Override
    public List<TestValueResult> getAllTestValueResults() {
        return new ArrayList<>();
    }

    @Override
    public TestResultCategory getResultCategory() {
        return this.testResultCategory;
    }

    @Override
    public DateTime getRunStartTime() {
        return this.runStartTime;
    }

    @Override
    public DateTime getRunCompletedTime() {
        return this.runCompletedTime;
    }

    @Override
    public List<String> getErrorMessages(){
        return this.errorMessages;
    }
}
