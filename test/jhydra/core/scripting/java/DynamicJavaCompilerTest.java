/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.java;

import java.util.List;
import java.util.Locale;
import javax.tools.Diagnostic;
import jhydra.core.config.IProjectConfig;
import jhydra.core.exceptions.FatalException;
import jhydra.core.exceptions.RecoverableException;
import jhydra.core.logging.ILog;
import jhydra.core.scripting.CompileErrorReport;
import jhydra.core.scripting.IBaseScript;
import jhydra.core.scripting.IScript;
import jhydra.core.scripting.IScriptCompiler;
import jhydra.core.scripting.IScriptFactory;
import jhydra.core.scripting.MasterScriptFactory;
import jhydra.core.scripting.ScriptType;
import jhydra.core.scripting.exceptions.ClassNotInScriptFileException;
import jhydra.core.scripting.exceptions.CompileErrorException;
import jhydra.core.scripting.exceptions.NonPublicScriptClassException;
import jhydra.core.scripting.exceptions.ScriptInputLoadingException;
import jhydra.core.scripting.exceptions.ScriptInstantiationException;
import jhydra.core.scripting.exceptions.ScriptNotExistException;
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
public class DynamicJavaCompilerTest {
    private final String GOODSCRIPTS = "Good Scripts/";
    private final String BADNONCOMPILING = "Bad Scripts/Non Compiling/";
    private final String BADCOMPILING = "Bad Scripts/Compiling/";
    //Needed to verify sum set in script.
    private String sum = "";
    
    /***Tests on script with no compiler errors****************************************/
   
    @Test
    public void getCompiledScript_normalFile_getName_NormalScript() throws FatalException{
        final IScript script = getCompiledScript("NormalScript", GOODSCRIPTS, ScriptType.JAVA);
        final String expected = "NormalScript";
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
        
        final IScript script = getInitializedScriptJava("NormalScript", GOODSCRIPTS, valueMap);
        script.execute();
        final String expected = "11.0";
        final String actual = sum;
        Assert.assertEquals(expected, actual);
    }
    
    /***Tests on script with syntax errors ****************************************/
    
    @Test(expected = CompileErrorException.class)
    public void getCompiledScript_syntaxErrorsScript_CompileErrorException() throws FatalException{
        getCompiledScriptJava("SyntaxErrorsScript", BADNONCOMPILING);
    }
    
