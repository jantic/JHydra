/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.lexicon;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import jhydra.core.config.IConfig;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author jantic
 */
public class Lexicon implements ILexicon {
    private static final String FILE_EXTENSION = "properties";
    private final IConfig config;
    private final Map<String, INameValue> staticRegistry = new HashMap<>();

    public Lexicon(IConfig config) 
            throws LexiconNotFoundException, LexiconFileTypeException, LexiconReadException{
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
    
    private String generateKey(String name){
        if(name == null){
            return "";
        }
        
        return name.trim().toLowerCase();
    }
    
    private void loadStaticRegistry() 
            throws LexiconNotFoundException, LexiconFileTypeException, LexiconReadException{
        validateLexiconFile();

        try{
            final Properties properties = new Properties();
            properties.load(new FileInputStream(this.getFilePath()));
            parseAndWriteToStaticRegistry(properties);
        }
        catch(IOException e){
            throw new LexiconReadException(this.getFilePath(), e);
        }
    }
    
    private void parseAndWriteToStaticRegistry(Properties properties){
        final Set<String> names = properties.stringPropertyNames();
        
        for(String name : names){
            final String value = properties.getProperty(name).trim();
            final INameValue pair = new NameValue(name, value);
            final String key = generateKey(name);
            this.staticRegistry.put(key, pair);
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
