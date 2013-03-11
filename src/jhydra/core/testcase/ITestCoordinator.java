/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.testcase;

import jhydra.core.config.IProgramConfig;

import java.util.List;

/**
 *
 * @author jantic
 */
public interface ITestCoordinator {
    void RequestRun(ITestRunRequest request);
    void RequestScriptCheck(IProgramConfig config);
    List<ITestInfo> getAvailableTestInfos(IProgramConfig config, ITestMode mode);
}
