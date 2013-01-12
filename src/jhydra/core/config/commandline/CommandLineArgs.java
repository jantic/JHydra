package jhydra.core.config.commandline;

/**
 * User: jantic
 * Date: 1/10/13
 */
public class CommandLineArgs implements ICommandLineArgs{
    public static CommandLineArgs NULL = new CommandLineArgs("");

    private final String projectName;

    public static CommandLineArgs getInstance(String[] args){
        //TODO: Implement this fully
        return new CommandLineArgs("");
    }

    private CommandLineArgs(String projectName){
        this.projectName = projectName;
    }

    @Override
    public String getProjectNameToRun() {
        return this.projectName;
    }

}
