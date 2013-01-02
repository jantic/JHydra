/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.config;

/**
 *
 * @author jantic
 */
public interface IProjectConfig extends IConfig {
    String getProjectName();
    String getProjectPath();
    String getScriptsPath();
    String getLexiconPath();
    Integer getScriptTimeout();
    Integer getScriptMaxTries();
    Integer getScriptTimeBetweenAttempts();
}
