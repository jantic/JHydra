package jhydra.core.testcase;

import jhydra.core.exceptions.RecoverableException;
import jhydra.core.lexicon.ILexicon;
import jhydra.core.lexicon.exceptions.NameNotInLexiconException;
import jhydra.core.logging.ILog;
import jhydra.core.properties.INameValue;
import jhydra.core.properties.NameValue;
import jhydra.core.properties.exceptions.NameNotValidException;
import jhydra.core.scripting.IScript;
import jhydra.core.scripting.IScriptFactory;
import jhydra.core.scripting.exceptions.CompileErrorException;
import jhydra.core.scripting.exceptions.ScriptFatalException;
import jhydra.core.testcase.result.FatalExitTestCaseResult;
import jhydra.core.testcase.result.ITestCaseResult;
import jhydra.core.testcase.result.NonFatalExitTestCaseResult;
import jhydra.core.testcase.result.NormalTestCaseResult;
import jhydra.core.uinav.IMasterNavigator;
import jhydra.core.valuemap.IValueMap;
import jhydra.core.valuemap.ValueMap;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: jantic
 * Date: 3/3/13
 */
public class TestCase implements ITestCase{
    private final ITestInfo testInfo;
    private final IScriptFactory scriptFactory;
    private final String entryScriptName;
    private final ILexicon lexicon;
    private final IMasterNavigator masterNavigator;
    private final ITestDataAnalyzer testDataAnalyzer;
    private final ILog log;


    public TestCase(ITestInfo testInfo, IScriptFactory scriptFactory,
                    String entryScriptName, ILexicon lexicon, IMasterNavigator masterNavigator,
                    ITestDataAnalyzer testDataAnalyzer, ILog log){
        this.testInfo = testInfo;
        this.scriptFactory = scriptFactory;
        this.entryScriptName = entryScriptName;
        this.lexicon = lexicon;
        this.masterNavigator = masterNavigator;
        this.testDataAnalyzer = testDataAnalyzer;
        this.log = log;
    }


    //Summary:  We want the test cases to be completely contained and "sand boxed" so that error conditions from the
    //scripts contained within can't escape these confines.  Hence the error handling logic below, and the use of
    //the ITestCaseResult type.
    @Override
    public ITestCaseResult execute(){
        final DateTime runStartTime = DateTime.now();

        try{
            final IValueMap valueMap = this.getInitializedValueMap();
            final IScript entryScript =  getEntryScript(valueMap);
            entryScript.execute();
            final List<INameValue> actualValues = extractActualValues(valueMap);
            final DateTime runCompleteTime = DateTime.now();
            return new NormalTestCaseResult(this.testDataAnalyzer,actualValues, runStartTime, runCompleteTime);
        }
        catch(RecoverableException e){
            log.error("Test case " + this.getName() + ", number " + this.getTestNumber().toString() + " ran into a" +
                    " potentially recoverable error: " + e.getMessage());
            final DateTime runCompleted = DateTime.now();
            return new NonFatalExitTestCaseResult(runStartTime, runCompleted, generateErrorList(e.getMessage()));
        }
        catch(Throwable e){
            log.error("Test case " + this.getName() + ", number " + this.getTestNumber().toString() + " ran into a" +
                    " fatal error, and therefore will be aborted: " + e.getMessage());
            final DateTime runCompleted = DateTime.now();
            return new FatalExitTestCaseResult(runStartTime, runCompleted, generateErrorList(e.getMessage()));
        }
    }

    private List<String> generateErrorList(String errorMessage){
        final List<String> errorMessages = new ArrayList<>();
        errorMessages.add(errorMessage);
        return errorMessages;
    }

    @Override
    public String getName() {
        return testInfo.getName();
    }

    @Override
    public String getDescription() {
        return testInfo.getDescription();
    }

    @Override
    public Integer getTestNumber() {
        return testInfo.getTestNumber();
    }

    private List<INameValue> extractActualValues(IValueMap valueMap) throws NameNotInLexiconException, NameNotValidException {
        final List<INameValue> expectedValues = testDataAnalyzer.getAllExpectedValues();
        final List<INameValue> actualValues = new ArrayList<>();

        for(INameValue expectedPair : expectedValues){
            final String actualValue = valueMap.getValue(expectedPair.getName());
            final INameValue actualPair = NameValue.getInstance(expectedPair.getName(), actualValue);
            actualValues.add(actualPair);
        }

        return actualValues;
    }

    private IValueMap getInitializedValueMap() throws NameNotInLexiconException, NameNotValidException {
        final IValueMap valueMap = new ValueMap(this.lexicon);
        valueMap.updateValues(testDataAnalyzer.getAllTestInputs());
        return valueMap;
    }

    private IScript getEntryScript(IValueMap valueMap) throws ScriptFatalException, CompileErrorException {
        return scriptFactory.getScript(this.entryScriptName, valueMap, this.masterNavigator);
    }
}
