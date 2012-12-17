/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.uinav.browser;

/**
 *
 * @author jantic
 */
public class BrowserType {
    public static final BrowserType IEXPLORER = new BrowserType("Internet Explorer");
    public static final BrowserType FIREFOX = new BrowserType("Firefox");
    public static final BrowserType CHROME = new BrowserType("Chrome");
    public static final BrowserType OPERA = new BrowserType("Opera");
    public static final BrowserType SAFARI = new BrowserType("Safari");
    
    private final String name;
    
    private BrowserType(String name){
        this.name = name;
    }
    
    public String getName(){
        return this.name;
    }
}
