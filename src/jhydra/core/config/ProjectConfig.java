package jhydra.core.config;

import jhydra.core.config.email.EmailSettingsFactory;
import jhydra.core.config.email.IEmailSettings;
import jhydra.core.config.environment.EnvironmentFactory;
import jhydra.core.config.environment.IEnvironment;
import jhydra.core.exceptions.FatalException;
import jhydra.core.properties.IProperties;
import jhydra.core.properties.Properties;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: jantic
 * Date: 1/10/13
 */
public class ProjectConfig implements IProjectConfig{
    private final IProgramConfig programConfig;
    private final String projectName;
    private final URI projectDirectory;
    private final URI projectConfigPath;
    private final URI projectScriptDirectory;
    private final URI projectLexiconPath;
    private final URI projectScreenShotsDirectory;
    private final URI projectLogsDirectory;
    private final Integer scriptTimeoutSeconds;
    private final Integer scriptMaxNumberOfTries;
    private final Integer scriptWaitSecondsBetweenAttempts;
    private final List<IEnvironment> environments;
    private final IEmailSettings emailSettings;

    public ProjectConfig(IProgramConfig programConfig, URI projectDirectory) throws FatalException{
        this.programConfig = programConfig;
        this.projectDirectory = projectDirectory;
        this.projectConfigPath = convertToFileURI(projectDirectory, "/jhydra.project");
        final IProperties properties = getConfigFile();
        this.projectName =  properties.getProperty("Project.Name");
        this.projectScriptDirectory =   convertToFileURI(projectDirectory, "/scripts/");
        this.projectLexiconPath = convertToFileURI(projectDirectory, "/lexicon.properties");
        this.projectScreenShotsDirectory = convertToFileURI(projectDirectory, "/screenshots/");
        this.projectLogsDirectory = convertToFileURI(projectDirectory, "/logs/");
        this.scriptTimeoutSeconds = Integer.parseInt(properties.getProperty("Script.TimeoutSeconds"));
        this.scriptMaxNumberOfTries = Integer.parseInt(properties.getProperty("Script.MaxNumberOfTries"));
        this.scriptWaitSecondsBetweenAttempts = Integer.parseInt(properties.getProperty("Script.WaitSecondsBetweenAttempts"));
        this.environments = loadEnvironments();
        this.emailSettings = loadEmailSettings();
    }

    @Override
    public String getProjectName() {
        return this.projectName;
    }

    @Override
    public URI getProjectDirectory() {
        return this.projectDirectory;
    }

    @Override
    public List<URI> getScriptDirectories() {
        final List<URI> scriptDirectories = new ArrayList<>();
        scriptDirectories.add(this.projectScriptDirectory);
        scriptDirectories.add(this.programConfig.getSharedScriptsDirectory());
        return scriptDirectories;
    }

    @Override
    public List<URI> getLexiconPaths() {
        final List<URI> lexiconPaths = new ArrayList<>();
        lexiconPaths.add(this.projectLexiconPath);
        lexiconPaths.add(this.programConfig.getSharedLexiconPath());
        return lexiconPaths;
    }

    @Override
    public Integer getScriptTimeoutSeconds() {
        return scriptTimeoutSeconds;
    }

    @Override
    public Integer getScriptMaxNumTries() {
        return this.scriptMaxNumberOfTries;
    }

    @Override
    public Integer getScriptWaitSecondsBetweenAttempts() {
        return this.scriptWaitSecondsBetweenAttempts;
    }

    @Override
    public URI getScreenshotsDirectory() {
        return this.projectScreenShotsDirectory;
    }

    @Override
    public List<IEnvironment> getEnvironments(){
        return this.environments;
    }

    @Override
    public URI getLogsDirectory() {
        return this.projectLogsDirectory;
    }

    @Override
    public String getProgramName() {
        return programConfig.getProgramName();
    }

    @Override
    public IEmailSettings getEmailSettings() {
        return this.emailSettings;
    }

    private IProperties getConfigFile() throws FatalException {
        return new Properties(this.projectConfigPath);
    }

    private URI convertToFileURI(URI baseURI, String relativePath) {
        final File baseDirectory = new File(baseURI);
        final File file = new File(baseDirectory.getPath() + relativePath);
        return file.toURI();
    }

    private IEmailSettings loadEmailSettings() throws FatalException{
        final EmailSettingsFactory emailSettingsFactory = new EmailSettingsFactory();
        return emailSettingsFactory.load(this.projectConfigPath);
    }

    private List<IEnvironment> loadEnvironments() throws FatalException {
        final EnvironmentFactory environmentFactory = new EnvironmentFactory();
        return environmentFactory.load(this.projectConfigPath);
    }


}
