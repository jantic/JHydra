/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.properties;

import jhydra.core.properties.exceptions.NameNotValidException;

/**
 *
 * @author jantic
 */
public interface INameValueValidator {

    void validateName(String name) throws NameNotValidException;
    
}
