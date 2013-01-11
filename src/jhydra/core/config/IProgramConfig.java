/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.config;

import java.net.URI;

/**
 *
 * @author jantic
 */
public interface IProgramConfig {
    URI getProgramDirectory();
    URI getProjectsDirectory();
    String getProgramName();
    IEmailSettings getEmailSettings();
    Boolean isAutomaticRun();
}
