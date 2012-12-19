/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.properties;

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
public class JHydraProperties {
    private static final String SEPARATOR = "=";
    private final Map<String,INameValue> keyValues;
    private final String filepath;
    
    public JHydraProperties(String filepath) throws IOException, DuplicatedKeyException{
        this.filepath = filepath;
        this.keyValues = load(filepath);
    }
    
    public List<String> stringPropertyNames(){
        final List<String> names = new ArrayList();
        
        final List<INameValue> pairs = new ArrayList(keyValues.values());
        
        for(INameValue pair : pairs){
            names.add(pair.getName());
        }
        
        return names;
    }
    
    public String getProperty(String name){
        final String key = generateKey(name);
        return keyValues.get(key).getValue();
    }
    
    public Boolean hasProperty(String name){
        final String key = generateKey(name);
        return keyValues.containsKey(key);
    }
    
    private Map load(String filepath) throws IOException, DuplicatedKeyException{
        final File file = new File(filepath);
        return load(file);
    }
    
    private Map<String,INameValue> load(File file) throws IOException, DuplicatedKeyException{
        if(!file.isFile()){
            throw new IOException("File not found at:  " + file.getAbsolutePath());
        }
        
        final List<String> lines =  FileUtils.readLines(file);
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
            
            final INameValue pair = new NameValue(name, value);            
            mapping.put(key, pair);
        }
        
        return mapping;
    }
    
    private String generateKey(String name){
        if(name == null){
            return "";
        }
        
        return name.trim().toLowerCase();
    }
}
