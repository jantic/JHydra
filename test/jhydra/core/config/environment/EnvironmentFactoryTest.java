/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.config.environment;


import jhydra.core.config.exceptions.ConfiguredPathNotValidException;
import jhydra.core.exceptions.FatalException;
import jhydra.core.properties.IProperties;
import jhydra.core.properties.Properties;
import org.junit.Assert;
import org.junit.Test;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 *
 * @author jantic
 */
public class EnvironmentFactoryTest {
    public EnvironmentFactoryTest() {
    }
    
    /***Tests using correctly configured properties file****************************************/
    @Test
    public void normalPropertiesFile_load_firstEnvironment_getName_CorrectValue() throws FatalException{
        final IEnvironment environment = getNormalPropertiesFileEnvironments().get(0);
        final String expected = "Local";
        final String actual = environment.getName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPropertiesFile_load_secondEnvironment_getName_CorrectValue() throws FatalException{
        final IEnvironment environment = getNormalPropertiesFileEnvironments().get(1);
        final String expected = "Dev";
        final String actual = environment.getName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPropertiesFile_load_fifthEnvironment_getName_CorrectValue() throws FatalException{
        final IEnvironment environment = getNormalPropertiesFileEnvironments().get(4);
        final String expected = "Production";
        final String actual = environment.getName();
        Assert.assertEquals(expected, actual);
    }


    @Test
    public void normalPropertiesFile_load_firstEnvironment_getDescription_CorrectValue() throws FatalException{
        final IEnvironment environment = getNormalPropertiesFileEnvironments().get(0);
        final String expected = "Developer's computer";
        final String actual = environment.getDescription();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPropertiesFile_load_secondEnvironment_getDescription_CorrectValue() throws FatalException{
        final IEnvironment environment = getNormalPropertiesFileEnvironments().get(1);
        final String expected = "Dev Environment";
        final String actual = environment.getDescription();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPropertiesFile_load_fifthEnvironment_getDescription_CorrectValue() throws FatalException{
        final IEnvironment environment = getNormalPropertiesFileEnvironments().get(4);
        final String expected = "";
        final String actual = environment.getDescription();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPropertiesFile_load_firstEnvironment_getAppURI_CorrectValue() throws FatalException, MalformedURLException {
        final IEnvironment environment = getNormalPropertiesFileEnvironments().get(0);
        final String expected = "http://localhost:7001/aex/login.jsp";
        final String actual = environment.getAppURI().toURL().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPropertiesFile_load_secondEnvironment_getAppURI_CorrectValue() throws FatalException, MalformedURLException{
        final IEnvironment environment = getNormalPropertiesFileEnvironments().get(1);
        final String expected = "http://vmdevapp2:7001/aex/login.jsp";
        final String actual = environment.getAppURI().toURL().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPropertiesFile_load_fifthEnvironment_getAppURI_CorrectValue() throws FatalException, MalformedURLException{
        final IEnvironment environment = getNormalPropertiesFileEnvironments().get(4);
        final String expected = "http://www.arrowheadexchange.com";
        final String actual = environment.getAppURI().toURL().toString();
        Assert.assertEquals(expected, actual);
    }

    /***Tests using incorrectly configured properties file- missing AppURI****************************************/
    @Test
    public void missingURIPropertiesFile_load_firstEnvironment_getName_CorrectValue() throws FatalException{
        final IEnvironment environment = getMissingAppURIPropertiesFileEnvironments().get(0);
        final String expected = "Local";
        final String actual = environment.getName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void missingURIPropertiesFile_load_secondEnvironment_getName_CorrectValue() throws FatalException{
        final IEnvironment environment = getMissingAppURIPropertiesFileEnvironments().get(1);
        final String expected = "Test";
        final String actual = environment.getName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void missingURIPropertiesFile_load_fourthEnvironment_getName_CorrectValue() throws FatalException{
        final IEnvironment environment = getMissingAppURIPropertiesFileEnvironments().get(3);
        final String expected = "Production";
        final String actual = environment.getName();
        Assert.assertEquals(expected, actual);
    }

    /***Tests using incorrectly configured properties file- missing name****************************************/
    @Test
    public void missingNamePropertiesFile_load_firstEnvironment_getName_CorrectValue() throws FatalException{
        final IEnvironment environment = getMissingNamePropertiesFileEnvironments().get(0);
        final String expected = "Local";
        final String actual = environment.getName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void missingNamePropertiesFile_load_secondEnvironment_getName_CorrectValue() throws FatalException{
        final IEnvironment environment = getMissingNamePropertiesFileEnvironments().get(1);
        final String expected = "Test";
        final String actual = environment.getName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void missingNamePropertiesFile_load_fourthEnvironment_getName_CorrectValue() throws FatalException{
        final IEnvironment environment = getMissingNamePropertiesFileEnvironments().get(3);
        final String expected = "Production";
        final String actual = environment.getName();
        Assert.assertEquals(expected, actual);
    }


    /***Tests using incorrectly configured properties file- invalid appURI****************************************/
    @Test(expected = ConfiguredPathNotValidException.class)
    public void invalidAppURIPropertiesFile_load_Exception() throws FatalException{
        getInvalidAppURIPropertiesFileEnvironments();
    }

    /***PRIVATE METHODS*******************************************************************************************/
    
    private List<IEnvironment> getNormalPropertiesFileEnvironments() throws FatalException{
        final URI propertiesURI = new File("./test/test data/basic_project.properties").toURI();
        final IProperties properties = new Properties(propertiesURI);
        final EnvironmentFactory environmentFactory = new EnvironmentFactory();
        return environmentFactory.load(properties);
    }

    private List<IEnvironment> getMissingAppURIPropertiesFileEnvironments() throws FatalException{
        final URI propertiesURI = new File("./test/test data/missingAppURI_project.properties").toURI();
        final IProperties properties = new Properties(propertiesURI);
        final EnvironmentFactory environmentFactory = new EnvironmentFactory();
        return environmentFactory.load(properties);
    }

    private List<IEnvironment> getMissingNamePropertiesFileEnvironments() throws FatalException{
        final URI propertiesURI = new File("./test/test data/missingName_project.properties").toURI();
        final IProperties properties = new Properties(propertiesURI);
        final EnvironmentFactory environmentFactory = new EnvironmentFactory();
        return environmentFactory.load(properties);
    }

    private List<IEnvironment> getInvalidAppURIPropertiesFileEnvironments() throws FatalException{
        final URI propertiesURI = new File("./test/test data/invalidAppURI_project.properties").toURI();
        final IProperties properties = new Properties(propertiesURI);
        final EnvironmentFactory environmentFactory = new EnvironmentFactory();
        return environmentFactory.load(properties);
    }

}
