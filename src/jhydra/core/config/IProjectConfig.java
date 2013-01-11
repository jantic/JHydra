/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.config;

import java.net.URI;
import java.net.URL;
import java.util.List;

/**
 *
 * @author jantic
 */
public interface IProjectConfig extends IProgramConfig {
    String getProjectName();
    URI getProjectDirectory();
    List<URI> getScriptDirectories();
    URI getLexiconPath();
    Integer getScriptTimeout();
    Integer getScriptMaxNumTries();
    Integer getScriptTimeBetweenAttempts();
    URI getScreenshotsDirectory();
    URL getURL();  
    URI getLogsDirectory();
}
