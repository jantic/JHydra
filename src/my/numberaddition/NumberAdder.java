/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package my.numberaddition;

import jhydra.core.scripting.IBaseScript;
import jhydra.core.scripting.IValueMap;
import jhydra.core.scripting.ValueMap;
import jhydra.core.scripting.java.JavaFileScriptFactory;


/**
 *
 * @author jantic
 */
public class NumberAdder {
    public String add(String text1, String text2) throws Exception{
        final IValueMap valueMap = new ValueMap();
        valueMap.setValue("num1", text1);
        valueMap.setValue("num2", text2);
        final JavaFileScriptFactory scriptFactory = new JavaFileScriptFactory();
        final IBaseScript script = scriptFactory.getScript("Addition");
        script.setValueMap(valueMap);
        script.execute();
        final String result = valueMap.getValue("result");
        return result;
    }
}
