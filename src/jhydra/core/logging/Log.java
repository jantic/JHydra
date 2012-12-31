/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jhydra.core.logging;

/**
 *
 * @author jantic
 */

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import java.io.File;
import jhydra.core.config.IConfig;
import jhydra.core.exceptions.FatalException;
import org.slf4j.LoggerFactory;


public class Log implements ILog{
    //TODO:  Put this stuff in config?
    private final String relativeLogDirectory = "/logs/";
    private final String logFileName = "rolling-log.log";
    private final String logDirectory;
    private final Logger logger;
    
    //For project specific logging
    public Log(IConfig config) throws FatalException{
        logDirectory = config.getProjectPath() + relativeLogDirectory;
        final String logPath = logDirectory + logFileName;
        logger = getLogger(logPath);
    }

    private Logger getLogger(String logPath) 
            throws LogDirectoryAccessDeniedException, LogDirectoryCreationException{
        establishLogDirectory(logDirectory);
        return getConfiguredLogger(logPath);
    }
    
    private void establishLogDirectory(String logDirectory) 
            throws LogDirectoryAccessDeniedException, LogDirectoryCreationException{
        try{
            final File file = new File(logDirectory);
           
            if (!file.isDirectory()){
                file.mkdirs();
            }
            
            if(!file.canWrite()){
                throw new LogDirectoryAccessDeniedException(logDirectory);
            }
        }
        catch(Exception e){
            throw new LogDirectoryCreationException(logDirectory, e);
        }
    }

    private Logger getConfiguredLogger(String logPath){
        final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        final RollingFileAppender<ILoggingEvent> appender = getAppender(logPath, loggerContext);        
        final Logger logbackLogger = loggerContext.getLogger(Log.class);
        logbackLogger.addAppender(appender);
        logbackLogger.setLevel(Level.DEBUG);
        logbackLogger.setAdditive(true);
        return logbackLogger;
    }
    
    private RollingFileAppender<ILoggingEvent> getAppender(String logPath, LoggerContext loggerContext){
        final RollingFileAppender<ILoggingEvent> appender = new RollingFileAppender<>();
        appender.setContext(loggerContext);
        appender.setFile(logPath);
        appender.setAppend(true);
        final FixedWindowRollingPolicy rollingPolicy = new FixedWindowRollingPolicy();
        rollingPolicy.setContext(loggerContext);
        rollingPolicy.setParent(appender);
        rollingPolicy.setMaxIndex(10);
        rollingPolicy.setFileNamePattern("rolling-log.%i.log");
        rollingPolicy.start();
        appender.setRollingPolicy(rollingPolicy);
        final SizeBasedTriggeringPolicy<ILoggingEvent> triggeringPolicy = new SizeBasedTriggeringPolicy<>();
        triggeringPolicy.setContext(loggerContext);
        triggeringPolicy.setMaxFileSize("30MB");
        triggeringPolicy.start();
        appender.setTriggeringPolicy(triggeringPolicy);
        final String conversionPattern = "%date [%thread] %-5level - %message%n%ex%n";
        final PatternLayout layout = new PatternLayout();
        layout.setContext(loggerContext);
        layout.setPattern(conversionPattern);
        layout.setPresentationHeader(System.lineSeparator() + System.lineSeparator() + "[Header]" + System.lineSeparator());
        layout.setPresentationFooter("[Footer]" + System.lineSeparator());
        layout.start();
        appender.setLayout(layout);
        appender.start();
        return appender;
    }
    
    @Override
    public void debug(String message, Exception exception) {
        logger.debug(message, exception);
    }

    @Override
    public void debug(String message) {
        logger.debug(message);
    }

    @Override
    public void error(String message, Exception exception) {
        logger.error(message, exception);
    }

    @Override
    public void error(String message) {
        logger.error(message);
    }

    @Override
    public void info(String message, Exception exception) {
        logger.info(message, exception);
    }

    @Override
    public void info(String message) {
        logger.info(message);
    }

    @Override
    public void warn(String message, Exception exception) {
        logger.warn(message, exception);
    }

    @Override
    public void warn(String message) {
        logger.warn(message);
    }
}
