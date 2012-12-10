/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

/**
 *
 * @author jantic
 */
public interface IScriptFactory {

    IBaseScript getScript(String name) throws Exception;
    
}
