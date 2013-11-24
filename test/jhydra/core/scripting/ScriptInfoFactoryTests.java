package jhydra.core.scripting;

import jhydra.core.config.IRuntimeConfig;
import jhydra.core.scripting.scriptinfo.IScriptInfo;
import jhydra.core.scripting.scriptinfo.ScriptInfoFactory;
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
