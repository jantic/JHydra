package jhydra.core.testcase;

import jhydra.core.properties.INameValue;
import jhydra.core.testcase.result.TestValueResult;

import java.util.List;

/**
 * Author: jantic
 * Date: 3/3/13
 */
public interface ITestDataAnalyzer {
    List<INameValue> getAllTestInputs();
    INameValue getTestInput(String name);
    List<INameValue> getAllExpectedValues();
    INameValue getExpectedValue(String name);
    List<TestValueResult> getValueResults(List<INameValue> actualValues);
}
