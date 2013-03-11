/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.testcase;

/**
 *
 * @author jantic
 */

//TODO:  Should support Excel, CSV, and some open source spreadsheet.
public interface ITestCase extends ITestInfo {
    ITestCaseResult execute();
}
