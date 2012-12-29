/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting.exceptions;

import java.util.List;
import jhydra.core.exceptions.FatalException;
import jhydra.core.scripting.CompileErrorReport;

/**
 *
 * @author jantic
 * 
 * Extending from FatalException instead of ScriptFatalException, to make sure
 * CompileErrorExceptions are treated separately.
 */
public class CompileErrorException extends FatalException{
    private final List<CompileErrorReport> reports;
    
    public CompileErrorException(String message, List<CompileErrorReport> reports){
        super(message);
        this.reports = reports;
    }
    
    public List<CompileErrorReport> getAllReports(){
        return this.reports;
    }
}
