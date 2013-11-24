package jhydra.core.scripting;

import jhydra.core.config.IRuntimeConfig;
import jhydra.core.scripting.scriptinfo.IScriptInfo;
import jhydra.core.scripting.scriptinfo.ScriptInfoFactory;
import jhydra.core.scripting.scriptinfo.exceptions.ScriptInfoLoadException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Author: jantic
 * Date: 4/4/13
 */
public class ScriptInfoFactoryTests {
    /***Tests normally executing, existing scripts ****************************************/
    @Test
    public void getAllScriptInfos_proj1_java_size7() throws Exception{
        final ScriptInfoFactory scriptInfoFactory = new ScriptInfoFactory();
        final List<IScriptInfo> scriptInfos = scriptInfoFactory.getAllScriptInfosOfType(getConfig(1), ScriptType.JAVA);
        final Integer expected = 7;
        final Integer actual = scriptInfos.size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllScriptInfos_proj1_jython_size1() throws Exception{
        final ScriptInfoFactory scriptInfoFactory = new ScriptInfoFactory();
        final List<IScriptInfo> scriptInfos = scriptInfoFactory.getAllScriptInfosOfType(getConfig(1), ScriptType.JYTHON);
        final Integer expected = 1;
        final Integer actual = scriptInfos.size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllScriptInfos_proj2_java_size1() throws Exception{
        final ScriptInfoFactory scriptInfoFactory = new ScriptInfoFactory();
        final List<IScriptInfo> scriptInfos = scriptInfoFactory.getAllScriptInfosOfType(getConfig(2), ScriptType.JAVA);
        final Integer expected = 1;
        final Integer actual = scriptInfos.size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllScriptInfos_proj2_jython_size2() throws Exception{
        final ScriptInfoFactory scriptInfoFactory = new ScriptInfoFactory();
        final List<IScriptInfo> scriptInfos = scriptInfoFactory.getAllScriptInfosOfType(getConfig(2), ScriptType.JYTHON);
        final Integer expected = 2;
        final Integer actual = scriptInfos.size();
        Assert.assertEquals(expected, actual);
    }

    /***Tests cases of 0 existing scripts, in existing folders****************************************/

    @Test
    public void getAllScriptInfos_proj4_java_size0() throws Exception{
        final ScriptInfoFactory scriptInfoFactory = new ScriptInfoFactory();
        final List<IScriptInfo> scriptInfos = scriptInfoFactory.getAllScriptInfosOfType(getConfig(4), ScriptType.JAVA);
        final Integer expected = 0;
        final Integer actual = scriptInfos.size();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getAllScriptInfos_proj4_jython_size0() throws Exception{
        final ScriptInfoFactory scriptInfoFactory = new ScriptInfoFactory();
        final List<IScriptInfo> scriptInfos = scriptInfoFactory.getAllScriptInfosOfType(getConfig(4), ScriptType.JYTHON);
        final Integer expected = 0;
        final Integer actual = scriptInfos.size();
        Assert.assertEquals(expected, actual);
    }

    /***Tests case of non-existing folders****************************************/

    @Test(expected = ScriptInfoLoadException.class)
    public void getAllScriptInfos_proj3_java_scriptInfoLoadException() throws Exception{
        final ScriptInfoFactory scriptInfoFactory = new ScriptInfoFactory();
        scriptInfoFactory.getAllScriptInfosOfType(getConfig(3), ScriptType.JAVA);
    }

    /***PRIVATE METHODS*********************************************************************/
    private IRuntimeConfig getConfig(Integer projNum){
        final IRuntimeConfig config = mock(IRuntimeConfig.class);
        final String projectsDirectory = "./test projects/project " + projNum.toString();
        final List<URI> scriptDirectories = new ArrayList<>();
        scriptDirectories.add(getURI(projectsDirectory + "/scripts/"));
        scriptDirectories.add(getURI(projectsDirectory + "/alt scripts/"));
        when(config.getScriptDirectories()).thenReturn(scriptDirectories);
        when(config.getProjectDirectory()).thenReturn(getURI(projectsDirectory));
        return config;
    }

    private URI getURI(String relativePath){
        final File file = new File(relativePath);
        return file.toURI();
    }
}
