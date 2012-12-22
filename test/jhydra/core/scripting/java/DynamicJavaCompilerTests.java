/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.java;

import java.util.List;
import java.util.Locale;
import javax.tools.Diagnostic;
import jhydra.core.FatalException;
import jhydra.core.scripting.CompileErrorException;
import jhydra.core.scripting.FileCompileErrorReport;
import jhydra.core.scripting.IScript;
import jhydra.core.scripting.IScriptCompiler;
import jhydra.core.scripting.ScriptInputLoadingException;
import jhydra.core.scripting.ScriptNotExistException;
import jhydra.core.scripting.ScriptOutputLoadingException;
import jhydra.core.scripting.ScriptType;
import jhydra.core.scripting.scriptinfo.IScriptInfo;
import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;

/**
 *
 * @author jantic
 */
public class DynamicJavaCompilerTests {
    /***Tests on correctly instantiated NameValue****************************************/
   
    @Test
    public void getCompiledScript_normalFile_Success() throws FatalException{
        final IScript script = getCompiledScript("NormalScript");
        final String expected = "jhydra.scripts.NormalScript";
        final String actual = script.getName();
        Assert.assertEquals(expected, actual);
    }
    
    private IScript getCompiledScript(String name) 
            throws CompileErrorException, ScriptOutputLoadingException, ScriptNotExistException, ScriptInputLoadingException {
        final IScriptCompiler compiler = new DynamicJavaCompiler();
        final IScriptInfo scriptInfo = mock(IScriptInfo.class);
        when(scriptInfo.getClassName()).thenReturn("jhydra.scripts." + name);
        when(scriptInfo.getFilePath()).thenReturn("./test-scripts/" + name +".java");
        when(scriptInfo.getName()).thenReturn(name);
        when(scriptInfo.getType()).thenReturn(ScriptType.JAVA);
        return compiler.getCompiledScript(scriptInfo); 
    }
}
