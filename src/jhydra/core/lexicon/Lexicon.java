/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.lexicon;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jhydra.core.config.IConfig;
import jhydra.core.lexicon.exceptions.LexiconFileTypeException;
import jhydra.core.lexicon.exceptions.LexiconNotFoundException;
import jhydra.core.lexicon.exceptions.LexiconReadException;
import jhydra.core.lexicon.exceptions.NameNotInLexiconException;
import jhydra.core.properties.INameValue;
import jhydra.core.properties.INameValueValidator;
import jhydra.core.properties.NameValue;
import jhydra.core.properties.NameValueValidator;
import jhydra.core.properties.Properties;
import jhydra.core.properties.exceptions.DuplicatedKeyException;
import jhydra.core.properties.exceptions.GeneralPropertiesFileException;
import jhydra.core.properties.exceptions.NameNotInPropertiesFileException;
import jhydra.core.properties.exceptions.NameNotValidException;
import jhydra.core.properties.exceptions.PropertiesFileNotFoundException;
import jhydra.core.properties.exceptions.PropertiesFileReadPermissionsException;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author jantic
 * 
 */
public class Lexicon implements ILexicon {
    private static final String FILE_EXTENSION = "properties";
    private final IConfig config;
    private final Map<String, INameValue> staticRegistry = new HashMap<>();
    private final INameValueValidator nameValueValidator;

    public Lexicon(IConfig config) 
            throws LexiconNotFoundException, LexiconFileTypeException, LexiconReadException, 
            DuplicatedKeyException, NameNotValidException, NameNotInPropertiesFileException{
        this.config = config;
        this.nameValueValidator = new NameValueValidator();
        loadStaticRegistry();
    }
    
    @Override
    public List<INameValue> getAllNameDefaultValuePairs() {
        final List<INameValue> pairs = new ArrayList<>(this.staticRegistry.values());
        return pairs;
    }

    @Override
    public void registerVariable(String variableName, String defaultValue) {
        //TODO:  Implement this!
    }

    @Override
    public String getFilePath() {
        return config.getLexiconPath();
    }
    
    @Override
    public INameValue getNameValue(String name) throws NameNotInLexiconException, NameNotValidException{
        nameValueValidator.validateName(name);
        if(!hasNameValue(name)){
            throw new NameNotInLexiconException(name, getFilePath());
        }
        final String key = generateKey(name);
        return staticRegistry.get(key);
    }
    
    @Override 
    public Boolean hasNameValue(String name){
        final String key = generateKey(name);
        return staticRegistry.containsKey(key);
    }
    
    private String generateKey(String name){
        if(name == null){
            return "";
        }
        
        return name.trim().toLowerCase();
    }
    
    private void loadStaticRegistry() 
            throws LexiconNotFoundException, LexiconFileTypeException, LexiconReadException, 
            DuplicatedKeyException, NameNotValidException, NameNotInPropertiesFileException{
        
        validateLexiconFile();

        try{
            final Properties properties = new Properties(this.getFilePath());
            parseAndWriteToStaticRegistry(properties);
        }
        catch(PropertiesFileNotFoundException e){
            throw new LexiconNotFoundException(this.getFilePath(), e);
        }
        catch(PropertiesFileReadPermissionsException|GeneralPropertiesFileException e){
            throw new LexiconReadException(this.getFilePath(), e);
        }
    }
    
    private void parseAndWriteToStaticRegistry(Properties properties) throws DuplicatedKeyException, NameNotValidException, NameNotInPropertiesFileException{
        final List<String> names = properties.getAllPropertyNames();
        
        for(String name : names){
            nameValueValidator.validateName(name);
            final String value = properties.getProperty(name).trim();
            final INameValue pair = NameValue.getInstance(name, value);
            final String key = generateKey(name);
            
            if(this.staticRegistry.containsKey(key)){
                throw new DuplicatedKeyException(name, getFilePath());
            }
            else{
                this.staticRegistry.put(key, pair);
            }
        }
    }
    
    private void validateLexiconFile() 
            throws LexiconNotFoundException, LexiconFileTypeException{      
        final String path = getFilePath();
        final File file =  new File(path);
        
        if(!file.isFile()){
            throw new LexiconNotFoundException(this.getFilePath());
        }
        
        final String extension = FilenameUtils.getExtension(this.getFilePath());
        
        if(!extension.equalsIgnoreCase(FILE_EXTENSION)){
            throw new LexiconFileTypeException(FILE_EXTENSION, extension, this.getFilePath());
        }
    }
}
