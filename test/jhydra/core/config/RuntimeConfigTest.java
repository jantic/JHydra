package jhydra.core.config;

import jhydra.core.exceptions.FatalException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Author: jantic
 * Date: 1/13/13
 */
public class RuntimeConfigTest {
    /***Tests on normally configured project with no command line args****************************************/
    @Test
    public void normalProjectFile_getProgramName_CorrectValue() throws FatalException, URISyntaxException {
        final IRuntimeConfig runtimeConfig = getNormalRuntimeConfig();
        final String expected = "JHydra";
        final String actual = runtimeConfig.getProgramName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalProjectFile_getProjectName_CorrectValue() throws FatalException, URISyntaxException {
        final IRuntimeConfig runtimeConfig = getNormalRuntimeConfig();
        final String expected = "Test Project 1";
        final String actual = runtimeConfig.getProjectName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalProjectFile_getEmailSettings_Sender_CorrectValue() throws FatalException, URISyntaxException {
        final IRuntimeConfig runtimeConfig = getNormalRuntimeConfig();
        final String expected = "f@f.f";
        final String actual = runtimeConfig.getEmailSettings().getSender().getAddress();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalProjectFile_getEmailSettings_FirstFailureRecipient_CorrectValue() throws FatalException, URISyntaxException {
        final IRuntimeConfig runtimeConfig = getNormalRuntimeConfig();
        final String expected = "a@a.a";
        final String actual = runtimeConfig.getEmailSettings().getFailureRecipients().get(0).getAddress();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalProjectFile_getEmailSettings_SecondFailureRecipient_CorrectValue() throws FatalException, URISyntaxException {
        final IRuntimeConfig runtimeConfig = getNormalRuntimeConfig();
        final String expected = "b@b.b";
        final String actual = runtimeConfig.getEmailSettings().getFailureRecipients().get(1).getAddress();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalProjectFile_getEmailSettings_ThirdFailureRecipient_CorrectValue() throws FatalException, URISyntaxException {
        final IRuntimeConfig runtimeConfig = getNormalRuntimeConfig();
        final String expected = "c@c.c";
        final String actual = runtimeConfig.getEmailSettings().getFailureRecipients().get(2).getAddress();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalProjectFile_getEmailSettings_FirstSuccessRecipient_CorrectValue() throws FatalException, URISyntaxException {
        final IRuntimeConfig runtimeConfig = getNormalRuntimeConfig();
        final String expected = "d@d.d";
        final String actual = runtimeConfig.getEmailSettings().getSuccessRecipients().get(0).getAddress();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalProjectFile_getEmailSettings_SecondSuccessRecipient_CorrectValue() throws FatalException, URISyntaxException {
        final IRuntimeConfig runtimeConfig = getNormalRuntimeConfig();
        final String expected = "e@e.e";
        final String actual = runtimeConfig.getEmailSettings().getSuccessRecipients().get(1).getAddress();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalProjectFile_getLogsDirectory_CorrectValue() throws FatalException, URISyntaxException {
        final IRuntimeConfig runtimeConfig = getNormalRuntimeConfig();
        final String expected = runtimeConfig.getProjectDirectory().toString() + "logs/";
        final String actual = runtimeConfig.getLogsDirectory().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalProjectFile_getEnvironment_getName_CorrectValue() throws FatalException, URISyntaxException {
        final IRuntimeConfig runtimeConfig = getNormalRuntimeConfig();
        final String expected = "Staging";
        final String actual = runtimeConfig.getEnvironment().getName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalProjectFile_getEnvironment_getAppURI_CorrectValue() throws FatalException, URISyntaxException {
        final IRuntimeConfig runtimeConfig = getNormalRuntimeConfig();
        final String expected = "http://staging.arrowheadexchange.com";
        final String actual = runtimeConfig.getEnvironment().getAppURI().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalProjectFile_getEnvironment_getDescription_CorrectValue() throws FatalException, URISyntaxException {
        final IRuntimeConfig runtimeConfig = getNormalRuntimeConfig();
        final String expected = "Staging Environment";
        final String actual = runtimeConfig.getEnvironment().getDescription();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalProjectFile_getLexiconPaths_FirstPath_CorrectValue() throws FatalException, URISyntaxException {
        final IRuntimeConfig runtimeConfig = getNormalRuntimeConfig();
        final String expected = runtimeConfig.getProjectDirectory().toString() + "lexicon.properties";
        final String actual = runtimeConfig.getLexiconPaths().get(0).toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalProjectFile_getLexiconPaths_SecondPath_CorrectValue() throws FatalException, URISyntaxException {
        final IRuntimeConfig runtimeConfig = getNormalRuntimeConfig();
        final IProgramConfig programConfig = getNormalProgramConfig();
        final String expected = programConfig.getProgramDirectory().toString() + "shared/lexicon.properties";
        final String actual = runtimeConfig.getLexiconPaths().get(1).toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalProjectFile_getScreenshotsDirectory_CorrectValue() throws FatalException, URISyntaxException {
        final IRuntimeConfig runtimeConfig = getNormalRuntimeConfig();
        final String expected = runtimeConfig.getProjectDirectory().toString() + "screenshots";
        final String actual = runtimeConfig.getScreenshotsDirectory().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalProjectFile_getScriptMaxNumTries_CorrectValue() throws FatalException, URISyntaxException {
        final IRuntimeConfig runtimeConfig = getNormalRuntimeConfig();
        final Integer expected = 4;
        final Integer actual = runtimeConfig.getScriptMaxNumTries();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalProjectFile_getScriptTimeoutSeconds_CorrectValue() throws FatalException, URISyntaxException {
        final IRuntimeConfig runtimeConfig = getNormalRuntimeConfig();
        final Integer expected = 65;
        final Integer actual = runtimeConfig.getScriptTimeoutSeconds();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalProjectFile_getScriptWaitSecondsBetweenAttempts_CorrectValue() throws FatalException, URISyntaxException {
        final IRuntimeConfig runtimeConfig = getNormalRuntimeConfig();
        final Integer expected = 32;
        final Integer actual = runtimeConfig.getScriptWaitSecondsBetweenAttempts();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalProjectFile_getScriptDirectories_FirstDirectory_CorrectValue() throws FatalException, URISyntaxException {
        final IRuntimeConfig runtimeConfig = getNormalRuntimeConfig();
        final String expected = runtimeConfig.getProjectDirectory().toString() + "scripts/";
        final String actual = runtimeConfig.getScriptDirectories().get(0).toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalProjectFile_getScriptDirectories_SecondDirectory_CorrectValue() throws FatalException, URISyntaxException {
        final IRuntimeConfig runtimeConfig = getNormalRuntimeConfig();
        final IProgramConfig programConfig = getNormalProgramConfig();
        final String expected = programConfig.getProgramDirectory().toString() + "shared/scripts";
        final String actual = runtimeConfig.getScriptDirectories().get(1).toString();
        Assert.assertEquals(expected, actual);
    }


    /*TODO:  Test for empty project files- Should we even allow for this?  An empty project file doesn't have
    *environments specified, which means that a sensible runtime config cannot be made (hence, a run can't
    * be sensibly initiated.
    */

    /*****PRIVATE METHODS********************************************************************/

    private IRuntimeConfig getNormalRuntimeConfig() throws FatalException, URISyntaxException{
        final IProjectConfig projectConfig = getNormalProjectConfig();
        return new RuntimeConfig(false, projectConfig, projectConfig.getEnvironments().get(3));
    }

    private IProjectConfig getNormalProjectConfig() throws FatalException, URISyntaxException {
        final IProgramConfig programConfig = getNormalProgramConfig();
        final File programDirectory = new File(programConfig.getProjectsDirectory().getPath() + "/project 1/");
        final URI projectConfigURI = programDirectory.toURI();
        return new ProjectConfig(programConfig, projectConfigURI);
    }

    private IProgramConfig getNormalProgramConfig(){
        final IProgramConfig programConfig = mock(IProgramConfig.class);
        when(programConfig.getProgramName()).thenReturn("JHydra");
        final URI projectsDirectory = new File(System.getProperty("user.dir") + "/test projects/").toURI();
        when(programConfig.getProjectsDirectory()).thenReturn(projectsDirectory);
        final URI sharedLexiconPath = new File(System.getProperty("user.dir") + "/shared/lexicon.properties").toURI();
        when(programConfig.getSharedLexiconPath()).thenReturn(sharedLexiconPath);
        final URI sharedScriptsDirectory = new File(System.getProperty("user.dir") + "/shared/scripts/").toURI();
        when(programConfig.getSharedScriptsDirectory()).thenReturn(sharedScriptsDirectory);
        final URI programDirectory = new File(System.getProperty("user.dir")).toURI();
        when(programConfig.getProgramDirectory()).thenReturn(programDirectory);
        return programConfig;
    }
}
