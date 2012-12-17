/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.testdriver;

import java.util.List;
import jhydra.core.config.IConfig;
import jhydra.core.uinav.browser.IBrowserInfo;

/**
 *
 * @author jantic
 */
public interface ITestRunRequest {
    IConfig getConfig();
    IBrowserInfo getBrowser();
    Boolean debugMode();
    ITestEnvironment getEnvironment();
    List<ITestInfo[]> getTestInfos();
    Boolean sendResultsEmail();
    ITestMode getTestMode();   
}