    @Test
    public void getCompiledScript_syntaxErrorsScript_firstError_correctErrorMessage() throws FatalException{
        final String expected = "cannot find symbol\n" +
            "  symbol:   class BigDecima\n" +
            "  location: class jhydra.scripts.SyntaxErrorsScript";
        final String actual = getResultingDiagnosticJava("SyntaxErrorsScript",BADNONCOMPILING, 0).getMessage(Locale.ENGLISH);
        Assert.assertEquals(expected, actual);
    }

    
    @Test
    public void getCompiledScript_syntaxErrorsScript_firstError_correctLineNumber() throws FatalException{
        final long expected = 14;
        final long actual = getResultingDiagnosticJava("SyntaxErrorsScript",BADNONCOMPILING, 0).getLineNumber();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getCompiledScript_syntaxErrorsScript_firstError_correctColumnNumber() throws FatalException{
        final long expected = 37;
        final long actual = getResultingDiagnosticJava("SyntaxErrorsScript",BADNONCOMPILING, 0).getColumnNumber();
        Assert.assertEquals(expected, actual);
    } 
    
    @Test
    public void getCompiledScript_syntaxErrorsScript_secondError_correctErrorMessage() throws FatalException{
        final String expected = "no suitable method found for add(java.lang.String)\n" +
            "    method java.math.BigDecimal.add(java.math.BigDecimal,java.math.MathContext) is not applicable\n" +
            "      (actual and formal argument lists differ in length)\n" +
            "    method java.math.BigDecimal.add(java.math.BigDecimal) is not applicable\n" +
            "      (actual argument java.lang.String cannot be converted to java.math.BigDecimal by method invocation conversion)";
        final String actual = getResultingDiagnosticJava("SyntaxErrorsScript",BADNONCOMPILING, 1).getMessage(Locale.ENGLISH);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getCompiledScript_syntaxErrorsScript_secondError_correctLineNumber() throws FatalException{
        final long expected = 15;
        final long actual = getResultingDiagnosticJava("SyntaxErrorsScript",BADNONCOMPILING, 1).getLineNumber();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getCompiledScript_syntaxErrorsScript_secondError_correctColumnNumber() throws FatalException{
        final long expected = 36;
        final long actual = getResultingDiagnosticJava("SyntaxErrorsScript",BADNONCOMPILING, 1).getColumnNumber();
        Assert.assertEquals(expected, actual);
    }
    
    
    @Test
    public void getCompiledScript_syntaxErrorsScript_thirdError_correctErrorMessage() throws FatalException{
        final String expected = "cannot find symbol\n" +
            "  symbol:   method seValue(java.lang.String,java.lang.String)\n" +
            "  location: class jhydra.scripts.SyntaxErrorsScript";
        final String actual = getResultingDiagnosticJava("SyntaxErrorsScript",BADNONCOMPILING, 2).getMessage(Locale.ENGLISH);
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getCompiledScript_syntaxErrorsScript_thirdError_correctLineNumber() throws FatalException{
        final long expected = 16;
        final long actual = getResultingDiagnosticJava("SyntaxErrorsScript",BADNONCOMPILING, 2).getLineNumber();
        Assert.assertEquals(expected, actual);
    }
    
    @Test
    public void getCompiledScript_syntaxErrorsScript_thirdError_correctColumnNumber() throws FatalException{
        final long expected = 9;
        final long actual = getResultingDiagnosticJava("SyntaxErrorsScript",BADNONCOMPILING, 2).getColumnNumber();
        Assert.assertEquals(expected, actual);
    }
    
    /***Tests on attempting to compile script without public qualifier****************************************/
    @Test(expected = NonPublicScriptClassException.class)
    public void getCompiledScript_nonPublicScript_NonPublicScriptClassException() throws FatalException{
        getCompiledScriptJava("NonPublicScript", BADCOMPILING);
    }
    
    /***Tests on attempting to compile script without class declaration****************************************/
    @Test(expected = CompileErrorException.class)
    public void getCompiledScript_noClassScript_CompileErrorException() throws FatalException{
        getCompiledScriptJava("NoClassScript", BADNONCOMPILING);
    }
    
    /***Tests on attempting to compile script with just function body****************************************/
    @Test(expected = CompileErrorException.class)
    public void getCompiledScript_justFunctionBodyScript_CompileErrorException() throws FatalException{
        getCompiledScriptJava("JustFunctionBodyScript", BADNONCOMPILING);
    }
    
    /***Tests on attempting to compile blank script file****************************************/
    @Test(expected = ClassNotInScriptFileException.class)
    public void getCompiledScript_blankScript_ClassNotInScriptFileException() throws FatalException{
        getCompiledScriptJava("BlankScript", BADCOMPILING); 
    }
    
    /***Tests on attempting to compile/instantiate abstract class script file****************************************/
    @Test(expected = ScriptInstantiationException.class)
    public void getCompiledScript_abstractScript_ScriptInstantiationException() throws FatalException{
        getCompiledScriptJava("AbstractScript", BADCOMPILING);
    }
    
    
    /***Tests on attempting to compile/instantiate nonexistent script file****************************************/
    @Test(expected = ScriptNotExistException.class)
    public void getCompiledScript_nonexistentScript_ScriptNotExistException() throws FatalException{
        getCompiledScriptJava("DoesNotExistScript", BADNONCOMPILING);
    } 
    
    /***Tests on attempting to compile/instantiate non java script file****************************************/
    @Test(expected = ScriptInputLoadingException.class)
    public void getCompiledScript_nonJava_ScriptInputLoadingException() throws FatalException{
        getNonJavaCompiledScript("NonJava", "./", ScriptType.JYTHON);
    }
    
    /***PRIVATE************************************************************************/
    private IScript getInitializedScriptJava(String name, String path, IValueMap valueMap) throws FatalException{
        final IBaseScript script = getCompiledScriptJava(name, path);
        final IProjectConfig config = mock(IProjectConfig.class);
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
    
    private IBaseScript getNonJavaCompiledScript(String name, String path, ScriptType scriptType) throws FatalException{
        final IScriptCompiler compiler = new DynamicJavaCompiler();
        final IScriptInfo scriptInfo = mock(IScriptInfo.class);
        when(scriptInfo.getClassName()).thenReturn("jhydra.scripts." + name);
        when(scriptInfo.getFilePath()).thenReturn("./test-projects/project 1/alt scripts/" + path + name +"." + scriptType.getExtension());
        when(scriptInfo.getName()).thenReturn(name);
        when(scriptInfo.getType()).thenReturn(scriptType);
        return compiler.getCompiledScript(scriptInfo); 
    }
    
    private IBaseScript getCompiledScript(String name, String path, ScriptType scriptType) throws FatalException{
        final IScriptCompiler compiler = new DynamicJavaCompiler();
        final IScriptInfo scriptInfo = mock(IScriptInfo.class);
        when(scriptInfo.getClassName()).thenReturn("jhydra.scripts." + name);
        when(scriptInfo.getFilePath()).thenReturn("./test-projects/project 1/scripts/" + path + name +"." + scriptType.getExtension());
        when(scriptInfo.getName()).thenReturn(name);
        when(scriptInfo.getType()).thenReturn(scriptType);
        return compiler.getCompiledScript(scriptInfo); 
    }
    
    private IBaseScript getCompiledScriptJava(String name, String path) throws FatalException{
        return getCompiledScript(name, path, ScriptType.JAVA); 
    }
          
    private Diagnostic getResultingDiagnosticJava(String scriptName, String path, Integer diagnosticNum) throws FatalException{
        try{
            getCompiledScriptJava(scriptName, path);
        }
        catch(CompileErrorException e){
            final List<CompileErrorReport> reports = e.getAllReports();
            final CompileErrorReport report = reports.get(0);
            return report.getDiagnostics().get(diagnosticNum);
        }
        
        return null;
    }
}
