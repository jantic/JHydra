/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.testcase;

import jhydra.core.config.IProgramConfig;
import jhydra.core.uinav.browser.IBrowserInfo;

import java.util.List;

/**
 *
 * @author jantic
 */
public interface ITestRunRequest {
    IProgramConfig getConfig();
    IBrowserInfo getBrowser();
    Boolean debugMode();
    ITestEnvironment getEnvironment();
    List<ITestInfo[]> getTestInfos();
    Boolean sendResultsEmail();
    ITestMode getTestMode();   
}
