/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.uinav;

import jhydra.core.uinav.selenium.IExtendedSelenium;

/**
 *
 * @author jantic
 */
public interface IMasterNavigator {
    IExtendedSelenium getSelenium();
    void screenshot();
    void screenshot(String filename);
}
