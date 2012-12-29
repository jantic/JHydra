/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.java;

import java.util.List;
import java.util.Locale;
import javax.tools.Diagnostic;
import jhydra.core.config.IConfig;
import jhydra.core.exceptions.FatalException;
import jhydra.core.exceptions.RecoverableException;
import jhydra.core.logging.ILog;
import jhydra.core.scripting.CompileErrorReport;
import jhydra.core.scripting.IScript;
import jhydra.core.scripting.IScriptFactory;
import jhydra.core.scripting.exceptions.ClassNotInScriptFileException;
import jhydra.core.scripting.exceptions.CompileErrorException;
import jhydra.core.scripting.exceptions.NonPublicScriptClassException;
import jhydra.core.scripting.exceptions.ScriptInstantiationException;
import jhydra.core.scripting.exceptions.ScriptNotExistException;
import jhydra.core.uinav.IMasterNavigator;
import jhydra.core.valuemap.IValueMap;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author jantic
 */
public class JavaFileScriptFactoryTests { 
    //Needed to verify sum set in script.
    private String sum = "";
    
    /***Tests on script with no compiler errors****************************************/
   
    @Test
    public void getScript_normalFile_getName_NormalScript() throws FatalException{
        final IScript script = getScript("NormalScript");
        final String expected = "jhydra.scripts.NormalScript";
        final String actual = script.getName();
        Assert.assertEquals(expected, actual);
    }
    
    
    @Test
    public void getScript_normalFile_execute_Success() throws FatalException, RecoverableException{
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
        
        final IScript script = getScript("NormalScript", valueMap);
        script.execute();
        final String expected = "11.0";
        final String actual = sum;
        Assert.assertEquals(expected, actual);
    }

    
    /***Tests on script with syntax errors ****************************************/
    
    @Test(expected = CompileErrorException.class)
    public void getScript_syntaxErrorsScript_CompileErrorException() throws FatalException{
        getScript("SyntaxErrorsScript");
    }
    
