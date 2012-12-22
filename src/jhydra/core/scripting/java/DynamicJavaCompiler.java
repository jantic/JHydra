package jhydra.core.scripting.java;

import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import javax.tools.Diagnostic;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import jhydra.core.scripting.CompileErrorException;
import jhydra.core.scripting.FileCompileErrorReport;
import jhydra.core.scripting.IBaseScript;
import jhydra.core.scripting.IScript;
import jhydra.core.scripting.IScriptCompiler;
import jhydra.core.scripting.ScriptInputLoadingException;
import jhydra.core.scripting.ScriptNotExistException;
import jhydra.core.scripting.ScriptOutputLoadingException;
import jhydra.core.scripting.ScriptType;
import jhydra.core.scripting.scriptinfo.IScriptInfo;
import org.apache.commons.io.IOUtils;


//Package access only
class DynamicJavaCompiler implements IScriptCompiler {
    //TODO:  Get this into config
    private final String classOutputFolder = "temp";
    
    @Override
    public IBaseScript getCompiledScript(IScriptInfo scriptInfo) 
            throws CompileErrorException, ScriptOutputLoadingException, ScriptNotExistException, ScriptInputLoadingException{
        
        final String filePath = scriptInfo.getFilePath();
        final String className = scriptInfo.getClassName();
               
        if(!fileExists(filePath)){
            throw new ScriptNotExistException(className);
        }
        
        final ScriptType scriptType = scriptInfo.getType();
        
        if(scriptType!=ScriptType.JAVA){
            final String message = "File attempted for java compilation is not a java file!  File path:  " + filePath;
            throw new ScriptInputLoadingException(filePath, className, message);
        }
            
        compile(filePath, className);
        return getScriptFromCompileOutput(className);      
    }
    
    private IBaseScript getScriptFromCompileOutput(String className) throws ScriptOutputLoadingException{
        try{
            final File file = new File(classOutputFolder);
            final URL url = file.toURI().toURL(); 
            final URL[] urls = new URL[]{url};
            final ClassLoader loader = new URLClassLoader(urls);
            final Class thisClass = loader.loadClass(className);
            return (IBaseScript)thisClass.newInstance();
        }
        catch(MalformedURLException | ClassNotFoundException | 
                InstantiationException | IllegalAccessException e){
            throw new ScriptOutputLoadingException(className, e);
        }
    }
    
    private void compile(String fileName, String className) throws CompileErrorException, ScriptInputLoadingException{
        try{
            final JavaFileObject file = getJavaFileObject(className, fileName);        
            final List<Diagnostic> diagnostics = compile(Arrays.asList(file));
            evaluateDiagnostics(fileName, diagnostics);
        }
        catch(CompileErrorException e){
            throw e;
        }
        catch(Exception e){
            throw new ScriptInputLoadingException(fileName, className, e);
        }
    }  
    
    private Boolean fileExists(String fileName){
        try{
            final File file = new File(fileName);
            return file.isFile();
        }
        catch(Exception e){
            return false;
        }
    }

    private void evaluateDiagnostics(String fileName, List<Diagnostic> diagnostics) throws CompileErrorException{
        for(Diagnostic diagnostic : diagnostics){
            if(diagnostic.getKind() == Diagnostic.Kind.ERROR){
                final FileCompileErrorReport report = new FileCompileErrorReport(fileName, diagnostics);
                final List<FileCompileErrorReport> reports = new ArrayList<>();
                reports.add(report);
                final String message = "Java compiler found error(s) in script.";
                throw new CompileErrorException(message, reports);
            }
        }
    }
      
    private List<Diagnostic> compile(Iterable<? extends JavaFileObject> files) throws CompileErrorException {
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        final JavaDiagnosticListener diagnosticListener = new JavaDiagnosticListener();
        final StandardJavaFileManager fileManager = 
                compiler.getStandardFileManager(diagnosticListener,Locale.ENGLISH,null);
        final Iterable options = Arrays.asList("-d", classOutputFolder);
        final JavaCompiler.CompilationTask task;
        task = compiler.getTask(null, fileManager, diagnosticListener, options, null, files);
        task.call();
        return diagnosticListener.getAllDiagnostics();
    }
    
      
    private JavaFileObject getJavaFileObject(String className, String fileName) throws Exception{
        final String sourceCode = loadSourceCode(fileName);       
        return new InMemoryJavaFileObject(className, sourceCode);
    }
    
    private String loadSourceCode(String fileName) throws Exception{
        try (FileInputStream inputStream = new FileInputStream(fileName)) {
            return IOUtils.toString(inputStream);
        }
    }
}