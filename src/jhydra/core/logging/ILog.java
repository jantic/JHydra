/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.logging;

/**
 *
 * @author jantic
 */
public interface ILog {
    void debug(String message, Exception exception);
    void debug(String message);
    void error(String message, Exception exception);
    void error(String message);
    void error(Exception exception);
    void fatal(String message, Exception exception);
    void fatal(String message);
    void fatal(Exception exception);
    void info(String message, Exception exception);
    void info(String message);
    void warn(String message, Exception exception);
    void warn(String message);
    void warn(Exception exception);
}
