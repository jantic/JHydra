package jhydra.core.config;

import jhydra.core.exceptions.FatalException;
import jhydra.core.properties.IProperties;
import jhydra.core.properties.Properties;
import java.net.URI;

/**
 * User: jantic
 * Date: 1/10/13
 */
public class EmailSettingsFactory {

    public IEmailSettings load(URI propertiesPath) throws FatalException{
        final IProperties properties = new Properties(propertiesPath);
        return new EmailSettings(properties);
    }
}
