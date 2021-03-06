package jhydra.core.scripting.java;

import jhydra.core.scripting.CompileErrorReport;
import jhydra.core.scripting.IBaseScript;
import jhydra.core.scripting.IScriptCompiler;
import jhydra.core.scripting.ScriptType;
import jhydra.core.scripting.exceptions.*;
import jhydra.core.scripting.scriptinfo.IScriptInfo;
import org.apache.commons.io.IOUtils;

import javax.tools.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


//Package access only
class DynamicJavaCompiler implements IScriptCompiler {
    //TODO:  Get this into config
    private final String classOutputFolder = "temp";

    public DynamicJavaCompiler(){
        final File file = new File(classOutputFolder);
        establishDirectory(file);
    }
     
    @Override
    public IBaseScript getCompiledScript(IScriptInfo scriptInfo) throws ScriptFatalException, CompileErrorException{
        
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
        return getScriptFromCompileOutput(className, filePath);      
    }
    
    private IBaseScript getScriptFromCompileOutput(String className, String filePath) throws ScriptFatalException{
        try{
            final File file = new File(classOutputFolder);
            final URL url = file.toURI().toURL(); 
            final URL[] urls = new URL[]{url};
            final ClassLoader loader = new URLClassLoader(urls);
            final Class thisClass = loader.loadClass(className);
            return (IBaseScript)thisClass.newInstance();
        }
        catch(IllegalAccessException e){
            throw new NonPublicScriptClassException(filePath, e);
        }
        catch(ClassNotFoundException e){
            throw new ClassNotInScriptFileException(filePath, e);
        }
        catch(InstantiationException e){
            throw new ScriptInstantiationException(filePath, e);
        }
        catch(MalformedURLException e){
            throw new ScriptOutputLoadingException(className, e);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void establishDirectory(File file){
          if(!file.exists()){
              file.mkdirs();
          }
    }
    
    private void compile(String fileName, String className) throws ScriptFatalException, CompileErrorException{
        try{
            final JavaFileObject file = getJavaFileObject(className, fileName);        
            final List<Diagnostic<? extends JavaFileObject>> diagnostics = compile(Arrays.asList(file));
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

    private void evaluateDiagnostics(String fileName, List<Diagnostic<? extends JavaFileObject>> diagnostics) 
            throws CompileErrorException{
        final List<CompileErrorReport> reports = new ArrayList<>();
        if(diagnostics.size() > 0){
            final CompileErrorReport report = new CompileErrorReport(fileName, diagnostics);
            reports.add(report);   
        }
        
        for(Diagnostic diagnostic : diagnostics){
            if(diagnostic.getKind() == Diagnostic.Kind.ERROR){
                final String message = "Java compiler found error(s) in script named " + fileName;
                throw new CompileErrorException(message, reports);
            }
        }

    }
      
    private List<Diagnostic<? extends JavaFileObject>> compile(Iterable<JavaFileObject> files)
            throws IOException {
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        final DiagnosticCollector<JavaFileObject> diagnosticCollector = new DiagnosticCollector<>();
        
        try(final StandardJavaFileManager fileManager = 
                compiler.getStandardFileManager(diagnosticCollector,Locale.ENGLISH,null)){
            final List<String> options = new ArrayList<>();
            options.add("-d");
            options.add(classOutputFolder);         
            options.add("-verbose"); 
            compiler.getTask(null, fileManager, diagnosticCollector, options, null, files).call();
            return diagnosticCollector.getDiagnostics();
        }
    }
    
      
    private JavaFileObject getJavaFileObject(String className, String fileName) throws IOException{
        final String sourceCode = loadSourceCode(fileName);       
        return new InMemoryJavaFileObject(className, sourceCode);
    }
    
    private String loadSourceCode(String fileName) throws IOException{
        try (FileInputStream inputStream = new FileInputStream(fileName)) {
            return IOUtils.toString(inputStream);
        }
    }

}