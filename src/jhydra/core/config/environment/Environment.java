package jhydra.core.config.environment;

import java.net.URI;

/**
 * Author: jantic
 * Date: 1/12/13
 */
public class Environment implements IEnvironment {
    private final String name;
    private final String description;
    private final URI appURI;

    public Environment(String name, String description, URI appURI){
        this.name = name;
        this.description = description;
        this.appURI = appURI;
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public String getDescription(){
        return this.description;
    }

    @Override
    public URI getAppURI(){
        return this.appURI;
    }

    public String toString(){
        return this.name;
    }
}
