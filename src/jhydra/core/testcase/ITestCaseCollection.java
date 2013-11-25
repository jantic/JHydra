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
public interface ITestCaseCollection {
    String getName();
    String getDescription();
    String getFileName();
    List<ITestCase> getOrderedTestCases();
    Boolean testsAreIndependent();
    DateTime getLoadTimeStamp();
}
