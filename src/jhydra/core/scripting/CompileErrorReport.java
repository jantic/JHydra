/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

import java.util.List;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 *
 * @author jantic
 */
public class CompileErrorReport {
    private final List<Diagnostic<? extends JavaFileObject>> diagnostics;
    private final String fileName;
    
    public CompileErrorReport(String fileName, List<Diagnostic<? extends JavaFileObject>> diagnostics){
        this.fileName = fileName;
        this.diagnostics = diagnostics;
    }
    
    public List<Diagnostic<? extends JavaFileObject>> getDiagnostics(){
        return this.diagnostics;
    }
    
    public String getFileName(){
        return this.fileName;
    }
}
