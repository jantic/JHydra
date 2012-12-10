/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.java;

import java.util.ArrayList;
import java.util.List;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 *
 * @author jantic
 */
public class JavaDiagnosticListener implements javax.tools.DiagnosticListener<JavaFileObject> {
    final List<Diagnostic> reportedDiagnostics = new ArrayList<Diagnostic>();

    @Override
    public void report(Diagnostic<? extends JavaFileObject> diagnostic) {
        reportedDiagnostics.add(diagnostic);
    }
    
    public List<Diagnostic> getAllDiagnostics(){
        return this.reportedDiagnostics;
    }
}
