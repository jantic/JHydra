package jhydra.core.scripting.java;

import java.io.File;
import java.io.FileInputStream;
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
import jhydra.core.scripting.IDynamicCompiler;
import org.apache.commons.io.IOUtils;


//Package access only
class DynamicJavaCompiler implements IDynamicCompiler {
    private final String classOutputFolder = "temp";
    
    @Override
    public IBaseScript getScript(String fileName, String className) throws Exception{   
        compile(fileName, className);
        final File file = new File(classOutputFolder);
        final URL url = file.toURI().toURL(); 
        final URL[] urls = new URL[]{url};
        final ClassLoader loader = new URLClassLoader(urls);
        final Class thisClass = loader.loadClass(className);
        return (IBaseScript)thisClass.newInstance();
    }
    
    @Override
    public void compile(String fileName, String className) throws Exception{
        final JavaFileObject file = getJavaFileObject(className, fileName);        
        final List<Diagnostic> diagnostics = compile(Arrays.asList(file));
        evaluateDiagnostics(fileName, diagnostics);
    }  

    private void evaluateDiagnostics(String fileName, List<Diagnostic> diagnostics) throws CompileErrorException{
        for(Diagnostic diagnostic : diagnostics){
            if(diagnostic.getKind() == Diagnostic.Kind.ERROR){
                final FileCompileErrorReport report = new FileCompileErrorReport(fileName, diagnostics);
                final List<FileCompileErrorReport> reports = new ArrayList<>();
                reports.add(report);
                throw new CompileErrorException(reports);
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