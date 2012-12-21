/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.properties;

/**
 *
 * @author jantic
 */
public interface INameValueValidator {

    void validateName(String name) throws NameNotValidException;
    
}
