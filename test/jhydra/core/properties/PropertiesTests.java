/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.properties;

import java.util.List;
import jhydra.core.exceptions.FatalException;
import jhydra.core.properties.exceptions.DuplicatedKeyException;
import jhydra.core.properties.exceptions.NameNotInPropertiesFileException;
import jhydra.core.properties.exceptions.NameNotValidException;
import jhydra.core.properties.exceptions.PropertiesFileNotFoundException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author jantic
 */
public class PropertiesTests {
    /***Tests on correctly configured properties file****************************************/
   
    @Test
    public void getProperty_matchingCaps_CorrectValue() throws FatalException{
        final IProperties properties = getBasicProperties();
        final String expected = "His own music";
        final String actual = properties.getProperty("FavoriteMusic");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getProperty_nonMatchingCaps_CorrectValue() throws FatalException{
        final IProperties properties = getBasicProperties();
        final String expected = "His own music";
        final String actual = properties.getProperty("FAVORITEMUSIC");
        Assert.assertEquals(expected, actual);
    }
     
    @Test(expected = NameNotInPropertiesFileException.class)
    public void getProperty_nonExistantProperty_NameNotValidException() throws FatalException{
        final IProperties properties = getBasicProperties();
        properties.getProperty("FavoriteMovie");
    }

    @Test
    public void hasProperty_matchingCaps_true() throws FatalException{
        final IProperties properties = getBasicProperties();
        final Boolean expected = true;
        final Boolean actual = properties.hasProperty("FavoriteMusic");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void hasProperty_nonMatchingCaps_true() throws FatalException{
        final IProperties properties = getBasicProperties();
        final Boolean expected = true;
        final Boolean actual = properties.hasProperty("FAVORITEMUSIC");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void hasProperty_oneCharShort_false() throws FatalException{
        final IProperties properties = getBasicProperties();
        final Boolean expected = false;
        final Boolean actual = properties.hasProperty("FavoriteMusi");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void hasProperty_empty_false() throws FatalException{
        final IProperties properties = getBasicProperties();
        final Boolean expected = false;
        final Boolean actual = properties.hasProperty("");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void hasProperty_null_false() throws FatalException{
        final IProperties properties = getBasicProperties();
        final Boolean expected = false;
        final Boolean actual = properties.hasProperty(null);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getAllPropertyNames_FirstItemNameCorrect() throws FatalException{
        final IProperties properties = getBasicProperties();
        final List<String> names = properties.getAllPropertyNames();
        final String expected = names.get(0);
        final String actual = "Spouse";
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getAllPropertyNames_LastItemNameCorrect() throws FatalException{
        final IProperties properties = getBasicProperties();
        final List<String> names = properties.getAllPropertyNames();
        final String expected = "TitlelyTitle";
        final String actual = names.get(5);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void specCharsValues_getProperty_CorrectValue() throws FatalException{
        final IProperties properties = getSpecCharsProperties();
        final String expected = "!#$%^&*()-_}{][\\|/><.,;\"-+.:?=";
        final String actual = properties.getProperty("FirstName");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void numericValues_getProperty_FirstName_CorrectValue() throws FatalException{
        final IProperties properties = getSpecCharsProperties();
        final String expected = "1234567890";
        final String actual = properties.getProperty("LastName");
        Assert.assertEquals(expected, actual);
    }
    
    /***Tests on incorrectly configured properties file****************************************/ 
    
    @Test(expected = DuplicatedKeyException.class)
    public void loadDupeKeyProperties_DuplicatedKeyException() throws FatalException{
        getDupeKeyProperties();
    }
    
    @Test(expected = PropertiesFileNotFoundException.class)
    public void loadNonExistingProperties_PropertiesFileNotFoundException() throws FatalException{
        getNonExistingProperties();
    }
    
    @Test(expected = NameNotValidException.class)
    public void loadSpacedNameProperties_NameNotValidException() throws FatalException{
        getSpacedNameProperties();
    }
    
    /***Private methods****************************************/ 
    
    private IProperties getBasicProperties() throws FatalException{
        return new Properties("./test/test data/basic_lexicon.properties");
    }
    
    private IProperties getDupeKeyProperties() throws FatalException{
        return new Properties("./test/test data/dupekey_lexicon.properties");
    }
    
    private IProperties getNonExistingProperties() throws FatalException{
        return new Properties("./test/test data/derp.properties");
    }
    
    private IProperties getSpacedNameProperties() throws FatalException{
        return new Properties("./test/test data/spacedname_lexicon.properties");
    }
    
    private IProperties getSpecCharsProperties() throws FatalException{
        return new Properties("./test/test data/speccharvalues_lexicon.properties");
    }
}
