package jhydra.core.config;

import jhydra.core.exceptions.FatalException;

import java.io.File;
import java.net.URI;

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
    private final URI projectsDirectory;
    private final URI sharedScriptsDirectory;
    private final URI sharedLexiconPath;
    private final String programName;

    //Package access
    static ProgramConfig getInstance() throws FatalException {
        return new ProgramConfig();
    }

    private ProgramConfig() throws FatalException {
        final String programDirString = System.getProperty("user.dir");
        this.programDirectory = convertToFileURI(programDirString);
        this.projectsDirectory = convertToFileURI(programDirString + "/projects/");
        this.sharedScriptsDirectory = convertToFileURI(programDirString + "/shared/scripts/");
        this.sharedLexiconPath = convertToFileURI(programDirString + "/shared/lexicon.properties");
        this.programName = "JHydra";
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
    public URI getSharedScriptsDirectory(){
        return this.sharedScriptsDirectory;
    }

    @Override
    public URI getSharedLexiconPath(){
        return this.sharedLexiconPath;
    }

    @Override
    public String getProgramName() {
        return this.programName;
    }

    private URI convertToFileURI(String path){
        final File file = new File(path);
        return file.toURI();
    }


}
