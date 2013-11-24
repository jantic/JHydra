/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.testcase.result;

/**
 *
 * @author jantic
 */
public class TestResultCategory {
    public static final TestResultCategory PASS = new TestResultCategory("pass");
    public static final TestResultCategory NON_FATAL_EXIT = new TestResultCategory("non-fatal exit");
    public static final TestResultCategory FATAL_EXIT = new TestResultCategory("fatal exit");
    public static final TestResultCategory VALUE_FAILURE = new TestResultCategory("value failure");
    
    private final String name;
    
    private TestResultCategory(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
