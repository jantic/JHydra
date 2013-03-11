/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.testcase;

import org.joda.time.DateTime;

import java.util.List;

/**
 *
 * @author jantic
 */
public interface ITestCaseResult {
    List<TestValueResult> getAllTestValueResults();
    TestResultCategory getResultCategory();
    DateTime getRunStartTime();
    DateTime getRunCompletedTime();
    List<String> getErrorMessages();
}
