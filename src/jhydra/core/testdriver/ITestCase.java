/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.testdriver;

import jhydra.core.scripting.lexicon.IValueMap;
import jhydra.core.uinav.IMasterNavigator;

/**
 *
 * @author jantic
 */
public interface ITestCase extends ITestInfo {
    ITestCaseResult execute(IMasterNavigator navigator, IValueMap valueMap);
}
