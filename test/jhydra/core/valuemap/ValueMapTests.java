/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.valuemap;

import jhydra.core.config.IConfig;
import jhydra.core.exceptions.FatalException;
import jhydra.core.lexicon.ILexicon;
import jhydra.core.lexicon.Lexicon;
import jhydra.core.lexicon.exceptions.NameNotInLexiconException;
import jhydra.core.properties.exceptions.NameNotValidException;
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
    
    /***Tests using correctly configured lexicon, for getValue****************************************/ 
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
   
   
    /***Tests using correctly configured lexicon, for setValue****************************************/ 
    @Test
    public void setValue_FirstName_ModifiedOnce_getValue_Smacky() throws FatalException{
        final ILexicon lexicon = getMockedBasicLexicon();
        final IValueMap valueMap = new ValueMap(lexicon);
        valueMap.setValue("FirstName", "Smacky");
        final String expected = "Smacky";
        final String actual = valueMap.getValue("FirstName");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void setValue_FirstName_ModifiedTwice_getValue_The_Frog() throws FatalException{
        final ILexicon lexicon = getMockedBasicLexicon();
        final IValueMap valueMap = new ValueMap(lexicon);
        valueMap.setValue("FirstName", "Smacky");
        valueMap.setValue("FirstName", "The Frog");
        final String expected = "The Frog";
        final String actual = valueMap.getValue("FirstName");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void setValue_SpecialChars_getValue_CorrectValue() throws FatalException{
        final ILexicon lexicon = getMockedBasicLexicon();
        final IValueMap valueMap = new ValueMap(lexicon);
        final String newValue = "!#$%^&*()-_}{][\\|/><.,;\"-+.:?=";
        valueMap.setValue("FirstName", newValue);
        final String expected = newValue;
        final String actual = valueMap.getValue("FirstName");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void setValue_NumericalChars_getValue_1234567890() throws FatalException{
        final ILexicon lexicon = getMockedBasicLexicon();
        final IValueMap valueMap = new ValueMap(lexicon);
        final String newValue = "1234567890";
        valueMap.setValue("FirstName", newValue);
        final String expected = newValue;
        final String actual = valueMap.getValue("FirstName");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void setValue_null_getValue_empty() throws FatalException{
        final ILexicon lexicon = getMockedBasicLexicon();
        final IValueMap valueMap = new ValueMap(lexicon);
        valueMap.setValue("FirstName", null);
        final String expected = "";
        final String actual = valueMap.getValue("FirstName");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void setValue_empty_getValue_empty() throws FatalException{
        final ILexicon lexicon = getMockedBasicLexicon();
        final IValueMap valueMap = new ValueMap(lexicon);
        valueMap.setValue("FirstName", "");
        final String expected = "";
        final String actual = valueMap.getValue("FirstName");
        Assert.assertEquals(expected, actual);
    }
    
    @Test(expected = NameNotInLexiconException.class)
    public void setValue_invalidName_NameNotInLexiconException() throws FatalException{
        final ILexicon lexicon = getMockedBasicLexicon();
        final IValueMap valueMap = new ValueMap(lexicon);
        valueMap.setValue("Derp", "");
    }
    
   /***Tests using correctly configured lexicon, for hasValue****************************************/ 
    
   @Test
    public void hasValue_FirstName_true() throws FatalException{
        final ILexicon lexicon = getMockedBasicLexicon();
        final IValueMap valueMap = new ValueMap(lexicon);
        final Boolean expected = true;
        final Boolean actual = valueMap.hasValue("FirstName");
        Assert.assertEquals(expected, actual);
    }
   
    @Test
    public void hasValue_SecondName_false() throws FatalException{
        final ILexicon lexicon = getMockedBasicLexicon();
        final IValueMap valueMap = new ValueMap(lexicon);
        final Boolean expected = false;
        final Boolean actual = valueMap.hasValue("SecondName");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void hasValue_InvalidName_false() throws FatalException{
        final ILexicon lexicon = getMockedBasicLexicon();
        final IValueMap valueMap = new ValueMap(lexicon);
        final Boolean expected = false;
        final Boolean actual = valueMap.hasValue("First Name");
        Assert.assertEquals(expected, actual);
    }
    
    
    @Test
    public void hasValue_SpecChars_false() throws FatalException{
        final ILexicon lexicon = getMockedBasicLexicon();
        final IValueMap valueMap = new ValueMap(lexicon);
        final Boolean expected = false;
        final Boolean actual = valueMap.hasValue("!#$%^&*()-_}{][\\|/><.,;\"-+.:?=");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void hasValue_null_false() throws FatalException{
        final ILexicon lexicon = getMockedBasicLexicon();
        final IValueMap valueMap = new ValueMap(lexicon);
        final Boolean expected = false;
        final Boolean actual = valueMap.hasValue(null);
        Assert.assertEquals(expected, actual);
    }
    
   @Test
    public void hasValue_empty_false() throws FatalException{
        final ILexicon lexicon = getMockedBasicLexicon();
        final IValueMap valueMap = new ValueMap(lexicon);
        final Boolean expected = false;
        final Boolean actual = valueMap.hasValue("");
        Assert.assertEquals(expected, actual);
    }
    
   /***PRIVATE****************************************/
   
    private ILexicon getMockedBasicLexicon() throws FatalException{
        return new Lexicon(getMockedBasicConfig());
    }
     
    private IConfig getMockedBasicConfig(){
        final IConfig config = mock(IConfig.class);
        when(config.getLexiconPath()).thenReturn("./test/test data/basic_lexicon.properties");
        return config;
    }
    
}
