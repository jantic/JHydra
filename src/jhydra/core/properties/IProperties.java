/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.properties;

import java.util.List;

/**
 *
 * @author jantic
 */
public interface IProperties {

    String getProperty(String name) throws NameNotInPropertiesFileException;

    Boolean hasProperty(String name);

    List<String> getAllPropertyNames();
    
}
