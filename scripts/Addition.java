/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.scripts;
import java.math.BigDecimal;
import jhydra.core.scripting.BaseScript;
import jhydra.core.scripting.IScript;
/**
 *
 * @author jantic
 */
public class Addition extends BaseScript implements IScript{

    @Override
    public void execute() throws Exception {
        final BigDecimal num1 = new BigDecimal(valueMap.getValue("num1"));
        final BigDecimal num2 = new BigDecimal(valueMap.getValue("num2"));
        final BigDecimal sum = num1.add(num2);
        valueMap.setValue("result", sum.toString());
    }   
}
