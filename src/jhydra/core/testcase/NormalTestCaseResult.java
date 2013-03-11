package jhydra.core.testcase;

import jhydra.core.properties.INameValue;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: jantic
 * Date: 3/3/13
 */
public class NormalTestCaseResult implements ITestCaseResult {
    private final List<TestValueResult> testValueResults;
    private final TestResultCategory testResultCategory;
    private final DateTime runStartTime;
    private final DateTime runCompletedTime;


    public NormalTestCaseResult(ITestDataAnalyzer testDataAnalyzer, List<INameValue> actualValues, DateTime runStartTime, DateTime runCompletedTime){
        this.testValueResults = testDataAnalyzer.getValueResults(actualValues);
        this.testResultCategory = determineTestResultCategory();
        this.runStartTime = runStartTime;
        this.runCompletedTime = runCompletedTime;
    }

    @Override
    public List<TestValueResult> getAllTestValueResults() {
        return this.testValueResults;
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
        return new ArrayList<>();
    }

    private TestResultCategory determineTestResultCategory(){
        for(TestValueResult testValueResult : testValueResults){
            if(testValueResult.getTestValueResultType() == TestValueResultType.FAIL){
                return TestResultCategory.VALUE_FAILURE;
            }
        }

        return TestResultCategory.PASS;
    }
}
