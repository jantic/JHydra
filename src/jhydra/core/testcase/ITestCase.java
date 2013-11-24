/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.testcase;

import jhydra.core.testcase.result.ITestCaseResult;

/**
 *
 * @author jantic
 */

//TODO:  Should support Excel, CSV, and some open source spreadsheet.
public interface ITestCase extends ITestInfo {
    ITestCaseResult execute();
}
