/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author jantic
 */
public class ProjectConfigTest {
    /***Tests on normally configured project****************************************/
    @Test
    public void getProgramName_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final String expected = "JHydra";
        final String actual = projectConfig.getProgramName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getProjectName_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final String expected = "Test Project 1";
        final String actual = projectConfig.getProjectName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getEmailSettings_Sender_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final String expected = "f@f.f";
        final String actual = projectConfig.getEmailSettings().getSender().getAddress();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getEmailSettings_FirstFailureRecipient_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final String expected = "a@a.a";
        final String actual = projectConfig.getEmailSettings().getFailureRecipients().get(0).getAddress();
        Assert.assertEquals(expected, actual);
    }

    @Test
         public void getEmailSettings_SecondFailureRecipient_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final String expected = "b@b.b";
        final String actual = projectConfig.getEmailSettings().getFailureRecipients().get(1).getAddress();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getEmailSettings_ThirdFailureRecipient_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final String expected = "c@c.c";
        final String actual = projectConfig.getEmailSettings().getFailureRecipients().get(2).getAddress();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getEmailSettings_FirstSuccessRecipient_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final String expected = "d@d.d";
        final String actual = projectConfig.getEmailSettings().getSuccessRecipients().get(0).getAddress();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getEmailSettings_SecondSuccessRecipient_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final String expected = "e@e.e";
        final String actual = projectConfig.getEmailSettings().getSuccessRecipients().get(1).getAddress();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getLogsDirectory_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final String expected = projectConfig.getProjectDirectory().toString() + "logs/";
        final String actual = projectConfig.getLogsDirectory().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getEnvironments_environment_1_getName_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final String expected = "Local";
        final String actual = projectConfig.getEnvironments().get(0).getName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getEnvironments_environment_1_getAppURI_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final String expected = "http://localhost:7001/aex/login.jsp";
        final String actual = projectConfig.getEnvironments().get(0).getAppURI().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getEnvironments_environment_1_getDescription_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final String expected = "Developer's computer";
        final String actual = projectConfig.getEnvironments().get(0).getDescription();
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void getEnvironments_environment_2_getName_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final String expected = "Dev";
        final String actual = projectConfig.getEnvironments().get(1).getName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getEnvironments_environment_2_getAppURI_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final String expected = "http://vmdevapp2:7001/aex/login.jsp";
        final String actual = projectConfig.getEnvironments().get(1).getAppURI().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getEnvironments_environment_2_getDescription_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final String expected = "Dev Environment";
        final String actual = projectConfig.getEnvironments().get(1).getDescription();
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void getEnvironments_environment_5_getName_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final String expected = "Production";
        final String actual = projectConfig.getEnvironments().get(4).getName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getEnvironments_environment_5_getAppURI_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final String expected = "http://www.arrowheadexchange.com";
        final String actual = projectConfig.getEnvironments().get(4).getAppURI().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getEnvironments_environment_5_getDescription_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final String expected = "Production Environment";
        final String actual = projectConfig.getEnvironments().get(4).getDescription();
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void getLexiconPaths_FirstPath_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final String expected = projectConfig.getProjectDirectory().toString() + "lexicon.properties";
        final String actual = projectConfig.getLexiconPaths().get(0).toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getLexiconPaths_SecondPath_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final IProgramConfig programConfig = getProgramConfig();
        final String expected = programConfig.getProgramDirectory().toString() + "shared/lexicon.properties";
        final String actual = projectConfig.getLexiconPaths().get(1).toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getScreenshotsDirectory_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final String expected = projectConfig.getProjectDirectory().toString() + "screenshots";
        final String actual = projectConfig.getScreenshotsDirectory().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getScriptMaxNumTries_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final Integer expected = 3;
        final Integer actual = projectConfig.getScriptMaxNumTries();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getScriptTimeoutSeconds_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final Integer expected = 60;
        final Integer actual = projectConfig.getScriptTimeoutSeconds();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getScriptWaitSecondsBetweenAttempts_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final Integer expected = 30;
        final Integer actual = projectConfig.getScriptWaitSecondsBetweenAttempts();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getScriptDirectories_FirstDirectory_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final String expected = projectConfig.getProjectDirectory().toString() + "scripts/";
        final String actual = projectConfig.getScriptDirectories().get(0).toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getScriptDirectories_SecondDirectory_CorrectValue() throws FatalException, URISyntaxException {
        final IProjectConfig projectConfig = getProjectConfig();
        final IProgramConfig programConfig = getProgramConfig();
        final String expected = programConfig.getProgramDirectory().toString() + "shared/scripts";
        final String actual = projectConfig.getScriptDirectories().get(1).toString();
        Assert.assertEquals(expected, actual);
    }

    /*****PRIVATE METHODS********************************************************************/
    private IProjectConfig getProjectConfig() throws FatalException, URISyntaxException {
        final IProgramConfig programConfig = getProgramConfig();
        final File programDirectory = new File(programConfig.getProjectsDirectory().getPath() + "/project 1/");
        final URI projectConfigURI = programDirectory.toURI();
        return new ProjectConfig(programConfig, projectConfigURI);
    }

    private IProgramConfig getProgramConfig(){
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
