package jhydra.core.config.environment;

import jhydra.core.config.exceptions.ConfiguredPathNotValidException;
import jhydra.core.exceptions.FatalException;
import jhydra.core.properties.IProperties;
import jhydra.core.properties.Properties;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: jantic
 * Date: 1/10/13
 */
public class EnvironmentFactory {

    public List<IEnvironment> load(IProperties properties) throws FatalException{
        final String namePrefix = "Environment.Name.";
        final String descriptionPrefix = "Environment.Description.";
        final String uriPrefix = "Environment.AppURI.";

        Integer index = 1;
        Boolean found = true;

        final List<IEnvironment> environments = new ArrayList<>();

        while(found){
            final String nameSelector = namePrefix + index.toString();
            final String uriSelector = uriPrefix + index.toString();
            final String descriptionSelector = descriptionPrefix + index.toString();

            if(properties.hasProperty(nameSelector) && properties.hasProperty(uriSelector)){
                final String name = properties.getProperty(nameSelector);
                final String appURIString = properties.getProperty(uriSelector);
                final String description = properties.hasProperty(descriptionSelector) ?
                        properties.getProperty(descriptionSelector) : "";
                final IEnvironment environment = generateEnvironment(name, description, appURIString);
                environments.add(environment);
            }
            else if(!(properties.hasProperty(nameSelector) || properties.hasProperty(uriSelector))){
                //No matching config keys found.  We're done.
                found = false;
            }
            else{
                //If parts are found, but not correctly configured, then we'll keep found at true and try the next,
                // ignoring this one.  Just log a warning here.
                //TODO:  Log a warning here.
            }

            index++;
        }

        return environments;
    }

    private IEnvironment generateEnvironment(String name, String description, String appURIString) throws FatalException{
        try{
            final URI appURI = new URI(appURIString);
            return new Environment(name, description, appURI);
        }
        catch(URISyntaxException e){
            throw new ConfiguredPathNotValidException("Environment.AppURI", appURIString);
        }
    }
}
