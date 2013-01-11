package jhydra.core.config;

import jhydra.core.config.jydra.core.config.exceptions.ConfiguredPathNotValidException;
import jhydra.core.exceptions.FatalException;
import sun.management.snmp.jvmmib.JvmThreadInstanceEntryMBean;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created with IntelliJ IDEA.
 * User: jantic
 * Date: 1/10/13
 * Time: 10:46 PM
 *
 * NOTES- I'm favoring making as little as possible configured by the user, to make it as simple (and reliable) as
 * possible.
 */

//TODO:  Make it so that if a config key is missing in config, it's reentered automatically and attempt to work around it
//being missing is made, if possible.

public class ProgramConfig implements IProgramConfig{
    private final URI programDirectory;
    private final URI programConfigPath;
    private final URI projectsDirectory;
    private final String programName;
    private final IEmailSettings emailSettings;
    private final Boolean isAutomaticRun;

    //Package access
    static ProgramConfig getInstance(ICommandLineArgs commandLineArgs) throws FatalException{
        return new ProgramConfig(commandLineArgs);
    }

    //Package access
    static ProgramConfig getInstance() throws FatalException {
        final ICommandLineArgs commandLineArgs = CommandLineArgs.NULL;
        return getInstance(commandLineArgs);
    }

    private ProgramConfig(ICommandLineArgs commandLineArgs) throws FatalException {
        final String programDirString = System.getProperty("user.dir");
        this.programDirectory = convertToURI("ProgramDirectory", programDirString);
        this.programConfigPath = convertToURI("ProgramConfigPath", programDirString + "/jhydra.program");
        this.projectsDirectory = convertToURI("ProjectsDirectory", programDirString + "/projects/");
        this.programName = "JHydra";
        this.emailSettings = loadEmailSettings();
        this.isAutomaticRun = (commandLineArgs == CommandLineArgs.NULL);
    }

    @Override
    public URI getProgramDirectory() {
        return this.programDirectory;
    }

    @Override
    public URI getProjectsDirectory() {
        return this.projectsDirectory;
    }

    @Override
    public String getProgramName() {
        return this.programName;
    }

    @Override
    public IEmailSettings getEmailSettings() {
        return this.emailSettings;
    }

    @Override
    public Boolean isAutomaticRun() {
        return this.isAutomaticRun;
    }

    private URI convertToURI(String configKey, String path) throws ConfiguredPathNotValidException{
        try{
            return new URI(path);
        }
        catch(URISyntaxException e){
            //This isn't likely to ever happen....
            throw new ConfiguredPathNotValidException(configKey, path);
        }
    }

    private IEmailSettings loadEmailSettings() throws FatalException{
        EmailSettingsFactory emailSettingsFactory = new EmailSettingsFactory();
        return emailSettingsFactory.load(this.programConfigPath);
    }
}
