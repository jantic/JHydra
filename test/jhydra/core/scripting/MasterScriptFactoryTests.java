package jhydra.core.scripting;

import jhydra.core.config.IRuntimeConfig;
import jhydra.core.logging.ILog;
import jhydra.core.scripting.exceptions.ScriptNotExistException;
import jhydra.core.scripting.scriptinfo.exceptions.ScriptInfoLoadException;
import jhydra.core.uinav.IMasterNavigator;
import jhydra.core.valuemap.IValueMap;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Author: jantic
 * Date: 4/4/13
 */
public class MasterScriptFactoryTests {
    //Needed to verify sum set in NormalScript from project 1.
    private String sum = "";
    //Needed to verify message in Proj2Script from project 2
    private String message = "";

    /***Tests normally executing, existing scripts ****************************************/
    @Test
    public void getScript_proj1_normalScript_getName_NormalScript() throws Exception{
        final IScript script = getProj1Script("NormalScript");
        final String expected = "NormalScript";
        final String actual = script.getName();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void hasScript_proj1_normalScript_true() throws Exception{
        final MasterScriptFactory scriptFactory = this.getMasterScriptFactory(1);
        final Boolean expected = true;
        final Boolean actual = scriptFactory.hasScript("NormalScript");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getScript_proj2_proj2Script_getName_Proj2Script() throws Exception{
        final IScript script = getProj2Script("Proj2Script");
        final String expected = "Proj2Script";
        final String actual = script.getName();
        Assert.assertEquals(expected, actual);
    }

    /***Tests non-existing scripts ******************************************************/

    @Test
    public void hasScript_proj1_proj2Script_false() throws Exception{
        final MasterScriptFactory scriptFactory = this.getMasterScriptFactory(1);
        final Boolean expected = false;
        final Boolean actual = scriptFactory.hasScript("Proj2Script");
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = ScriptNotExistException.class)
    public void getScript_proj1_proj2Script_throwsScriptNotExistException() throws Exception{
        getProj1Script("Proj2Script");
    }

    @Test(expected = ScriptNotExistException.class)
    public void getScript_proj1_null_throwsScriptNotExistException() throws Exception{
        getProj1Script(null);
    }

    @Test(expected = ScriptNotExistException.class)
    public void getScript_proj1_empty_throwsScriptNotExistException() throws Exception{
        getProj1Script("");
    }

    /***PRIVATE METHODS*********************************************************************/

    private IScript getProj1Script(String name) throws Exception{
        final IValueMap valueMap = mock(IValueMap.class);
        when(valueMap.getValue("num1")).thenReturn("5.0");
        when(valueMap.getValue("num2")).thenReturn("6.0");

        sum = "";

        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                sum = (String) invocation.getArguments()[1];
                return null;
            }
        }).when(valueMap).setValue(eq("sum"), anyString());
        final MasterScriptFactory masterScriptFactory = getMasterScriptFactory(1);
        final IMasterNavigator navigator = mock(IMasterNavigator.class);
        return masterScriptFactory.getScript(name, valueMap, navigator);
    }

    private IScript getProj2Script(String name) throws Exception{
        final IValueMap valueMap = mock(IValueMap.class);
        message = "";
        doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                message = (String) invocation.getArguments()[1];
                return null;
            }
        }).when(valueMap).setValue(eq("message"), anyString());

        final MasterScriptFactory masterScriptFactory = getMasterScriptFactory(2);
        final IMasterNavigator navigator = mock(IMasterNavigator.class);
        return masterScriptFactory.getScript(name, valueMap, navigator);
    }

    private MasterScriptFactory getMasterScriptFactory(Integer projNum) throws ScriptInfoLoadException {
        final IRuntimeConfig config = mock(IRuntimeConfig.class);
        final String projectsDirectory = "./test projects/project " + projNum.toString();
        final List<URI> scriptDirectories = new ArrayList<>();
        scriptDirectories.add(getURI(projectsDirectory + "/scripts/"));
        scriptDirectories.add(getURI(projectsDirectory + "/alt scripts/"));
        when(config.getScriptDirectories()).thenReturn(scriptDirectories);
        when(config.getProjectDirectory()).thenReturn(getURI(projectsDirectory));
        when(config.getScriptMaxNumTries()).thenReturn(1);
        final ILog log = mock(ILog.class);
        return new MasterScriptFactory(config, log);
    }

    private URI getURI(String relativePath){
        final File file = new File(relativePath);
        return file.toURI();
    }
}
