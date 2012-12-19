/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.properties;

/**
 *
 * @author jantic
 */
public interface INameValue {
    Boolean matchesName(String name);
    Boolean matchesValue(String value);
    String getName();
    String getValue();
    INameValue copy();
    INameValue copyWithNewValue(String value);
}
