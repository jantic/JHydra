/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.properties;

import java.io.IOException;
import java.util.List;
import jhydra.core.FatalException;
import jhydra.core.properties.DuplicatedKeyException;
import jhydra.core.properties.IProperties;
import jhydra.core.properties.NameNotInPropertiesFileException;
import jhydra.core.properties.NameNotValidException;
import jhydra.core.properties.Properties;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author jantic
 */
public class PropertiesTests {
    /***Tests on correctly configured properties file****************************************/
   
    @Test
    public void getProperty_matchingCaps_CorrectValue() throws FatalException, IOException{
        final IProperties properties = getBasicProperties();
        final String expected = "His own music";
        final String actual = properties.getProperty("FavoriteMusic");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getProperty_nonMatchingCaps_CorrectValue() throws FatalException, IOException{
        final IProperties properties = getBasicProperties();
        final String expected = "His own music";
        final String actual = properties.getProperty("FAVORITEMUSIC");
        Assert.assertEquals(expected, actual);
    }
     
    @Test(expected = NameNotInPropertiesFileException.class)
    public void getProperty_nonExistantProperty_NameNotValidException() throws FatalException, IOException{
        final IProperties properties = getBasicProperties();
        properties.getProperty("FavoriteMovie");
    }

    @Test
    public void hasProperty_matchingCaps_true() throws FatalException, IOException{
        final IProperties properties = getBasicProperties();
        final Boolean expected = true;
        final Boolean actual = properties.hasProperty("FavoriteMusic");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void hasProperty_nonMatchingCaps_true() throws FatalException, IOException{
        final IProperties properties = getBasicProperties();
        final Boolean expected = true;
        final Boolean actual = properties.hasProperty("FAVORITEMUSIC");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void hasProperty_oneCharShort_false() throws FatalException, IOException{
        final IProperties properties = getBasicProperties();
        final Boolean expected = false;
        final Boolean actual = properties.hasProperty("FavoriteMusi");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void hasProperty_empty_false() throws FatalException, IOException{
        final IProperties properties = getBasicProperties();
        final Boolean expected = false;
        final Boolean actual = properties.hasProperty("");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void hasProperty_null_false() throws FatalException, IOException{
        final IProperties properties = getBasicProperties();
        final Boolean expected = false;
        final Boolean actual = properties.hasProperty(null);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getAllPropertyNames_FirstItemNameCorrect() throws FatalException, IOException{
        final IProperties properties = getBasicProperties();
        final List<String> names = properties.getAllPropertyNames();
        final String expected = names.get(0);
        final String actual = "Spouse";
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getAllPropertyNames_LastItemNameCorrect() throws FatalException, IOException{
        final IProperties properties = getBasicProperties();
        final List<String> names = properties.getAllPropertyNames();
        final String expected = "TitlelyTitle";
        final String actual = names.get(5);
        Assert.assertEquals(expected, actual);
    }
    
    private IProperties getBasicProperties() throws FatalException, IOException{
        return new Properties("./test/test data/basic_lexicon.properties");
    }
}
