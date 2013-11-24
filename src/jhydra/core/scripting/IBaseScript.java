/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.scripting;

import jhydra.core.config.IRuntimeConfig;
import jhydra.core.logging.ILog;
import jhydra.core.uinav.IMasterNavigator;
import jhydra.core.valuemap.IValueMap;

/**
 *
 * @author jantic
 */
public interface IBaseScript extends IScript{

    public void initialize(IMasterNavigator navigator, IValueMap valueMap, IScriptFactory scriptFactory,
                           IRuntimeConfig config, ILog log);
}
