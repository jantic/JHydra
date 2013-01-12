package jhydra.core.config.environment;

import java.net.URI;

/**
 * Author: jantic
 * Date: 1/12/13
 */
public interface IEnvironment {
    String getName();

    String getDescription();

    URI getAppURI();
}
