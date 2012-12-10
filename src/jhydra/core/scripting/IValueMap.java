/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

/**
 *
 * @author jantic
 */
public interface IValueMap {

    String getValue(String name) throws Exception;

    boolean hasValue(String name);

    void setValue(String name, String value) throws Exception;
    
}
