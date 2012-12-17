/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.config;

/**
 *
 * @author jantic
 */
public interface ICommandLineArgs {
    String GetArgumentValue(String argName);
    Boolean HasArgument(String argName);
    Boolean HasSpecifiedProject();
    String getSpecifiedProjectName();
}
