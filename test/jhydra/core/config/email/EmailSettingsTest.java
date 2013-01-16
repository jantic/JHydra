/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.config.email;


import jhydra.core.config.exceptions.InvalidEmailAddressException;
import jhydra.core.exceptions.FatalException;
import jhydra.core.properties.IProperties;
import jhydra.core.properties.Properties;
import org.junit.Assert;
import org.junit.Test;

import javax.mail.internet.InternetAddress;
import java.io.File;
import java.net.URI;



/**
 *
 * @author jantic
 */
public class EmailSettingsTest {
    public EmailSettingsTest() {
    }
    
    /***Tests using correctly configured properties file****************************************/
    @Test
    public void normalPropertiesFile_load_firstFailureRecipient_getEmailAddress_CorrectValue() throws FatalException{
        final InternetAddress address = getNormalPropertiesFileEmailSettings().getFailureRecipients().get(0);
        final String expected = "a@a.a";
        final String actual = address.getAddress();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPropertiesFile_load_secondFailureRecipient_getEmailAddress_CorrectValue() throws FatalException{
        final InternetAddress address = getNormalPropertiesFileEmailSettings().getFailureRecipients().get(1);
        final String expected = "b@b.b";
        final String actual = address.getAddress();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPropertiesFile_load_thirdFailureRecipient_getEmailAddress_CorrectValue() throws FatalException{
        final InternetAddress address = getNormalPropertiesFileEmailSettings().getFailureRecipients().get(2);
        final String expected = "c@c.c";
        final String actual = address.getAddress();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPropertiesFile_load_firstSuccessRecipient_getEmailAddress_CorrectValue() throws FatalException{
        final InternetAddress address = getNormalPropertiesFileEmailSettings().getSuccessRecipients().get(0);
        final String expected = "d@d.d";
        final String actual = address.getAddress();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPropertiesFile_load_secondSuccessRecipient_getEmailAddress_CorrectValue() throws FatalException{
        final InternetAddress address = getNormalPropertiesFileEmailSettings().getSuccessRecipients().get(1);
        final String expected = "e@e.e";
        final String actual = address.getAddress();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void normalPropertiesFile_load_getSender_CorrectValue() throws FatalException{
        final InternetAddress address = getNormalPropertiesFileEmailSettings().getSender();
        final String expected = "f@f.f";
        final String actual = address.getAddress();
        Assert.assertEquals(expected, actual);
    }

    /***Tests using incorrectly configured properties file- invalid failure recipient address****************************************/
    @Test
    public void invalidRecipientAddressPropertiesFile_load_firstFailureRecipient_getEmailAddress_CorrectValue() throws FatalException{
        final InternetAddress address =  getInvalidRecipientAddressPropertiesFileEmailSettings().getFailureRecipients().get(0);
        final String expected = "a@a.a";
        final String actual = address.getAddress();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void invalidRecipientAddressPropertiesFile_load_secondFailureRecipient_getEmailAddress_CorrectValue() throws FatalException{
        final InternetAddress address =  getInvalidRecipientAddressPropertiesFileEmailSettings().getFailureRecipients().get(1);
        final String expected = "c@c.c";
        final String actual = address.getAddress();
        Assert.assertEquals(expected, actual);
    }


    /***Tests using incorrectly configured properties file- invalid success recipient address****************************************/
    @Test
    public void invalidRecipientAddressPropertiesFile_load_firstSuccessRecipient_getEmailAddress_CorrectValue() throws FatalException{
        final InternetAddress address =  getInvalidRecipientAddressPropertiesFileEmailSettings().getSuccessRecipients().get(0);
        final String expected = "d@d.d";
        final String actual = address.getAddress();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void invalidRecipientAddressPropertiesFile_load_secondSuccessRecipient_getEmailAddress_CorrectValue() throws FatalException{
        final InternetAddress address =  getInvalidRecipientAddressPropertiesFileEmailSettings().getSuccessRecipients().get(1);
        final String expected = "f@f.f";
        final String actual = address.getAddress();
        Assert.assertEquals(expected, actual);
    }


    /***Tests using incorrectly configured properties file- invalid sender address****************************************/
    @Test(expected = InvalidEmailAddressException.class)
    public void invalidSenderAddressPropertiesFile_load_InvalidEmailAddressException() throws FatalException{
        getInvalidSenderAddressPropertiesFileEmailSettings();
    }


    /***PRIVATE METHODS*******************************************************************************************/
    
    private IEmailSettings getNormalPropertiesFileEmailSettings() throws FatalException{
        final URI propertiesURI = new File("./test/test data/basic_project.properties").toURI();
        final IProperties properties = new Properties(propertiesURI);
        return new EmailSettings(properties);
    }

    private IEmailSettings getInvalidRecipientAddressPropertiesFileEmailSettings() throws FatalException{
        final URI propertiesURI = new File("./test/test data/invalidRecipientAddress_project.properties").toURI();
        final IProperties properties = new Properties(propertiesURI);
        return new EmailSettings(properties);
    }

    private IEmailSettings getInvalidSenderAddressPropertiesFileEmailSettings() throws FatalException{
        final URI propertiesURI = new File("./test/test data/invalidSenderAddress_project.properties").toURI();
        final IProperties properties = new Properties(propertiesURI);
        return new EmailSettings(properties);
    }

}
