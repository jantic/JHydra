/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.lexicon;

import jhydra.core.config.IProjectConfig;
import jhydra.core.exceptions.FatalException;
import jhydra.core.lexicon.exceptions.LexiconFileTypeException;
import jhydra.core.lexicon.exceptions.LexiconNotFoundException;
import jhydra.core.lexicon.exceptions.LexiconReadException;
import jhydra.core.lexicon.exceptions.NameNotInLexiconException;
import jhydra.core.properties.*;
import jhydra.core.properties.exceptions.*;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jantic
 * 
 */
public class Lexicon implements ILexicon {
    private static final String FILE_EXTENSION = "properties";
    private final IProjectConfig config;
    private final Map<String, INameValue> staticRegistry = new HashMap<>();
    private final INameValueValidator nameValueValidator;

    public Lexicon(IProjectConfig config) throws FatalException{
        this.config = config;
        this.nameValueValidator = new NameValueValidator();
        loadStaticRegistry();
    }
    
    @Override
    public List<INameValue> getAllNameDefaultValuePairs() {
        return new ArrayList<>(this.staticRegistry.values());
    }

    @Override
    public void registerVariable(String variableName, String defaultValue) {
        //TODO:  Implement this!
    }

    @Override
    public URI getFilePath() {
        return config.getLexiconPath();
    }
    
    @Override
    public INameValue getNameValue(String name) throws NameNotInLexiconException, NameNotValidException{
        nameValueValidator.validateName(name);
        if(!hasNameValue(name)){
            throw new NameNotInLexiconException(name, getFilePath().toString());
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
            throw new LexiconNotFoundException(this.getFilePath().toString(), e);
        }
        catch(PropertiesFileReadPermissionsException|GeneralPropertiesFileException e){
            throw new LexiconReadException(this.getFilePath().toString(), e);
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
                throw new DuplicatedKeyException(name, getFilePath().toString());
            }
            else{
                this.staticRegistry.put(key, pair);
            }
        }
    }
    
    private void validateLexiconFile() 
            throws LexiconNotFoundException, LexiconFileTypeException{      
        final URI path = getFilePath();
        final File file =  new File(path);
        
        if(!file.isFile()){
            throw new LexiconNotFoundException(this.getFilePath().toString());
        }
        
        final String extension = FilenameUtils.getExtension(this.getFilePath().toString());
        
        if(!extension.equalsIgnoreCase(FILE_EXTENSION)){
            throw new LexiconFileTypeException(FILE_EXTENSION, extension, this.getFilePath().toString());
        }
    }
}
