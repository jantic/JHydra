package jhydra.scripts;

import jhydra.core.exceptions.FatalException;
import jhydra.core.scripting.IScript;
import jhydra.core.scripting.java.JavaBaseScript;


public class Proj2Script extends JavaBaseScript implements IScript{

    @Override
    public void execute() throws FatalException {
        setValue("message", "hello");
    }   
}
