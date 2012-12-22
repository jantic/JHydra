/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.lexicon;

import jhydra.core.lexicon.ILexicon;
import jhydra.core.lexicon.NameNotInLexiconException;
import jhydra.core.lexicon.Lexicon;
import java.util.List;
import jhydra.core.FatalException;
import jhydra.core.config.IConfig;
import jhydra.core.properties.DuplicatedKeyException;
import jhydra.core.properties.INameValue;
import jhydra.core.properties.IProperties;
import jhydra.core.properties.NameNotValidException;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;


/**
 *
 * @author jantic
 */
public class LexiconTests {
    public LexiconTests() {
    }
    
    /***Tests on correctly configured lexicon****************************************/
   
    @Test
    public void initialization_success() throws FatalException{
        final ILexicon lexicon = new Lexicon(getMockedBasicConfig());
        final List<INameValue> pairs = lexicon.getAllNameDefaultValuePairs();
        final Integer expected = 6;
        final Integer actual = pairs.size();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getDefaultValueOfVariable_FirstName_Mr() throws FatalException{
        final ILexicon lexicon = new Lexicon(getMockedBasicConfig());
        final String expected = "Mr";
        final String actual = lexicon.getNameValue("FirstName").getValue();
        Assert.assertEquals(expected, actual);
    }
    
     @Test
    public void getDefaultValueOfVariable_FirstName_CaseInsensitive_Mr() throws FatalException{
        final ILexicon lexicon = new Lexicon(getMockedBasicConfig());
        final String expected = "Mr";
        final String actual = lexicon.getNameValue("FIRSTNAME").getValue();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getNameOfVariable_FirstName_FirstName() throws FatalException{
        final ILexicon lexicon = new Lexicon(getMockedBasicConfig());
        final String expected = "FirstName";
        final String actual = lexicon.getNameValue("FirstName").getName();
        Assert.assertEquals(expected, actual);
    }
    
    @Test(expected = NameNotValidException.class)
    public void getNameValue_null_NameNotValidException() throws FatalException{
        final ILexicon lexicon = new Lexicon(getMockedBasicConfig());
        lexicon.getNameValue(null);
    }
    
    @Test(expected = NameNotValidException.class)
    public void getNameValue_empty_NameNotValidException() throws FatalException{
        final ILexicon lexicon = new Lexicon(getMockedBasicConfig());
        lexicon.getNameValue("");
    }
    
    @Test(expected = NameNotInLexiconException.class)
    public void getNameValue_NonExistantName_NameNotInLexiconException() throws FatalException{
        final ILexicon lexicon = new Lexicon(getMockedBasicConfig());
        lexicon.getNameValue("FirstNameDerp");
    }
    
    @Test
    public void getValueOfVariable_ValueWithSpaces_His_Own_Music() throws FatalException{
        final ILexicon lexicon = new Lexicon(getMockedBasicConfig());
        final String expected = "His own music";
        final String actual = lexicon.getNameValue("FavoriteMusic").getValue();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getValueOfVariable_ValueWithEqualsSign_EqMister() throws FatalException{
        final ILexicon lexicon = new Lexicon(getMockedBasicConfig());
        final String expected = "=Mister";
        final String actual = lexicon.getNameValue("LastName").getValue();
        Assert.assertEquals(expected, actual);
    }
    
    @Test(expected = NameNotValidException.class)
    public void getNameValue_NameWithSpaces_NameNotValidException() throws FatalException{
        final ILexicon lexicon = new Lexicon(getMockedBasicConfig());
        lexicon.getNameValue("Titley Title");
    }
    
   @Test
    public void specCharsValues_getNameValue_CorrectValue() throws FatalException{
        final IConfig config = mock(IConfig.class);
        when(config.getLexiconPath()).thenReturn("./test/test data/speccharvalues_lexicon.properties");
        final ILexicon lexicon = new Lexicon(config);
        final String expected = "!#$%^&*()-_}{][\\|/><.,;\"-+.:?=";
        final String actual = lexicon.getNameValue("FirstName").getValue();
        Assert.assertEquals(expected, actual);
    }
    
   @Test
    public void numericValues_getNameValue_CorrectValue() throws FatalException{
        final IConfig config = mock(IConfig.class);
        when(config.getLexiconPath()).thenReturn("./test/test data/speccharvalues_lexicon.properties");
        final ILexicon lexicon = new Lexicon(config);
        final String expected = "1234567890";
        final String actual = lexicon.getNameValue("LastName").getValue();
        Assert.assertEquals(expected, actual);
    }
    
    /***Tests on incorrectly configured lexicons****************************************/
    
    @Test(expected = DuplicatedKeyException.class)
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void initialization_DupeKeyLexicon_DuplicatedKeyException() throws FatalException{
        final IConfig config = mock(IConfig.class);
        when(config.getLexiconPath()).thenReturn("./test/test data/dupekey_lexicon.properties");
        new Lexicon(config);
    }
    
    @Test(expected = NameNotValidException.class)
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void initialization_NameWithSpaces_NameNotValidException() throws FatalException{
        final IConfig config = mock(IConfig.class);
        when(config.getLexiconPath()).thenReturn("./test/test data/spacedname_lexicon.properties");
        new Lexicon(config);
    }
    
    private IConfig getMockedBasicConfig(){
        final IConfig config = mock(IConfig.class);
        when(config.getLexiconPath()).thenReturn("./test/test data/basic_lexicon.properties");
        return config;
    }
    
}
