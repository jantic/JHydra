/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.config;

import jhydra.core.config.email.IEmailSettings;
import jhydra.core.config.environment.IEnvironment;

import java.net.URI;
import java.util.List;

/**
 *
 * @author jantic
 */
public interface IProjectConfig {
    String getProjectName();
    URI getProjectDirectory();
    List<URI> getScriptDirectories();
    List<URI> getLexiconPaths();
    Integer getScriptTimeoutSeconds();
    Integer getScriptMaxNumTries();
    Integer getScriptWaitSecondsBetweenAttempts();
    Integer getTestCaseMaxNumTries();
    Integer getTestCaseWaitSecondsBetweenAttempts();
    URI getScreenshotsDirectory();
    URI getLogsDirectory();
    List<IEnvironment> getEnvironments();
    String getProgramName();
    IEmailSettings getEmailSettings();
}
