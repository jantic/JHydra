package jhydra.core.testcase.excel;

import jhydra.core.config.IRuntimeConfig;
import jhydra.core.logging.ILog;
import jhydra.core.testcase.ITestCase;
import jhydra.core.testcase.ITestCaseCollection;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: jantic
 * Date: 11/24/13
 */
public class ExcelTestCaseCollection implements ITestCaseCollection {
    private final String name;
    private final String description;
    private final String fileName;
    private final List<ITestCase> orderedTestCases;
    private final Boolean testsAreIndependent;
    private final DateTime testLoadTimeStamp;
    private final IRuntimeConfig config;
    private final ILog log;

    public ExcelTestCaseCollection(String fileName, IRuntimeConfig config, ILog log) {
        //TODO:  Properly implement the following:
        this.name = "";
        this.description = "";
        this.fileName = fileName;
        this.config = config;
        this.log = log;
        //TODO:  This should be specified in the spreadsheet, method tbd
        this.testsAreIndependent = true;
        this.orderedTestCases = loadTestCases(fileName);
        this.testLoadTimeStamp =  DateTime.now();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getFileName() {
        return this.fileName;
    }

    @Override
    public List<ITestCase> getOrderedTestCases() {
        return this.orderedTestCases;
    }

    @Override
    public Boolean testsAreIndependent() {
        return this.testsAreIndependent;
    }

    @Override
    public DateTime getLoadTimeStamp() {
        return this.testLoadTimeStamp;
    }

    private List<ITestCase> loadTestCases(String fileName){
        //TODO:  Properly implement this
        final List<ITestCase> testCases = new ArrayList<ITestCase>();
        return testCases;
    }
}
