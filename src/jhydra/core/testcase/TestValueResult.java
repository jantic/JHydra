package jhydra.core.testcase;

/**
 * Author: jantic
 * Date: 3/3/13
 */
public class TestValueResult {
    private final String expectedValue;
    private final String actualValue;
    private final TestValueResultType testValueResultType;
    private final String variableName;
    private final String comments;

    static public TestValueResult getInstance(String expectedValue, String actualValue, TestValueResultType testValueResultType, String variableName){
        return getInstance(expectedValue, actualValue, testValueResultType, variableName, "");
    }

    static public TestValueResult getInstance(String expectedValue, String actualValue, TestValueResultType testValueResultType, String variableName, String comments){
         return new TestValueResult(expectedValue, actualValue, testValueResultType, variableName, comments);
    }

    private TestValueResult(String expectedValue, String actualValue, TestValueResultType testValueResultType, String variableName, String comments){
        this.expectedValue = expectedValue;
        this.actualValue = actualValue;
        this.testValueResultType = testValueResultType;
        this.variableName = variableName;
        this.comments = comments;
    }

    public String getExpectedValue(){
        return this.expectedValue;
    }

    public String getActualValue(){
        return this.actualValue;
    }

    public TestValueResultType getTestValueResultType(){
        return this.testValueResultType;
    }

    public String getVariableName(){
        return this.variableName;
    }

    public String getComments(){
        return this.comments;
    }
}
