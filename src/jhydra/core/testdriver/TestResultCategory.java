/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.testdriver;

/**
 *
 * @author jantic
 */
public class TestResultCategory {
    public static final TestResultCategory PASS = new TestResultCategory("pass");
    public static final TestResultCategory ABNORMAL_EXIT = new TestResultCategory("abnormal exit");
    public static final TestResultCategory VALUE_FAILURE = new TestResultCategory("value fallure");
    
    private final String name;
    
    private TestResultCategory(String name){
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
