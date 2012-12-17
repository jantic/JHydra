/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.uinav.selenium;

/**
 *
 * @author jantic
 */
public interface IExtendedSelenium extends ISelenium{
    void waitForPageElementToBePopulated(String identity, Integer maxSeconds);
    void waitForPageElementPresence(String identity, Integer maxSeconds);
    void waitForPageElementToBeEditable(String identity, Integer maxSeconds);
    void waitForPageElementToBeNOTEditable(String identity, Integer maxSeconds);
    void waitForDropDownOptionToBeSelected(String identity, Integer maxSeconds);
    void waitForPromptToDisappear(Integer maxSeconds);
    void waitForCondition(String script, Integer maxSeconds);
    void waitForFrameToLoad(String frameAddress, Integer maxSeconds);
    void waitForPageToLoad(Integer maxSeconds);
    void waitForPopUp(String windowID, Integer maxSeconds);
    String getValueIfPresent(String identity); 
    String getTextIfPresent(String identity);  
    void setTimeout(Integer timeout);
    void screenshot(String filename);
    void screenshot();
}
