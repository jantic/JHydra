/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.java;

import jhydra.core.config.IProjectConfig;
import jhydra.core.logging.ILog;
import jhydra.core.scripting.CompileErrorReport;
import jhydra.core.scripting.IScript;
import jhydra.core.scripting.IScriptFactory;
import jhydra.core.scripting.exceptions.*;
import jhydra.core.uinav.IMasterNavigator;
import jhydra.core.valuemap.IValueMap;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.tools.Diagnostic;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.*;

/**
 *
 * @author jantic
 */
public class JavaFileScriptFactoryTest { 
    //Needed to verify sum set in NormalScript from project 1.
    private String sum = "";
    //Needed to verify message in Proj2Script from project 2
    private String message = "";
    
    /***Tests on script with no compiler errors****************************************/
   
    @Test
    public void getScript_proj1_normalScript_getName_NormalScript() throws Exception{
        final IScript script = getProj1Script("NormalScript");
        final String expected = "NormalScript";
        final String actual = script.getName();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getScript_proj2_proj2Script_getName_Proj2Script() throws Exception{
        final IScript script = getProj2Script("Proj2Script");
        final String expected = "Proj2Script";
        final String actual = script.getName();
        Assert.assertEquals(expected, actual);
    }
    
    
    @Test
    public void getScript_proj1_normalScript_execute_Success() throws Exception{
        final IScript script = getProj1Script("NormalScript");
        script.execute();
        final String expected = "11.0";
        final String actual = sum;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getScript_proj2_proj2Script_execute_Success() throws Exception{
        final IScript script = getProj2Script("Proj2Script");
        script.execute();
        final String expected = "hello";
        final String actual = message;
        Assert.assertEquals(expected, actual);
    }
    
    /***Tests on script with syntax errors ****************************************/
    
    @Test(expected = CompileErrorException.class)
    public void getScript_proj1_syntaxErrorsScript_CompileErrorException() throws Exception{
        getProj1Script("SyntaxErrorsScript");
    }
    
    @Test
    public void getScript_proj1_syntaxErrorsScript_firstError_correctErrorMessage() throws  Exception{
        final String expected = "cannot find symbol\n" +
            "  symbol:   class BigDecima\n" +
            "  location: class jhydra.scripts.SyntaxErrorsScript";
        final String actual = getResultingDiagnosticProj1("SyntaxErrorsScript", 0).getMessage(Locale.ENGLISH);
        Assert.assertEquals(expected, actual);
    }

    
    @Test
    public void getScript_proj1_syntaxErrorsScript_firstError_correctLineNumber() throws  Exception{
        final long expected = 14;
        final long actual = getResultingDiagnosticProj1("SyntaxErrorsScript", 0).getLineNumber();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getScript_proj1_syntaxErrorsScript_firstError_correctColumnNumber() throws  Exception{
        final long expected = 37;
        final long actual = getResultingDiagnosticProj1("SyntaxErrorsScript", 0).getColumnNumber();
        Assert.assertEquals(expected, actual);
    } 
    
    @Test
    public void getScript_proj1_syntaxErrorsScript_secondError_correctErrorMessage() throws  Exception{
        final String expected = "no suitable method found for add(java.lang.String)\n" +
            "    method java.math.BigDecimal.add(java.math.BigDecimal,java.math.MathContext) is not applicable\n" +
            "      (actual and formal argument lists differ in length)\n" +
            "    method java.math.BigDecimal.add(java.math.BigDecimal) is not applicable\n" +
            "      (actual argument java.lang.String cannot be converted to java.math.BigDecimal by method invocation conversion)";
        final String actual = getResultingDiagnosticProj1("SyntaxErrorsScript", 1).getMessage(Locale.ENGLISH);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getScript_proj1_syntaxErrorsScript_secondError_correctLineNumber() throws Exception{
        final long expected = 15;
        final long actual = getResultingDiagnosticProj1("SyntaxErrorsScript", 1).getLineNumber();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getScript_proj1_syntaxErrorsScript_secondError_correctColumnNumber() throws Exception{
        final long expected = 36;
        final long actual = getResultingDiagnosticProj1("SyntaxErrorsScript", 1).getColumnNumber();
        Assert.assertEquals(expected, actual);
    }
    
    
    @Test
    public void getScript_proj1_syntaxErrorsScript_thirdError_correctErrorMessage() throws Exception{
        final String expected = "cannot find symbol\n" +
            "  symbol:   method seValue(java.lang.String,java.lang.String)\n" +
            "  location: class jhydra.scripts.SyntaxErrorsScript";
        final String actual = getResultingDiagnosticProj1("SyntaxErrorsScript", 2).getMessage(Locale.ENGLISH);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getScript_proj1_syntaxErrorsScript_thirdError_correctLineNumber() throws Exception{
        final long expected = 16;
        final long actual = getResultingDiagnosticProj1("SyntaxErrorsScript", 2).getLineNumber();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getScript_proj1_syntaxErrorsScript_thirdError_correctColumnNumber() throws Exception{
        final long expected = 9;
        final long actual = getResultingDiagnosticProj1("SyntaxErrorsScript", 2).getColumnNumber();
        Assert.assertEquals(expected, actual);
    }
    
    /***Tests on attempting to compile script without public qualifier****************************************/
    @Test(expected = NonPublicScriptClassException.class)
    public void getScript_proj1_nonPublicScript_NonPublicScriptClassException() throws Exception{
        getProj1Script("NonPublicScript");
    }
    
    /***Tests on attempting to compile script without class declaration****************************************/
    @Test(expected = CompileErrorException.class)
    public void getScript_proj1_noClassScript_CompileErrorException() throws Exception{
        getProj1Script("NoClassScript");
    }
    
    /***Tests on attempting to compile script with just function body****************************************/
    @Test(expected = CompileErrorException.class)
    public void getScript_proj1_justFunctionBodyScript_CompileErrorException() throws Exception{
        getProj1Script("JustFunctionBodyScript");
    }
    
    /***Tests on attempting to compile blank script file****************************************/
    @Test(expected = ClassNotInScriptFileException.class)
    public void getScript_proj1_blankScript_ClassNotInScriptFileException() throws Exception{
        getProj1Script("BlankScript"); 
    }
    
    /***Tests on attempting to compile/instantiate abstract class script file****************************************/
    @Test(expected = ScriptInstantiationException.class)
    public void getScript_proj1_abstractScript_ScriptInstantiationException() throws Exception{
        getProj1Script("AbstractScript");
    }
    
    
    /***Tests on attempting to compile/instantiate nonexistent script file****************************************/
    @Test(expected = ScriptNotExistException.class)
    public void getScript_proj1_nonexistentScript_ScriptNotExistException() throws Exception{
        getProj1Script("DoesNotExistScript");
    } 
    
    /***Tests on attempting to compile/instantiate script that's not in this project, yet is present in the other****/
    @Test(expected = ScriptNotExistException.class)
    public void getScript_proj2_normalScript_ScriptNotExistException() throws Exception{
        getProj2Script("NormalScript");
    }
    
    /***PRIVATE************************************************************************/    
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
        return getScript(name, valueMap, 1);
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
        
        return getScript(name, valueMap, 2);
    }
    
    private IScript getScript(String name, IValueMap valueMap, Integer projNum) throws Exception{
        final IProjectConfig config = mock(IProjectConfig.class);
        final String projectsDirectory = "./test projects/project " + projNum.toString();
        final List<URI> scriptDirectories = new ArrayList<>();
        scriptDirectories.add(getURI(projectsDirectory + "/scripts/"));
        scriptDirectories.add(getURI(projectsDirectory + "/alt scripts/"));
        when(config.getScriptDirectories()).thenReturn(scriptDirectories);
        when(config.getProjectDirectory()).thenReturn(getURI(projectsDirectory));
        when(config.getScriptMaxNumTries()).thenReturn(1);
        final ILog log = mock(ILog.class);
        final IScriptFactory masterScriptFactory = mock(IScriptFactory.class);
        final IScriptFactory scriptFactory = new JavaFileScriptFactory(config, log, masterScriptFactory);
        final IMasterNavigator navigator = mock(IMasterNavigator.class);
        return scriptFactory.getScript(name, valueMap, navigator);
    }

    private URI getURI(String relativePath){
        final File file = new File(relativePath);
        return file.toURI();
    }
          
    private Diagnostic getResultingDiagnosticProj1(String scriptName, Integer diagnosticNum) throws Exception{
        try{
            getProj1Script(scriptName);
        }
        catch(CompileErrorException e){
            final List<CompileErrorReport> reports = e.getAllReports();
            final CompileErrorReport report = reports.get(0);
            return report.getDiagnostics().get(diagnosticNum);
        }
        
        return null;
    }
}
