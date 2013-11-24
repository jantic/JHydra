package jhydra.core.testcase;

import jhydra.core.config.IRuntimeConfig;
import jhydra.core.logging.ILog;
import jhydra.core.testcase.result.FatalExitTestCaseResult;
import jhydra.core.testcase.result.ITestCaseResult;
import jhydra.core.testcase.result.TestResultCategory;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: jantic
 * Date: 3/9/13
 */
public class RobustTestCase implements ITestCase{
    private final ITestCase testCase;
    private final IRuntimeConfig config;
    private final ILog log;

    public RobustTestCase(ITestCase testCase, IRuntimeConfig config, ILog log){
        this.testCase = testCase;
        this.config = config;
        this.log = log;
    }

    @Override
    public ITestCaseResult execute(){
        final Integer maxNumberOfTries = config.getTestCaseMaxNumTries();
        final Integer waitSecondsBetweenAttempts = config.getTestCaseWaitSecondsBetweenAttempts();
        Integer numberOfTries = 1;

        while (numberOfTries <= maxNumberOfTries) {
            final ITestCaseResult testCaseResult = testCase.execute();
            if(testCaseResult.getResultCategory() != TestResultCategory.NON_FATAL_EXIT){return testCaseResult;}
            numberOfTries++;

            if (numberOfTries <= maxNumberOfTries) {
                final String message = "Attempt on test case failed.  Attempt number " + numberOfTries.toString() +
                        " coming up, after a " + waitSecondsBetweenAttempts.toString() + " second pause.";
                log.warn(message);
                pauseBetweenAttempts(waitSecondsBetweenAttempts);
            }
            else{
                final String message = "Attempt on test case failed, and max number of attempts were made.  Returning failure result.";
                log.warn(message);
                return testCaseResult;
            }
        }

        final String message = "Error- max number of tries configured for test cases is less than 1! Number configured: " +
                maxNumberOfTries.toString() + ".  Test will be skipped.";
        log.error(message);
        final List<String> errorMessages = new ArrayList<>();
        errorMessages.add(message);
        return new FatalExitTestCaseResult(DateTime.now(), DateTime.now(), errorMessages);
    }

    private void pauseBetweenAttempts(Integer pauseSeconds){
        try{
            Thread.sleep(pauseSeconds * 1000);
        }
        catch(InterruptedException e){
            final String message = "Warning- testing pause failed: " + e.getMessage();
            log.warn(message);
        }
    }

    @Override
    public String getName() {
        return testCase.getName();
    }

    @Override
    public String getDescription() {
        return testCase.getDescription();
    }

    @Override
    public Integer getTestNumber() {
        return testCase.getTestNumber();
    }
}