    @Test
    public void getScript_syntaxErrorsScript_firstError_correctErrorMessage() throws FatalException{
        final String expected = "cannot find symbol\n" +
            "  symbol:   class BigDecima\n" +
            "  location: class jhydra.scripts.SyntaxErrorsScript";
        final String actual = getResultingDiagnostic("SyntaxErrorsScript", 0).getMessage(Locale.ENGLISH);
        Assert.assertEquals(expected, actual);
    }

    
    @Test
    public void getScript_syntaxErrorsScript_firstError_correctLineNumber() throws FatalException{
        final long expected = 14;
        final long actual = getResultingDiagnostic("SyntaxErrorsScript", 0).getLineNumber();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getScript_syntaxErrorsScript_firstError_correctColumnNumber() throws FatalException{
        final long expected = 37;
        final long actual = getResultingDiagnostic("SyntaxErrorsScript", 0).getColumnNumber();
        Assert.assertEquals(expected, actual);
    } 
    
    @Test
    public void getScript_syntaxErrorsScript_secondError_correctErrorMessage() throws FatalException{
        final String expected = "no suitable method found for add(java.lang.String)\n" +
            "    method java.math.BigDecimal.add(java.math.BigDecimal,java.math.MathContext) is not applicable\n" +
            "      (actual and formal argument lists differ in length)\n" +
            "    method java.math.BigDecimal.add(java.math.BigDecimal) is not applicable\n" +
            "      (actual argument java.lang.String cannot be converted to java.math.BigDecimal by method invocation conversion)";
        final String actual = getResultingDiagnostic("SyntaxErrorsScript", 1).getMessage(Locale.ENGLISH);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getScript_syntaxErrorsScript_secondError_correctLineNumber() throws FatalException{
        final long expected = 15;
        final long actual = getResultingDiagnostic("SyntaxErrorsScript", 1).getLineNumber();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getScript_syntaxErrorsScript_secondError_correctColumnNumber() throws FatalException{
        final long expected = 36;
        final long actual = getResultingDiagnostic("SyntaxErrorsScript", 1).getColumnNumber();
        Assert.assertEquals(expected, actual);
    }
    
    
    @Test
    public void getScript_syntaxErrorsScript_thirdError_correctErrorMessage() throws FatalException{
        final String expected = "cannot find symbol\n" +
            "  symbol:   method seValue(java.lang.String,java.lang.String)\n" +
            "  location: class jhydra.scripts.SyntaxErrorsScript";
        final String actual = getResultingDiagnostic("SyntaxErrorsScript", 2).getMessage(Locale.ENGLISH);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getScript_syntaxErrorsScript_thirdError_correctLineNumber() throws FatalException{
        final long expected = 16;
        final long actual = getResultingDiagnostic("SyntaxErrorsScript", 2).getLineNumber();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getScript_syntaxErrorsScript_thirdError_correctColumnNumber() throws FatalException{
        final long expected = 9;
        final long actual = getResultingDiagnostic("SyntaxErrorsScript", 2).getColumnNumber();
        Assert.assertEquals(expected, actual);
    }
    
    /***Tests on attempting to compile script without public qualifier****************************************/
    @Test(expected = NonPublicScriptClassException.class)
    public void getScript_nonPublicScript_NonPublicScriptClassException() throws FatalException{
        getScript("NonPublicScript");
    }
    
    /***Tests on attempting to compile script without class declaration****************************************/
    @Test(expected = CompileErrorException.class)
    public void getScript_noClassScript_CompileErrorException() throws FatalException{
        getScript("NoClassScript");
    }
    
    /***Tests on attempting to compile script with just function body****************************************/
    @Test(expected = CompileErrorException.class)
    public void getScript_justFunctionBodyScript_CompileErrorException() throws FatalException{
        getScript("JustFunctionBodyScript");
    }
    
    /***Tests on attempting to compile blank script file****************************************/
    @Test(expected = ClassNotInScriptFileException.class)
    public void getScript_blankScript_ClassNotInScriptFileException() throws FatalException{
        getScript("BlankScript"); 
    }
    
    /***Tests on attempting to compile/instantiate abstract class script file****************************************/
    @Test(expected = ScriptInstantiationException.class)
    public void getScript_abstractScript_ScriptInstantiationException() throws FatalException{
        getScript("AbstractScript");
    }
    
    
    /***Tests on attempting to compile/instantiate nonexistent script file****************************************/
    @Test(expected = ScriptNotExistException.class)
    public void getScript_nonexistentScript_ScriptNotExistException() throws FatalException{
        getScript("DoesNotExistScript");
    } 
    
    /***PRIVATE************************************************************************/    
    private IScript getScript(String name) throws FatalException{
        final IValueMap valueMap = mock(IValueMap.class);
        when(valueMap.getValue("num1")).thenReturn("5.0");
        when(valueMap.getValue("num2")).thenReturn("6.0");
        return getScript(name, valueMap);
    }
    
    private IScript getScript(String name, IValueMap valueMap) throws FatalException{
        final IConfig config = mock(IConfig.class);
        when(config.getScriptsPath()).thenReturn("./test-projects/project 1/scripts/");
        when(config.getProjectPath()).thenReturn("./test-projects/project 1/");
        when(config.getScriptMaxTries()).thenReturn(1);
        final ILog log = mock(ILog.class);
        final IScriptFactory masterScriptFactory = mock(IScriptFactory.class);
        final IScriptFactory scriptFactory = new JavaFileScriptFactory(config, log, masterScriptFactory);
        final IMasterNavigator navigator = mock(IMasterNavigator.class);
        return scriptFactory.getScript(name, valueMap, navigator);
    }

          
    private Diagnostic getResultingDiagnostic(String scriptName, Integer diagnosticNum) throws FatalException{
        try{
            getScript(scriptName);
        }
        catch(CompileErrorException e){
            final List<CompileErrorReport> reports = e.getAllReports();
            final CompileErrorReport report = reports.get(0);
            return report.getDiagnostics().get(diagnosticNum);
        }
        
        return null;
    }
}
