/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.config;

import jhydra.core.exceptions.FatalException;
import org.junit.Assert;
import org.junit.Test;


/**
 *
 * @author jantic
 */
public class ProgramConfigTest {
    @Test
    public void getProgramName_CorrectValue() throws FatalException{
        final IProgramConfig programConfig = ProgramConfig.getInstance();
        final String expected = "JHydra";
        final String actual = programConfig.getProgramName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getSharedLexiconPath_CorrectValue() throws FatalException{
        final IProgramConfig programConfig = ProgramConfig.getInstance();
        final String expected = programConfig.getProgramDirectory().toString() + "shared/lexicon.properties";
        final String actual = programConfig.getSharedLexiconPath().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getProgramDirectory_CorrectValue() throws FatalException{
        final IProgramConfig programConfig = ProgramConfig.getInstance();
        final String expected = "file:" + System.getProperty("user.dir") + "/";
        final String actual = programConfig.getProgramDirectory().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getProjectsDirectory_CorrectValue() throws FatalException{
        final IProgramConfig programConfig = ProgramConfig.getInstance();
        final String expected = programConfig.getProgramDirectory().toString() + "projects";
        final String actual = programConfig.getProjectsDirectory().toString();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getSharedScriptsDirectory_CorrectValue() throws FatalException{
        final IProgramConfig programConfig = ProgramConfig.getInstance();
        final String expected =  programConfig.getProgramDirectory().toString() + "shared/scripts";
        final String actual = programConfig.getSharedScriptsDirectory().toString();
        Assert.assertEquals(expected, actual);
    }

}
