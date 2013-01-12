package jhydra.core.config;

import jhydra.core.config.email.IEmailSettings;
import jhydra.core.config.environment.IEnvironment;

import java.net.URI;
import java.util.List;

/**
 * Author: jantic
 * Date: 1/12/13
 */
public interface IRuntimeConfig {
    String getProjectName();
    URI getProjectDirectory();
    List<URI> getScriptDirectories();
    List<URI> getLexiconPaths();
    Integer getScriptTimeoutSeconds();
    Integer getScriptMaxNumTries();
    Integer getScriptWaitSecondsBetweenAttempts();
    URI getScreenshotsDirectory();
    URI getLogsDirectory();
    IEnvironment getEnvironment();
    String getProgramName();
    IEmailSettings getEmailSettings();
    Boolean isAutomaticRun();
}
