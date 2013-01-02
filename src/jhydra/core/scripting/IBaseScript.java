/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

import jhydra.core.config.IProjectConfig;
import jhydra.core.logging.ILog;
import jhydra.core.uinav.IMasterNavigator;
import jhydra.core.valuemap.IValueMap;

/**
 *
 * @author jantic
 */
public interface IBaseScript extends IScript{
    
    public void setValueMap(IValueMap valueMap);
    
    public void setScriptFactory(IScriptFactory scriptFactory);
    
    public void setConfig(IProjectConfig config);
    
    public void setLog(ILog log);
    
    public void setNavigator(IMasterNavigator navigator);
}
