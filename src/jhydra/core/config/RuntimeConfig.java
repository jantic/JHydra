package jhydra.core.config;

import jhydra.core.config.email.IEmailSettings;
import jhydra.core.config.environment.IEnvironment;

import java.net.URI;
import java.util.List;

/**
 * Author: jantic
 * Date: 1/12/13
 */
public class RuntimeConfig implements IRuntimeConfig {
    private final Boolean isAutomaticRun;
    private final IProjectConfig projectConfig;
    private final IEnvironment environment;

    public RuntimeConfig(Boolean isAutomaticRun, IProjectConfig projectConfig, IEnvironment environment){
        this.isAutomaticRun = isAutomaticRun;
        this.projectConfig = projectConfig;
        this.environment = environment;
    }

    @Override
    public String getProjectName() {
        return projectConfig.getProjectName();
    }

    @Override
    public URI getProjectDirectory() {
        return projectConfig.getProjectDirectory();
    }

    @Override
    public List<URI> getScriptDirectories() {
        return projectConfig.getScriptDirectories();
    }

    @Override
    public List<URI> getLexiconPaths() {
        return projectConfig.getLexiconPaths();
    }

    @Override
    public Integer getScriptTimeoutSeconds() {
        return projectConfig.getScriptTimeoutSeconds();
    }

    @Override
    public Integer getScriptMaxNumTries() {
        return projectConfig.getScriptMaxNumTries();
    }

    @Override
    public Integer getScriptWaitSecondsBetweenAttempts() {
        return projectConfig.getScriptWaitSecondsBetweenAttempts();
    }

    @Override
    public URI getScreenshotsDirectory() {
        return projectConfig.getScreenshotsDirectory();
    }

    @Override
    public URI getLogsDirectory() {
        return projectConfig.getLogsDirectory();
    }

    @Override
    public IEnvironment getEnvironment() {
        return this.environment;
    }

    @Override
    public String getProgramName() {
        return projectConfig.getProgramName();
    }

    @Override
    public IEmailSettings getEmailSettings() {
        return projectConfig.getEmailSettings();
    }

    @Override
    public Boolean isAutomaticRun() {
        return this.isAutomaticRun;
    }
}
