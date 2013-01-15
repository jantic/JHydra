package jhydra.core.config;

import jhydra.core.config.email.EmailSettingsFactory;
import jhydra.core.config.email.IEmailSettings;
import jhydra.core.config.environment.EnvironmentFactory;
import jhydra.core.config.environment.IEnvironment;
import jhydra.core.exceptions.FatalException;
import jhydra.core.properties.IProperties;
import jhydra.core.properties.Properties;
import org.apache.commons.lang3.math.NumberUtils;

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

    private final String PROJECT_NAME_KEY = "Project.Name";
    private final String SCRIPT_TIMEOUT_SECONDS_KEY = "Script.TimeoutSeconds";
    private final Integer SCRIPT_TIMEOUT_SECONDS_DEFAULT = 30;
    private final String SCRIPT_MAX_TRIES_KEY = "Script.MaxNumberOfTries";
    private final Integer SCRIPT_MAX_TRIES_DEFAULT = 3;
    private final String SCRIPT_WAIT_SECONDS_BETWEEN_ATTEMPTS_KEY = "Script.WaitSecondsBetweenAttempts";
    private final Integer SCRIPT_WAIT_SECONDS_BETWEEN_ATTEMPTS_DEFAULT = 60;

    public ProjectConfig(IProgramConfig programConfig, URI projectConfigPath) throws FatalException{
        this.programConfig = programConfig;
        this.projectConfigPath = projectConfigPath;
        this.projectDirectory = deriveParentDirectory(projectConfigPath);
        this.projectScriptDirectory =   convertToFileURI(projectDirectory, "/scripts/");
        this.projectLexiconPath = convertToFileURI(projectDirectory, "/lexicon.properties");
        this.projectScreenShotsDirectory = convertToFileURI(projectDirectory, "/screenshots/");
        this.projectLogsDirectory = convertToFileURI(projectDirectory, "/logs/");
        //Config file fields
        final IProperties properties = getConfigFile();
        this.projectName = determineProjectName(properties);
        this.scriptTimeoutSeconds = determineScriptTimeoutSeconds(properties);
        this.scriptMaxNumberOfTries = determineScriptMaxNumberOfTries(properties);
        this.scriptWaitSecondsBetweenAttempts = determineWaitTimeSecondsBetweenAttempts(properties);
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

    private URI deriveParentDirectory(URI filePath){
        return new File(filePath).getParentFile().toURI();
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

    private String determineProjectName(IProperties properties) throws FatalException{
        if(properties.hasProperty(PROJECT_NAME_KEY)){
            final String configValue = properties.getProperty(PROJECT_NAME_KEY);
            if(!configValue.isEmpty()){
                return configValue;
            }
        }
        //If no config value present, derive name from relative project directory, using dot notation instead of '/'.
        final String projectsDirectory = programConfig.getProjectsDirectory().getPath();
        final String thisProjectDirectory = this.projectDirectory.getPath();
        final String rawName = thisProjectDirectory.replace(projectsDirectory, "").replace("/",".");
        //Remove end '.'
        return rawName.endsWith(".") ? rawName.substring(0, rawName.length()-1) : rawName;
    }

    private Integer determineScriptTimeoutSeconds(IProperties properties) throws FatalException{
        return getIntegerValueFromProperties(SCRIPT_TIMEOUT_SECONDS_KEY, properties, SCRIPT_TIMEOUT_SECONDS_DEFAULT);
    }

    private Integer determineWaitTimeSecondsBetweenAttempts(IProperties properties) throws FatalException{
        return getIntegerValueFromProperties(SCRIPT_WAIT_SECONDS_BETWEEN_ATTEMPTS_KEY, properties, SCRIPT_WAIT_SECONDS_BETWEEN_ATTEMPTS_DEFAULT);
    }

    private Integer determineScriptMaxNumberOfTries(IProperties properties) throws FatalException{
        return getIntegerValueFromProperties(SCRIPT_MAX_TRIES_KEY, properties, SCRIPT_MAX_TRIES_DEFAULT);
    }

    private Integer getIntegerValueFromProperties(String configKey, IProperties properties, Integer defaultValue) throws FatalException{
        if(properties.hasProperty(configKey)){
            final String configValue = properties.getProperty(configKey);
            if(!configValue.isEmpty() && NumberUtils.isDigits(configValue)){
                return Integer.parseInt(configValue);
            }
        }
        //If no config value present, just use default built in as constant.
        return defaultValue;
    }
}
