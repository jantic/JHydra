/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.config.exceptions;


import jhydra.core.exceptions.FatalException;

/**
 *
 * @author jantic
 */
public class ConfiguredPathNotValidException extends FatalException {
    private final String path;
    private final String configKey;

    public ConfiguredPathNotValidException(String configKey, String path){
        super("");
        this.configKey = configKey;
        this.path = path;
    }
    
    public String getMessage(){
        return "Path for config key " + configKey +  " has an invalid path specified: " + path;
    }
}
