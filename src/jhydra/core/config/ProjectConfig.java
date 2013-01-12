package jhydra.core.config;

import jhydra.core.config.email.EmailSettingsFactory;
import jhydra.core.config.email.IEmailSettings;
import jhydra.core.config.environment.EnvironmentFactory;
import jhydra.core.config.environment.IEnvironment;
import jhydra.core.config.exceptions.ConfiguredPathNotValidException;
import jhydra.core.exceptions.FatalException;
import jhydra.core.properties.IProperties;
import jhydra.core.properties.Properties;

import java.net.URI;
import java.net.URISyntaxException;
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
        this.projectConfigPath = convertToURI("Project.ConfigPath", projectDirectory.toString() + "/jhydra.project");
        final IProperties properties = getConfigFile();
        this.projectName =  properties.getProperty("Project.Name");
        this.projectScriptDirectory =   convertToURI("Project.ScriptDirectory", projectDirectory.toString() + "/scripts/");
        this.projectLexiconPath = convertToURI("Project.LexiconPath",projectDirectory.toString() + "/lexicon.properties");
        this.projectScreenShotsDirectory = convertToURI("Project.ScreenShotsDirectory", projectDirectory.toString() + "/screenshots/");
        this.projectLogsDirectory = convertToURI("Project.LogsDirectory", projectDirectory.toString() + "/logs/");
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

    private URI convertToURI(String configKey, String path) throws ConfiguredPathNotValidException {
        try{
            return new URI(path);
        }
        catch(URISyntaxException e){
            throw new ConfiguredPathNotValidException(configKey, path);
        }
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
