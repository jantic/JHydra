/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.testdriver;

import java.util.List;
import jhydra.core.config.IProgramConfig;

/**
 *
 * @author jantic
 */
public interface ITestCoordinator {
    void RequestRun(ITestRunRequest request);
    void RequestScriptCheck(IProgramConfig config);
    List<ITestInfo> getAvailableTestInfos(IProgramConfig config, ITestMode mode);
}
