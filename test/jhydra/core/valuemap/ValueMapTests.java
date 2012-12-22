/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.valuemap;

import java.util.List;
import jhydra.core.FatalException;
import jhydra.core.config.IConfig;
import jhydra.core.lexicon.ILexicon;
import jhydra.core.lexicon.Lexicon;
import jhydra.core.lexicon.NameNotInLexiconException;
import jhydra.core.properties.DuplicatedKeyException;
import jhydra.core.properties.INameValue;
import jhydra.core.properties.NameNotValidException;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;


/**
 *
 * @author jantic
 */
public class ValueMapTests {
    public ValueMapTests() {
    }
    
    /***Tests using correctly configured lexicon, no modifications of state****************************************/ 
    @Test
    public void getValue_FirstName_Mr() throws FatalException{
        final ILexicon lexicon = getMockedBasicLexicon();
        final IValueMap valueMap = new ValueMap(lexicon);
        final String expected = "Mr";
        final String actual = valueMap.getValue("FirstName");
        Assert.assertEquals(expected, actual);
    }
    
     @Test
    public void getValue_FirstName_CaseInsensitive_Mr() throws FatalException{
        final ILexicon lexicon = getMockedBasicLexicon();
        final IValueMap valueMap = new ValueMap(lexicon);
        final String expected = "Mr";
        final String actual = valueMap.getValue("FIRSTNAME");
        Assert.assertEquals(expected, actual);
    }
    
    
    @Test(expected = NameNotValidException.class)
    public void getValue_null_NameNotValidException() throws FatalException{
        final ILexicon lexicon = getMockedBasicLexicon();
        final IValueMap valueMap = new ValueMap(lexicon);
        valueMap.getValue(null);
    }
    
    @Test(expected = NameNotValidException.class)
    public void getValue_empty_NameNotValidException() throws FatalException{
        final ILexicon lexicon = getMockedBasicLexicon();
        final IValueMap valueMap = new ValueMap(lexicon);
        valueMap.getValue("");
    }
    
    @Test(expected = NameNotInLexiconException.class)
    public void getValue_NonExistantName_NameNotInLexiconException() throws FatalException{
        final ILexicon lexicon = getMockedBasicLexicon();
        final IValueMap valueMap = new ValueMap(lexicon);
        valueMap.getValue("FirstNameDerp");
    }
    
    @Test
    public void getValue_ValueWithSpaces_His_Own_Music() throws FatalException{
        final ILexicon lexicon = getMockedBasicLexicon();
        final IValueMap valueMap = new ValueMap(lexicon);
        final String expected = "His own music";
        final String actual = valueMap.getValue("FavoriteMusic");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getValue_ValueWithEqualsSign_EqMister() throws FatalException{
        final ILexicon lexicon = getMockedBasicLexicon();
        final IValueMap valueMap = new ValueMap(lexicon);
        final String expected = "=Mister";
        final String actual = valueMap.getValue("LastName");
        Assert.assertEquals(expected, actual);
    }
    
    @Test(expected = NameNotValidException.class)
    public void getValue_NameWithSpaces_NameNotValidException() throws FatalException{
        final ILexicon lexicon = getMockedBasicLexicon();
        final IValueMap valueMap = new ValueMap(lexicon);
        valueMap.getValue("Titley Title");
    }
    
   @Test
    public void specCharsValues_getValue_CorrectValue() throws FatalException{
        final IConfig config = mock(IConfig.class);
        when(config.getLexiconPath()).thenReturn("./test/test data/speccharvalues_lexicon.properties");
        final ILexicon lexicon = new Lexicon(config);
        final IValueMap valueMap = new ValueMap(lexicon);
        final String expected = "!#$%^&*()-_}{][\\|/><.,;\"-+.:?=";
        final String actual = valueMap.getValue("FirstName");
        Assert.assertEquals(expected, actual);
    }
    
   @Test
    public void numericValues_getValue_CorrectValue() throws FatalException{
        final IConfig config = mock(IConfig.class);
        when(config.getLexiconPath()).thenReturn("./test/test data/speccharvalues_lexicon.properties");
        final ILexicon lexicon = new Lexicon(config);
        final IValueMap valueMap = new ValueMap(lexicon);
        final String expected = "1234567890";
        final String actual = valueMap.getValue("LastName");
        Assert.assertEquals(expected, actual);
    }
      
    private ILexicon getMockedBasicLexicon() throws FatalException{
        return new Lexicon(getMockedBasicConfig());
    }
    
    private IConfig getMockedBasicConfig(){
        final IConfig config = mock(IConfig.class);
        when(config.getLexiconPath()).thenReturn("./test/test data/basic_lexicon.properties");
        return config;
    }
    
}
