/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.properties;

import jhydra.core.properties.exceptions.NameNotValidException;
import jhydra.core.properties.exceptions.GeneralPropertiesFileException;
import jhydra.core.properties.exceptions.PropertiesFileNotFoundException;
import jhydra.core.properties.exceptions.PropertiesFileReadPermissionsException;
import jhydra.core.properties.exceptions.NameNotInPropertiesFileException;
import jhydra.core.properties.exceptions.DuplicatedKeyException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author jantic
 */
public class Properties implements IProperties {
    private static final String SEPARATOR = "=";
    private final Map<String,INameValue> keyValues;
    private final String filepath;
    
    public Properties(String filepath) 
            throws DuplicatedKeyException, NameNotValidException, PropertiesFileNotFoundException, 
            GeneralPropertiesFileException, PropertiesFileReadPermissionsException{
        this.filepath = filepath;
        this.keyValues = load(filepath);
    }
    
    @Override
    public List<String> getAllPropertyNames(){
        final List<String> names = new ArrayList<>();        
        final List<INameValue> pairs = new ArrayList<>(keyValues.values());
        
        for(INameValue pair : pairs){
            names.add(pair.getName());
        }
        
        return names;
    }
    

    @Override
    public String getProperty(String name) throws NameNotInPropertiesFileException{
        final String key = generateKey(name);
        if(!keyValues.containsKey(key)){
            throw new NameNotInPropertiesFileException(name, this.filepath);
        }
        return keyValues.get(key).getValue();
    }
    
    @Override
    public Boolean hasProperty(String name){
        final String key = generateKey(name);
        return keyValues.containsKey(key);
    }
    
    private Map<String,INameValue> load(String filepath) 
            throws DuplicatedKeyException, NameNotValidException, PropertiesFileNotFoundException, 
            GeneralPropertiesFileException, PropertiesFileReadPermissionsException{
        final File file = new File(filepath);
        return load(file);
    }
    
    private void validatePropertiesFile(File file) throws PropertiesFileNotFoundException, PropertiesFileReadPermissionsException{
        if(!file.isFile()){
            throw new PropertiesFileNotFoundException(file.getAbsolutePath());
        }
        
        if(!file.canRead()){
            throw new PropertiesFileReadPermissionsException(file.getAbsolutePath());
        }
    }
    
    private Map<String,INameValue> load(File file) 
            throws DuplicatedKeyException, NameNotValidException, PropertiesFileNotFoundException, 
            GeneralPropertiesFileException, PropertiesFileReadPermissionsException{
        
        validatePropertiesFile(file);    
        final List<String> lines =  getFileContents(file);
        final Map<String,INameValue> mapping = new HashMap<>();
        
        for(String line : lines){
            if(!line.contains(SEPARATOR)){continue;}
            final String[] split = line.split(SEPARATOR, 2);
            final String name = split[0].trim();
            final String key = generateKey(name);
            final String value = split.length > 1 ? split[1].trim() : "";
            
            if(mapping.containsKey(key)){
                throw new DuplicatedKeyException(name, filepath);
            }
            
            final INameValue pair = NameValue.getInstance(name, value);            
            mapping.put(key, pair);
        }
        
        return mapping;
    }
    
    private List<String> getFileContents(File file) throws GeneralPropertiesFileException{
        try {
            return FileUtils.readLines(file);
        } catch (IOException e) {
            throw new GeneralPropertiesFileException(this.filepath, e);
        }
    
    }
    
    private String generateKey(String name){
        if(name == null){
            return "";
        }
        
        return name.trim().toLowerCase();
    }
}
