/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

import java.util.List;
import jhydra.core.FatalException;

/**
 *
 * @author jantic
 */
public class CompileErrorException extends FatalException{
    private final List<FileCompileErrorReport> reports;
    
    public CompileErrorException(String message, List<FileCompileErrorReport> reports){
        super(message);
        this.reports = reports;
    }
    
    public List<FileCompileErrorReport> getAllReports(){
        return this.reports;
    }
}
