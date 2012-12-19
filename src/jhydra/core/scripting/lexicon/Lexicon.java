/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.lexicon;

import jhydra.core.properties.INameValue;
import jhydra.core.properties.NameValue;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jhydra.core.config.IConfig;
import jhydra.core.properties.DuplicatedKeyException;
import jhydra.core.properties.JHydraProperties;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author jantic
 * 
 */
public class Lexicon implements ILexicon {
    private static final String FILE_EXTENSION = "properties";
    private static final String NAME_PATTERN = "^\\w+$";
    private final IConfig config;
    private final Map<String, INameValue> staticRegistry = new HashMap<>();

    public Lexicon(IConfig config) 
            throws LexiconNotFoundException, LexiconFileTypeException, LexiconReadException, DuplicatedKeyException, NameNotValidException{
        this.config = config;
        loadStaticRegistry();
    }
    
    @Override
    public List<INameValue> getAllNameDefaultValuePairs() {
        final List<INameValue> pairs = new ArrayList(this.staticRegistry.values());
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
        validateName(name);
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
    
    @Override
    public void validateName(String name) throws NameNotValidException{
        if(name == null || name.isEmpty()){
            throw new NameNotValidException("", "Name must not be null or empty");
        }
        
        final Pattern pattern = Pattern.compile(NAME_PATTERN);
        final Matcher matcher = pattern.matcher(name.trim());
        
        if(!matcher.matches()){
            final String details = "Variable name attempted is not valid! Attempted: " + name + 
                    ".  Names must be alphanumeric, and not have any spaces in between characters.";
            throw new NameNotValidException(name, details);
        }
    }
 
    private String generateKey(String name){
        if(name == null){
            return "";
        }
        
        return name.trim().toLowerCase();
    }
    
    private void loadStaticRegistry() 
            throws LexiconNotFoundException, LexiconFileTypeException, LexiconReadException, DuplicatedKeyException, NameNotValidException{
        validateLexiconFile();

        try{
            final JHydraProperties properties = new JHydraProperties(this.getFilePath());
            parseAndWriteToStaticRegistry(properties);
        }
        catch(IOException e){
            throw new LexiconReadException(this.getFilePath(), e);
        }
    }
    
    private void parseAndWriteToStaticRegistry(JHydraProperties properties) throws DuplicatedKeyException, NameNotValidException{
        final List<String> names = properties.stringPropertyNames();
        
        for(String name : names){
            validateName(name);
            final String value = properties.getProperty(name).trim();
            final INameValue pair = new NameValue(name, value);
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
