/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.java;

import java.util.List;
import java.util.Locale;
import javax.tools.Diagnostic;
import jhydra.core.FatalException;
import jhydra.core.RecoverableException;
import jhydra.core.config.IConfig;
import jhydra.core.logging.ILog;
import jhydra.core.scripting.CompileErrorException;
import jhydra.core.scripting.CompileErrorReport;
import jhydra.core.scripting.IBaseScript;
import jhydra.core.scripting.IScript;
import jhydra.core.scripting.IScriptCompiler;
import jhydra.core.scripting.IScriptFactory;
import jhydra.core.scripting.MasterScriptFactory;
import jhydra.core.scripting.ScriptType;
import jhydra.core.scripting.scriptinfo.IScriptInfo;
import jhydra.core.uinav.IMasterNavigator;
import jhydra.core.valuemap.IValueMap;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 *
 * @author jantic
 */
public class DynamicJavaCompilerTests {
    //Needed to verify sum set in script.
    private String sum = "";
    
    /***Tests on script with no compiler errors****************************************/
   
    @Test
    public void getCompiledScript_normalFile_getName_NormalScript() throws FatalException{
        final IScript script = getCompiledScript("NormalScript");
        final String expected = "jhydra.scripts.NormalScript";
        final String actual = script.getName();
        Assert.assertEquals(expected, actual);
    }
    
    
    @Test
    public void getCompiledScript_normalFile_execute_Success() throws FatalException, RecoverableException{
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
        
        final IScript script = getInitializedScript("NormalScript", valueMap);
        script.execute();
        final String expected = "11.0";
        final String actual = sum;
        Assert.assertEquals(expected, actual);
    }
    
    /***Tests on script with syntax errors ****************************************/
    
    @Test(expected = CompileErrorException.class)
    public void getCompiledScript_syntaxErrorsScript_CompileErrorException() throws FatalException{
        getCompiledScript("SyntaxErrorsScript");
    }
    
    @Test
    public void getCompiledScript_syntaxErrorsScript_firstError_correctErrorMessage() throws FatalException{
        final String expected = "cannot find symbol\n" +
            "  symbol:   class BigDecima\n" +
            "  location: class jhydra.scripts.SyntaxErrorsScript";
        final String actual = getResultingDiagnostic("SyntaxErrorsScript", 0).getMessage(Locale.ENGLISH);
        Assert.assertEquals(expected, actual);
    }

    
    @Test
    public void getCompiledScript_syntaxErrorsScript_firstError_correctLineNumber() throws FatalException{
        final long expected = 14;
        final long actual = getResultingDiagnostic("SyntaxErrorsScript", 0).getLineNumber();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getCompiledScript_syntaxErrorsScript_firstError_correctColumnNumber() throws FatalException{
        final long expected = 37;
        final long actual = getResultingDiagnostic("SyntaxErrorsScript", 0).getColumnNumber();
        Assert.assertEquals(expected, actual);
    } 
    
    @Test
    public void getCompiledScript_syntaxErrorsScript_secondError_correctErrorMessage() throws FatalException{
        final String expected = "no suitable method found for add(java.lang.String)\n" +
            "    method java.math.BigDecimal.add(java.math.BigDecimal,java.math.MathContext) is not applicable\n" +
            "      (actual and formal argument lists differ in length)\n" +
            "    method java.math.BigDecimal.add(java.math.BigDecimal) is not applicable\n" +
            "      (actual argument java.lang.String cannot be converted to java.math.BigDecimal by method invocation conversion)";
        final String actual = getResultingDiagnostic("SyntaxErrorsScript", 1).getMessage(Locale.ENGLISH);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getCompiledScript_syntaxErrorsScript_secondError_correctLineNumber() throws FatalException{
        final long expected = 15;
        final long actual = getResultingDiagnostic("SyntaxErrorsScript", 1).getLineNumber();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getCompiledScript_syntaxErrorsScript_secondError_correctColumnNumber() throws FatalException{
        final long expected = 36;
        final long actual = getResultingDiagnostic("SyntaxErrorsScript", 1).getColumnNumber();
        Assert.assertEquals(expected, actual);
    }
    
    
    @Test
    public void getCompiledScript_syntaxErrorsScript_thirdError_correctErrorMessage() throws FatalException{
        final String expected = "cannot find symbol\n" +
            "  symbol:   method seValue(java.lang.String,java.lang.String)\n" +
            "  location: class jhydra.scripts.SyntaxErrorsScript";
        final String actual = getResultingDiagnostic("SyntaxErrorsScript", 2).getMessage(Locale.ENGLISH);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getCompiledScript_syntaxErrorsScript_thirdError_correctLineNumber() throws FatalException{
        final long expected = 16;
        final long actual = getResultingDiagnostic("SyntaxErrorsScript", 2).getLineNumber();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getCompiledScript_syntaxErrorsScript_thirdError_correctColumnNumber() throws FatalException{
        final long expected = 9;
        final long actual = getResultingDiagnostic("SyntaxErrorsScript", 2).getColumnNumber();
        Assert.assertEquals(expected, actual);
    }
    
    private IScript getInitializedScript(String name, IValueMap valueMap) throws FatalException{
        final IBaseScript script = getCompiledScript(name);
        final IConfig config = mock(IConfig.class);
        script.setConfig(config);
        final ILog log = mock(ILog.class);
        script.setLog(log);
        final IMasterNavigator navigator = mock(IMasterNavigator.class);
        script.setNavigator(navigator);
        final IScriptFactory scriptFactory = new MasterScriptFactory();
        script.setScriptFactory(scriptFactory);
        script.setValueMap(valueMap);
        return script;
    }
    
    private IBaseScript getCompiledScript(String name) throws FatalException {
        final IScriptCompiler compiler = new DynamicJavaCompiler();
        final IScriptInfo scriptInfo = mock(IScriptInfo.class);
        when(scriptInfo.getClassName()).thenReturn("jhydra.scripts." + name);
        when(scriptInfo.getFilePath()).thenReturn("./test-scripts/" + name +".java");
        when(scriptInfo.getName()).thenReturn(name);
        when(scriptInfo.getType()).thenReturn(ScriptType.JAVA);
        return compiler.getCompiledScript(scriptInfo); 
    }
          
    private Diagnostic getResultingDiagnostic(String scriptName, Integer diagnosticNum) throws FatalException{
        try{
            getCompiledScript(scriptName);
        }
        catch(CompileErrorException e){
            final List<CompileErrorReport> reports = e.getAllReports();
            final CompileErrorReport report = reports.get(0);
            return report.getDiagnostics().get(diagnosticNum);
        }
        
        return null;
    }
}
