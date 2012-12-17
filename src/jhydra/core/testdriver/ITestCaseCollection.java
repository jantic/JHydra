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
public interface ITestCaseCollection {
    String getName();
    String getDescription();
    String getFileName();
    List<ITestCase> getOrderedTestCases();
    Boolean testsAreIndependent();
    Date getLoadTimeStamp();
}
