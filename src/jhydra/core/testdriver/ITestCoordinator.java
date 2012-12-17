/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.testdriver;

import java.util.List;
import jhydra.core.config.IConfig;

/**
 *
 * @author jantic
 */
public interface ITestCoordinator {
    void RequestRun(ITestRunRequest request);
    void RequestScriptCheck(IConfig config);
    List<ITestInfo> getAvailableTestInfos(IConfig config, ITestMode mode);
}
