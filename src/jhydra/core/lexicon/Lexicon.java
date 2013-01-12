/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.lexicon;

import jhydra.core.config.IRuntimeConfig;
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
    private final IRuntimeConfig config;
    private final Map<String, INameValue> staticRegistry = new HashMap<>();
    private final INameValueValidator nameValueValidator;

    public Lexicon(IRuntimeConfig config) throws FatalException{
        this.config = config;
        this.nameValueValidator = new NameValueValidator();
        loadAllStaticRegistries();
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
    public List<URI> getFilePaths() {
        return config.getLexiconPaths();
    }
    
    @Override
    public INameValue getNameValue(String name) throws NameNotInLexiconException, NameNotValidException{
        nameValueValidator.validateName(name);
        if(!hasNameValue(name)){
            throw new NameNotInLexiconException(name, getFilePaths());
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

    private void loadAllStaticRegistries() throws FatalException{
        final List<URI> paths = this.getFilePaths();

        for(URI path : paths){
            loadStaticRegistry(path);
        }
    }
    
    private void loadStaticRegistry(URI path) throws FatalException{
        validateLexiconFile(path);

        try{
            final Properties properties = new Properties(path);
            parseAndWriteToStaticRegistry(properties);
        }
        catch(PropertiesFileNotFoundException e){
            throw new LexiconNotFoundException(path.toString(), e);
        }
        catch(PropertiesFileReadPermissionsException|GeneralPropertiesFileException e){
            throw new LexiconReadException(path.toString(), e);
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
                throw new DuplicatedKeyException(name, properties.getFilePath().toString());
            }
            else{
                this.staticRegistry.put(key, pair);
            }
        }
    }
    
    private void validateLexiconFile(URI path)
            throws LexiconNotFoundException, LexiconFileTypeException{
        final File file =  new File(path);
        
        if(!file.isFile()){
            throw new LexiconNotFoundException(path.toString());
        }
        
        final String extension = FilenameUtils.getExtension(path.toString());
        
        if(!extension.equalsIgnoreCase(FILE_EXTENSION)){
            throw new LexiconFileTypeException(FILE_EXTENSION, extension, path.toString());
        }
    }
}
