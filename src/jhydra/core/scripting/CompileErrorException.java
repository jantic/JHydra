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
    private final List<CompileErrorReport> reports;
    
    public CompileErrorException(String message, List<CompileErrorReport> reports){
        super(message);
        this.reports = reports;
    }
    
    public List<CompileErrorReport> getAllReports(){
        return this.reports;
    }
}
