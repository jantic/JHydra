/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.properties;

import jhydra.core.exceptions.FatalException;
import jhydra.core.properties.INameValue;
import jhydra.core.properties.exceptions.NameNotValidException;
import jhydra.core.properties.NameValue;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author jantic
 */
public class NameValueTests {
    /***Tests on correctly instantiated NameValue****************************************/
   
    @Test
    public void getName_CorrectName() throws FatalException{
        final INameValue nameValue = getBasicNameValue();
        final String expected = "TheName";
        final String actual = nameValue.getName();
        Assert.assertEquals(expected, actual);
    }
        
    @Test
    public void getValue_CorrectValue() throws FatalException{
        final INameValue nameValue = getBasicNameValue();
        final String expected = "The Value";
        final String actual = nameValue.getValue();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void copy_getName_CorrectName() throws FatalException{
        final INameValue nameValue = getBasicNameValue();
        final String expected = "TheName";
        final String actual = nameValue.copy().getName();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void copy_getValue_CorrectValue() throws FatalException{
        final INameValue nameValue = getBasicNameValue();
        final String expected = "The Value";
        final String actual = nameValue.copy().getValue();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void matchesName_capsMatching_true() throws FatalException{
        final INameValue nameValue = getBasicNameValue();
        final Boolean expected = true;
        final Boolean actual = nameValue.matchesName("TheName");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void matchesName_capsNotMatching_true() throws FatalException{
        final INameValue nameValue = getBasicNameValue();
        final Boolean expected = true;
        final Boolean actual = nameValue.matchesName("THENAME");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void matchesName_shortOneChar_false() throws FatalException{
        final INameValue nameValue = getBasicNameValue();
        final Boolean expected = false;
        final Boolean actual = nameValue.matchesName("TheNam");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void matchesName_null_false() throws FatalException{
        final INameValue nameValue = getBasicNameValue();
        final Boolean expected = false;
        final Boolean actual = nameValue.matchesName(null);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void matchesValue_capsMatching_true() throws FatalException{
        final INameValue nameValue = getBasicNameValue();
        final Boolean expected = true;
        final Boolean actual = nameValue.matchesValue("The Value");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void matchesValue_capsNotMatching_false() throws FatalException{
        final INameValue nameValue = getBasicNameValue();
        final Boolean expected = false;
        final Boolean actual = nameValue.matchesValue("THE VALUE");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void matchesValue_shortOneChar_false() throws FatalException{
        final INameValue nameValue = getBasicNameValue();
        final Boolean expected = false;
        final Boolean actual = nameValue.matchesValue("THE VALU");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void matchesValue_null_false() throws FatalException{
        final INameValue nameValue = getBasicNameValue();
        final Boolean expected = false;
        final Boolean actual = nameValue.matchesValue(null);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void matchesValue_initAsEmpty_null_true() throws FatalException{
        final String name = "TheName";
        final String value = "";
        final INameValue nameValue = NameValue.getInstance(name, value);
        final Boolean expected = true;
        final Boolean actual = nameValue.matchesValue(null);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void matchesValue_initAsNull_null_true() throws FatalException{
        final String name = "TheName";
        final String value = null;
        final INameValue nameValue = NameValue.getInstance(name, value);
        final Boolean expected = true;
        final Boolean actual = nameValue.matchesValue(null);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void matchesValue_initAsNull_empty_true() throws FatalException{
        final String name = "TheName";
        final String value = null;
        final INameValue nameValue = NameValue.getInstance(name, value);
        final Boolean expected = true;
        final Boolean actual = nameValue.matchesValue("");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void matchesValue_initAsNull_nonempty_false() throws FatalException{
        final String name = "TheName";
        final String value = null;
        final INameValue nameValue = NameValue.getInstance(name, value);
        final Boolean expected = false;
        final Boolean actual = nameValue.matchesValue("derp");
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void copyWithNewValue_CorrectName() throws FatalException{
        final INameValue nameValue = getBasicNameValue();
        final String expected = "TheName";
        final String actual = nameValue.copyWithNewValue("derp").getName();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void copyWithNewValue_CorrectValue() throws FatalException{
        final INameValue nameValue = getBasicNameValue();
        final String expected = "derp";
        final String actual = nameValue.copyWithNewValue("derp").getValue();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void copyWithNewValue_nullValue_CorrectValue() throws FatalException{
        final INameValue nameValue = getBasicNameValue();
        final String expected = "";
        final String actual = nameValue.copyWithNewValue(null).getValue();
        Assert.assertEquals(expected, actual);
    }
    
    /***Tests on incorrectly instantiated NameValue****************************************/
    
    @Test(expected = NameNotValidException.class)
    public void initNameWithSpace_NameNotValidException() throws FatalException{
        final String name = "The Name";
        final String value = "The Value";
        NameValue.getInstance(name, value);
    }
    
    private INameValue getBasicNameValue() throws NameNotValidException{
        final String name = "TheName";
        final String value = "The Value";
        return NameValue.getInstance(name, value);
    }
}
