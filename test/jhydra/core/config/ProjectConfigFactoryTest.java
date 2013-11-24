package jhydra.core.config;

import jhydra.core.config.exceptions.InvalidProjectNameException;
import jhydra.core.exceptions.FatalException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Author: jantic
 * Date: 1/15/13
 */
public class ProjectConfigFactoryTest {
    @Test
    public void getAllProjectConfigs_listSize4() throws FatalException, URISyntaxException {
        final ProjectConfigFactory projectConfigFactory = getProjectConfigFactory();
        final List<IProjectConfig> projectConfigs = projectConfigFactory.getAllProjectConfigs();
        final Integer expected = 4;
        final Integer actual = projectConfigs.size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllProjectConfigs_firstProject_getProjectName_CorrectValue() throws FatalException, URISyntaxException {
        final ProjectConfigFactory projectConfigFactory = getProjectConfigFactory();
        final IProjectConfig projectConfig = projectConfigFactory.getAllProjectConfigs().get(0);
        final String expected = "Test Project 3";
        final String actual = projectConfig.getProjectName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllProjectConfigs_secondProject_getProjectName_CorrectValue() throws FatalException, URISyntaxException {
        final ProjectConfigFactory projectConfigFactory = getProjectConfigFactory();
        final IProjectConfig projectConfig = projectConfigFactory.getAllProjectConfigs().get(1);
        final String expected = "Test Project 1";
        final String actual = projectConfig.getProjectName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllProjectConfigs_thirdProject_getProjectName_CorrectValue() throws FatalException, URISyntaxException {
        final ProjectConfigFactory projectConfigFactory = getProjectConfigFactory();
        final IProjectConfig projectConfig = projectConfigFactory.getAllProjectConfigs().get(2);
        final String expected = "project 2";
        final String actual = projectConfig.getProjectName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getProjectConfigByName_firstProject_getProjectName_CorrectValue() throws FatalException, URISyntaxException {
        final ProjectConfigFactory projectConfigFactory = getProjectConfigFactory();
        final IProjectConfig projectConfig = projectConfigFactory.getProjectConfigByName("Test Project 3");
        final String expected = "Test Project 3";
        final String actual = projectConfig.getProjectName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getProjectConfigByName_secondProject_getProjectName_CorrectValue() throws FatalException, URISyntaxException {
        final ProjectConfigFactory projectConfigFactory = getProjectConfigFactory();
        final IProjectConfig projectConfig = projectConfigFactory.getProjectConfigByName("Test Project 1");
        final String expected = "Test Project 1";
        final String actual = projectConfig.getProjectName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getProjectConfigByName_thirdProject_getProjectName_CorrectValue() throws FatalException, URISyntaxException {
        final ProjectConfigFactory projectConfigFactory = getProjectConfigFactory();
        final IProjectConfig projectConfig = projectConfigFactory.getProjectConfigByName("project 2");
        final String expected = "project 2";
        final String actual = projectConfig.getProjectName();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = InvalidProjectNameException.class)
    public void getProjectConfigByName_nonExistingProject_Exception() throws FatalException, URISyntaxException {
        final ProjectConfigFactory projectConfigFactory = getProjectConfigFactory();
        projectConfigFactory.getProjectConfigByName("invalid name");
    }

    /**PRIVATE METHODS*************************************************************/
    private ProjectConfigFactory getProjectConfigFactory() throws FatalException {
        final IProgramConfig programConfig = getProgramConfig();
        return new ProjectConfigFactory(programConfig);
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
