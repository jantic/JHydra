/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

import java.util.List;

/**
 *
 * @author jantic
 */
public class CompileErrorException extends Exception{
    private final List<FileCompileErrorReport> reports;
    
    public CompileErrorException(List<FileCompileErrorReport> reports){
        this.reports = reports;
    }
    
    public List<FileCompileErrorReport> getAllReports(){
        return this.reports;
    }
}
