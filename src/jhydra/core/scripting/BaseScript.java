/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

/**
 *
 * @author jantic
 */
public abstract class BaseScript implements IScript, IBaseScript {
    protected IValueMap valueMap;
  
    @Override
    public void setValueMap(IValueMap valueMap){
        this.valueMap = valueMap;
    }
    
    @Override
    public abstract void execute() throws Exception;
    
}
