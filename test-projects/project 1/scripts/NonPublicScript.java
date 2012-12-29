package jhydra.scripts;

import java.math.BigDecimal;
import jhydra.core.exceptions.FatalException;
import jhydra.core.scripting.IScript;
import jhydra.core.scripting.java.JavaBaseScript;


class NonPublicScript extends JavaBaseScript implements IScript{

    @Override
    public void execute() throws FatalException {
        final BigDecimal num1 = new BigDecimal(getValue("num1"));
        final BigDecimal num2 = new BigDecimal(getValue("num2"));
        final BigDecimal sum = num1.add(num2);
        setValue("sum", sum.toString());
    }   
}
