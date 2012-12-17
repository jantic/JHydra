/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.testdriver;

import java.util.Date;
import java.util.List;

/**
 *
 * @author jantic
 */
public interface ITestCaseResult {
    List<ITestUnitResult> getAllTestUnitResults();
    TestResultCategory getResultCategory();
    Date runStartTime();
    Date runCompletedTime();
    
}
